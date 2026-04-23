use crate::browser;
use crate::json::parse_json;
use anyhow::Result;
use serde::{Deserialize, Serialize};
use std::thread;
use std::time::Duration;

pub fn expand_all_view_more() -> Result<()> {
    for _ in 0..10 {
        let raw = browser::chrome_eval_json(
            r#"
function isVisible(el) {
  if (!el) return false;
  const style = window.getComputedStyle(el);
  if (style.display === 'none' || style.visibility === 'hidden') return false;
  const rect = el.getBoundingClientRect();
  return rect.width > 0 && rect.height > 0;
}
let clicked = 0;
for (const button of Array.from(document.querySelectorAll('button[aria-label^="View more"]'))) {
  if (!isVisible(button)) continue;
  button.scrollIntoView({ block: 'center' });
  button.click();
  clicked += 1;
}
return { clicked };
"#,
        )?;

        let result: ClickResult = parse_json(&raw)?;
        if result.clicked == 0 {
            break;
        }

        thread::sleep(Duration::from_millis(700));
    }

    Ok(())
}

pub fn scrape_sections_once(course_url: &str, classwork_url: &str) -> Result<CourseSectionsDump> {
    let raw = browser::chrome_eval_json(
        r#"
const clean = text => (text || '').replace(/\s+/g, ' ').trim();
const isVisible = el => {
  if (!el) return false;
  const style = window.getComputedStyle(el);
  if (style.display === 'none' || style.visibility === 'hidden') return false;
  const rect = el.getBoundingClientRect();
  return rect.width > 0 && rect.height > 0;
};

for (let round = 0; round < 8; round += 1) {
  let clicked = 0;
  for (const button of Array.from(document.querySelectorAll('button[aria-label^="View more"]'))) {
    if (!isVisible(button)) continue;
    button.scrollIntoView({ block: 'center' });
    button.click();
    clicked += 1;
  }
  if (!clicked) break;
}

const sections = [];
for (const section of Array.from(document.querySelectorAll('div.jHgkif.KSMuDe[data-topic-id]'))) {
  const topicId = section.getAttribute('data-topic-id') || '';
  const header = section.querySelector('div.jLMcgc.O1l69[data-topic-id]') || section.querySelector('[data-topic-id]');
  const title = clean((header?.innerText || '').split('Collapse topic')[0].replace(/more_vert/g, ''));
  const items = [];
  const seenItems = new Set();

  for (const li of Array.from(section.querySelectorAll('li[data-stream-item-id]'))) {
    const streamItemId = li.getAttribute('data-stream-item-id') || '';
    if (!streamItemId || seenItems.has(streamItemId)) continue;
    seenItems.add(streamItemId);

    const clickTarget = li.querySelector('[role="button"][aria-label]') || li.querySelector('[jsname="tdoU3e"]') || li;
    clickTarget.click();

    const titleNode = li.querySelector('.kByKEb, .Vu2fZd.Cx437e, [aria-label]');
    const itemTitle = clean(titleNode?.innerText || titleNode?.getAttribute('aria-label') || '');
    const metaLines = Array.from(li.querySelectorAll('.wCDkmf, .WZkEbf, .Vu2fZd.qoXqmb, .HM4nYe'))
      .map(node => clean(node.innerText || node.textContent))
      .filter(Boolean);

    const attachments = [];
    const seenUrls = new Set();
    for (const a of Array.from(li.querySelectorAll('a[href]'))) {
      const url = a.href || '';
      const text = clean(a.innerText || a.textContent || a.getAttribute('aria-label') || '');
      if (!url || seenUrls.has(url)) continue;
      seenUrls.add(url);
      attachments.push({
        text,
        url,
        aria_label: a.getAttribute('aria-label') || '',
        target: a.getAttribute('target') || '',
      });
    }

    const itemUrl = attachments.find(a =>
      /drive\.google|docs\.google|forms\.gle|github\.com|classroom\.google\.com\/u\/0\/c\/.*\/[am]\//.test(a.url)
    )?.url || '';

    items.push({
      stream_item_id: streamItemId,
      item_type: li.getAttribute('data-stream-item-type') || '',
      title: itemTitle,
      summary: clean(li.innerText || ''),
      due_or_status: metaLines[0] || '',
      attachments,
      body_text: clean(li.innerText || ''),
      item_url: itemUrl,
      badges: Array.from(li.querySelectorAll('[aria-label]'))
        .map(el => clean(el.getAttribute('aria-label')))
        .filter(Boolean),
      meta_lines: metaLines,
    });
  }

  sections.push({ topic_id: topicId, title, items });
}

return {
  course_name: clean(document.querySelector('h1')?.innerText || document.title || 'Selected course'),
  course_url: '',
  classwork_url: location.href,
  sections,
};
"#,
    )?;

    let mut dump: CourseSectionsDump = parse_json(&raw)?;
    dump.course_url = course_url.to_string();
    if dump.classwork_url.is_empty() {
        dump.classwork_url = classwork_url.to_string();
    }
    Ok(dump)
}

#[derive(Debug, Deserialize)]
struct ClickResult {
    clicked: usize,
}

#[derive(Debug, Serialize, Deserialize)]
pub(crate) struct Attachment {
    text: String,
    url: String,
    aria_label: String,
    target: String,
}

#[derive(Debug, Serialize, Deserialize)]
pub(crate) struct SectionItem {
    stream_item_id: String,
    item_type: String,
    title: String,
    summary: String,
    due_or_status: String,
    attachments: Vec<Attachment>,
    body_text: String,
    item_url: String,
    badges: Vec<String>,
    meta_lines: Vec<String>,
}

#[derive(Debug, Serialize, Deserialize)]
pub(crate) struct Section {
    topic_id: String,
    title: String,
    items: Vec<SectionItem>,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct CourseSectionsDump {
    pub course_name: String,
    pub course_url: String,
    pub classwork_url: String,
    pub sections: Vec<Section>,
}
