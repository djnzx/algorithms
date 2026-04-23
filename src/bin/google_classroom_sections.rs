use anyhow::{bail, Result};
use std::thread;
use std::time::Duration;

#[path = "mods/browser.rs"]
mod browser;
#[path = "mods/course.rs"]
mod course;
#[path = "mods/json.rs"]
mod json;
#[path = "mods/scrape.rs"]
mod scrape;

const PAGE_WAIT: Duration = Duration::from_secs(4);
const DEFAULT_COURSE_URL: &str = "https://classroom.google.com/u/0/c/NzkyOTk0OTk1ODIy";

fn main() {
    let course_url = std::env::args()
        .nth(1)
        .unwrap_or_else(|| DEFAULT_COURSE_URL.to_string());

    if let Err(error) = run(course_url) {
        eprintln!("error: {error:#}");
        std::process::exit(1);
    }
}

fn run(course_url: String) -> Result<()> {
    browser::ensure_chrome_available()?;
    let classwork_url = course::classwork_url(&course_url);

    println!("Opening Classwork page in Chrome...");
    browser::chrome_open_url(&classwork_url)?;
    thread::sleep(PAGE_WAIT);

    scrape::expand_all_view_more()?;
    thread::sleep(Duration::from_millis(800));

    let dump = scrape::scrape_sections_once(&course_url, &classwork_url)?;
    if dump.sections.is_empty() {
        bail!("could not find Classwork topics on the page")
    }

    println!("{}", serde_json::to_string_pretty(&dump)?);
    Ok(())
}
