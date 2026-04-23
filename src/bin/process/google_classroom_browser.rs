use anyhow::{anyhow, bail, Context, Result};
use serde::{Deserialize, Serialize};
use std::fs;
use std::io::{self, Write};
use std::process::Command;
use std::thread;
use std::time::Duration;

const CLASSROOM_HOME_URL: &str = "https://classroom.google.com/u/0/h";
const OUTPUT_JSON: &str = "classroom_browser_dump.json";
const OUTPUT_TEXT: &str = "classroom_browser_dump.txt";
const PAGE_LOAD_WAIT: Duration = Duration::from_secs(4);

fn main() {
    if let Err(error) = run() {
        eprintln!("error: {error:#}");
        std::process::exit(1);
    }
}

fn run() -> Result<()> {
    ensure_chrome_available()?;

    let args: Vec<String> = std::env::args().collect();
    let explicit_url = args.get(1).cloned();

    let selected_course = if let Some(url) = explicit_url {
        println!("Opening Classroom course in Chrome...");
        chrome_open_url(&url)?;
        thread::sleep(PAGE_LOAD_WAIT);
        CourseChoice {
            name: scrape_course_name().unwrap_or_else(|_| "Selected course".to_string()),
            url,
        }
    } else {
        println!("Opening Google Classroom in Chrome...");
        chrome_open_url(CLASSROOM_HOME_URL)?;
        thread::sleep(PAGE_LOAD_WAIT);

        let mut courses = scrape_courses()?;
        if courses.is_empty() {
            println!(
                "I could not find any courses yet. Make sure Chrome is logged into Google Classroom and the Classroom home page is visible."
            );
            wait_for_enter("Press Enter after the courses are visible in Chrome...")?;
            courses = scrape_courses()?;
        }

        if courses.is_empty() {
            bail!("still could not find Classroom course cards in Chrome")
        }

        courses.sort_by(|a, b| a.name.cmp(&b.name));
        println!("\nCourses found in Chrome:\n");
        for (index, course) in courses.iter().enumerate() {
            println!("{}. {}", index + 1, course.name);
            println!("   {}", course.url);
        }

        let selected = choose_course(&courses)?;
        println!("\nOpening course: {}", selected.name);
        chrome_open_url(&selected.url)?;
        thread::sleep(PAGE_LOAD_WAIT);
        selected.clone()
    };

    println!(
        "\nIf needed, finish loading the course in Chrome. The app will try Course, Stream, and Classwork pages when available."
    );

    let mut pages = Vec::new();
    pages.push(scrape_current_page("course")?);

    let tabs = scrape_tabs().unwrap_or_default();
    for label in ["Stream", "Classwork"] {
        if let Some(tab) = tabs.iter().find(|tab| tab.label.eq_ignore_ascii_case(label)) {
            chrome_open_url(&tab.url)?;
            thread::sleep(PAGE_LOAD_WAIT);
            pages.push(scrape_current_page(&label.to_lowercase())?);
        }
    }

    let dump = CourseDump {
        selected_course,
        tabs,
        pages,
    };

    let json = serde_json::to_string_pretty(&dump).context("failed to serialize JSON dump")?;
    fs::write(OUTPUT_JSON, &json).context("failed to write JSON dump")?;
    fs::write(OUTPUT_TEXT, format_dump_text(&dump)).context("failed to write text dump")?;

    println!("\nTyped dump:\n");
    println!("{json}");
    println!("\nSaved:");
    println!("- {OUTPUT_JSON}");
    println!("- {OUTPUT_TEXT}");

    Ok(())
}

fn ensure_chrome_available() -> Result<()> {
    let version = run_osascript(&["tell application \"Google Chrome\" to get version"])?;
    if version.trim().is_empty() {
        bail!("Google Chrome is not available")
    }
    Ok(())
}

fn chrome_open_url(url: &str) -> Result<()> {
    let escaped_url = apple_string(url);
    let script = [
        "tell application \"Google Chrome\"",
        "activate",
        "if (count of windows) = 0 then make new window",
        &format!("set URL of active tab of front window to \"{escaped_url}\""),
        "end tell",
    ];

    run_osascript(&script).map(|_| ())
}

fn chrome_eval_json(js: &str) -> Result<String> {
    let wrapped = format!("JSON.stringify((() => {{ {} }})())", js);
    let escaped = apple_string(&wrapped);
    run_osascript(&[
        "tell application \"Google Chrome\"",
        &format!("execute active tab of front window javascript \"{escaped}\""),
        "end tell",
    ])
}

fn scrape_courses() -> Result<Vec<CourseChoice>> {
    let raw = chrome_eval_json(
        r#"
const seen = new Set();
const links = Array.from(document.querySelectorAll('a[href*="/c/"]'));
const courses = [];
for (const link of links) {
  const url = link.href;
  if (!url || seen.has(url)) continue;
  const text = (link.innerText || link.textContent || '').replace(/\s+/g, ' ').trim();
  if (!text) continue;
  const name = text.split('Join class')[0].trim();
  if (!name) continue;
  seen.add(url);
  courses.push({ name, url });
}
return courses;
"#,
    )?;

    parse_json(&raw)
}

fn scrape_course_name() -> Result<String> {
    let raw = chrome_eval_json(
        r#"
const text = (document.querySelector('h1')?.innerText || document.title || '').replace(/\s+/g, ' ').trim();
return { value: text };
"#,
    )?;
    let response: ScalarValue = parse_json(&raw)?;
    Ok(response.value)
}

fn scrape_tabs() -> Result<Vec<CourseTab>> {
    let raw = chrome_eval_json(
        r#"
const tabs = [];
for (const link of Array.from(document.querySelectorAll('a[href]'))) {
  const label = (link.innerText || link.textContent || '').replace(/\s+/g, ' ').trim();
  if (!label) continue;
  if (!['Stream', 'Classwork', 'People', 'Grades'].includes(label)) continue;
  tabs.push({ label, url: link.href });
}
return tabs;
"#,
    )?;

    let mut tabs: Vec<CourseTab> = parse_json(&raw)?;
    tabs.sort_by(|a, b| a.label.cmp(&b.label));
    tabs.dedup_by(|a, b| a.label == b.label && a.url == b.url);
    Ok(tabs)
}

fn scrape_current_page(kind: &str) -> Result<PageDump> {
    let raw = chrome_eval_json(
        r#"
function clean(text) {
  return (text || '').replace(/\s+/g, ' ').trim();
}
function isVisible(el) {
  if (!el) return false;
  const style = window.getComputedStyle(el);
  if (style.display === 'none' || style.visibility === 'hidden') return false;
  const rect = el.getBoundingClientRect();
  return rect.width > 0 && rect.height > 0;
}
function attrs(el) {
  const output = {};
  for (const attr of Array.from(el.attributes || [])) {
    output[attr.name] = attr.value;
  }
  return output;
}
function dataset(el) {
  return Object.assign({}, el.dataset || {});
}
function role(el) {
  return el.getAttribute('role') || '';
}
function cssPath(el) {
  const parts = [];
  let current = el;
  let depth = 0;
  while (current && current.nodeType === Node.ELEMENT_NODE && depth < 4) {
    let part = current.tagName.toLowerCase();
    if (current.id) {
      part += '#' + current.id;
      parts.unshift(part);
      break;
    }
    const classes = Array.from(current.classList || []).slice(0, 2);
    if (classes.length) {
      part += '.' + classes.join('.');
    }
    parts.unshift(part);
    current = current.parentElement;
    depth += 1;
  }
  return parts.join(' > ');
}
const page = {
  title: document.title,
  url: location.href,
  course_id: (location.pathname.match(/\/c\/([^/]+)/) || [null, null])[1],
  headings: [],
  elements: [],
};
const seenHeadings = new Set();
for (const el of Array.from(document.querySelectorAll('h1, h2, h3, h4, [role="heading"]'))) {
  const text = clean(el.innerText || el.textContent);
  if (!text || seenHeadings.has(text)) continue;
  seenHeadings.add(text);
  page.headings.push({
    tag: el.tagName.toLowerCase(),
    text,
    role: role(el),
    aria_label: el.getAttribute('aria-label') || '',
    css_path: cssPath(el),
  });
}
const candidates = Array.from(document.querySelectorAll('main a, main button, main article, main li, main [role="listitem"], main [role="button"], main [role="link"], main [data-stream-item-id], main [data-topic-id], main [data-course-id], main div'));
const seen = new Set();
for (const el of candidates) {
  if (!isVisible(el)) continue;
  const text = clean(el.innerText || el.textContent);
  const href = el.href || '';
  const marker = [el.tagName, text, href, cssPath(el)].join('|');
  if (text.length < 2 && !href) continue;
  if (text.length > 800) continue;
  if (seen.has(marker)) continue;
  seen.add(marker);
  page.elements.push({
    tag: el.tagName.toLowerCase(),
    text,
    href,
    role: role(el),
    id: el.id || '',
    class_name: el.className || '',
    aria_label: el.getAttribute('aria-label') || '',
    aria_description: el.getAttribute('aria-description') || '',
    title: el.getAttribute('title') || '',
    data_attributes: dataset(el),
    attributes: attrs(el),
    css_path: cssPath(el),
  });
  if (page.elements.length >= 200) break;
}
return page;
"#,
    )?;

    let page: BrowserPageResult = parse_json(&raw)?;
    Ok(PageDump {
        kind: kind.to_string(),
        title: page.title,
        url: page.url,
        course_id: page.course_id,
        headings: page.headings,
        elements: page.elements,
    })
}

fn choose_course(courses: &[CourseChoice]) -> Result<&CourseChoice> {
    print!("\nChoose a course by number: ");
    io::stdout().flush().context("failed to flush stdout")?;

    let mut input = String::new();
    io::stdin()
        .read_line(&mut input)
        .context("failed to read course selection")?;

    let index: usize = input.trim().parse().context("please enter a valid number")?;
    if !(1..=courses.len()).contains(&index) {
        bail!("selection out of range")
    }

    Ok(&courses[index - 1])
}

fn wait_for_enter(prompt: &str) -> Result<()> {
    print!("{prompt}");
    io::stdout().flush().context("failed to flush stdout")?;
    let mut input = String::new();
    io::stdin()
        .read_line(&mut input)
        .context("failed to read input")?;
    Ok(())
}

fn run_osascript(lines: &[&str]) -> Result<String> {
    let mut command = Command::new("osascript");
    for line in lines {
        command.arg("-e").arg(line);
    }

    let output = command.output().context("failed to run osascript")?;
    if !output.status.success() {
        let stderr = String::from_utf8_lossy(&output.stderr);
        if stderr.contains("Allow JavaScript from Apple Events") {
            bail!(
                "Chrome blocked page scraping. In Google Chrome, enable View > Developer > Allow JavaScript from Apple Events, then run the app again."
            )
        }
        bail!("osascript failed: {}", stderr.trim())
    }

    Ok(String::from_utf8(output.stdout)
        .context("osascript returned non-utf8 output")?
        .trim()
        .to_string())
}

fn parse_json<T: for<'de> Deserialize<'de>>(raw: &str) -> Result<T> {
    if raw.trim().is_empty() || raw.trim() == "undefined" {
        return Err(anyhow!("Chrome returned an empty result"));
    }
    serde_json::from_str(raw).with_context(|| format!("failed to parse Chrome JSON: {raw}"))
}

fn apple_string(value: &str) -> String {
    value.replace('\\', "\\\\").replace('"', "\\\"")
}

fn format_dump_text(dump: &CourseDump) -> String {
    let mut out = String::new();
    out.push_str(&format!("Course: {}\n", dump.selected_course.name));
    out.push_str(&format!("URL: {}\n\n", dump.selected_course.url));

    if !dump.tabs.is_empty() {
        out.push_str("Tabs:\n");
        for tab in &dump.tabs {
            out.push_str(&format!("- {} => {}\n", tab.label, tab.url));
        }
        out.push('\n');
    }

    for page in &dump.pages {
        out.push_str(&format!("== {} ==\n", page.kind));
        out.push_str(&format!("Title: {}\n", page.title));
        out.push_str(&format!("URL: {}\n", page.url));
        if let Some(course_id) = &page.course_id {
            out.push_str(&format!("Course ID: {}\n", course_id));
        }
        out.push_str("Headings:\n");
        for heading in &page.headings {
            out.push_str(&format!("- [{}] {}\n", heading.tag, heading.text));
        }
        out.push_str("Elements:\n");
        for element in &page.elements {
            out.push_str(&format!(
                "- [{}] text='{}' href='{}' role='{}' css='{}'\n",
                element.tag, element.text, element.href, element.role, element.css_path
            ));
        }
        out.push('\n');
    }

    out
}

#[derive(Debug, Clone, Serialize, Deserialize)]
struct CourseChoice {
    name: String,
    url: String,
}

#[derive(Debug, Clone, Serialize, Deserialize)]
struct CourseTab {
    label: String,
    url: String,
}

#[derive(Debug, Serialize, Deserialize)]
struct ScalarValue {
    value: String,
}

#[derive(Debug, Serialize, Deserialize)]
struct HeadingDump {
    tag: String,
    text: String,
    role: String,
    aria_label: String,
    css_path: String,
}

#[derive(Debug, Serialize, Deserialize)]
struct ElementDump {
    tag: String,
    text: String,
    href: String,
    role: String,
    id: String,
    class_name: String,
    aria_label: String,
    aria_description: String,
    title: String,
    data_attributes: serde_json::Map<String, serde_json::Value>,
    attributes: serde_json::Map<String, serde_json::Value>,
    css_path: String,
}

#[derive(Debug, Serialize, Deserialize)]
struct BrowserPageResult {
    title: String,
    url: String,
    course_id: Option<String>,
    headings: Vec<HeadingDump>,
    elements: Vec<ElementDump>,
}

#[derive(Debug, Serialize, Deserialize)]
struct PageDump {
    kind: String,
    title: String,
    url: String,
    course_id: Option<String>,
    headings: Vec<HeadingDump>,
    elements: Vec<ElementDump>,
}

#[derive(Debug, Serialize, Deserialize)]
struct CourseDump {
    selected_course: CourseChoice,
    tabs: Vec<CourseTab>,
    pages: Vec<PageDump>,
}
