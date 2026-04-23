use anyhow::{anyhow, bail, Context, Result};
use base64::Engine;
use reqwest::blocking::Client;
use serde::{de::DeserializeOwned, Deserialize, Serialize};
use sha2::{Digest, Sha256};
use std::fs;
use std::io::{Read, Write};
use std::net::TcpListener;
use std::path::PathBuf;
use std::time::{Duration, SystemTime, UNIX_EPOCH};
use url::Url;

const AUTH_URL: &str = "https://accounts.google.com/o/oauth2/v2/auth";
const TOKEN_URL: &str = "https://oauth2.googleapis.com/token";
const CLASSROOM_API_BASE: &str = "https://classroom.googleapis.com/v1";
const TOKEN_FILE: &str = ".google_classroom_token.json";
const SCOPES: &[&str] = &[
    "https://www.googleapis.com/auth/classroom.courses.readonly",
    "https://www.googleapis.com/auth/classroom.coursework.me.readonly",
    "https://www.googleapis.com/auth/classroom.courseworkmaterials.readonly",
    "https://www.googleapis.com/auth/classroom.announcements.readonly",
];

fn main() {
    if let Err(error) = run() {
        eprintln!("error: {error:#}");
        std::process::exit(1);
    }
}

fn run() -> Result<()> {
    let client_id = read_required_env("GOOGLE_CLIENT_ID")?;
    let client_secret = std::env::var("GOOGLE_CLIENT_SECRET").ok();
    let http = Client::builder()
        .timeout(Duration::from_secs(30))
        .build()
        .context("failed to build HTTP client")?;

    let token_path = PathBuf::from(TOKEN_FILE);
    let access_token = get_access_token(&http, &client_id, client_secret.as_deref(), &token_path)?;

    let courses: CoursesResponse = google_get(
        &http,
        &access_token,
        &format!("{CLASSROOM_API_BASE}/courses?courseStates=ACTIVE&pageSize=100"),
    )?;

    let mut courses = courses.courses.unwrap_or_default();
    if courses.is_empty() {
        println!("No active Google Classroom courses were found for this account.");
        return Ok(());
    }

    courses.sort_by(|a, b| a.name.cmp(&b.name));
    println!("Available Google Classroom courses:\n");
    for (index, course) in courses.iter().enumerate() {
        println!(
            "{}. {} [{}]",
            index + 1,
            course.name.as_deref().unwrap_or("Untitled course"),
            course.id.as_deref().unwrap_or("no-id")
        );
        if let Some(section) = &course.section {
            println!("   Section: {section}");
        }
        if let Some(description) = &course.description_heading {
            println!("   Heading: {description}");
        }
    }

    let selected = choose_course(&courses)?;
    println!(
        "\nFetching content for: {}\n",
        selected.name.as_deref().unwrap_or("Untitled course")
    );

    let course_id = selected
        .id
        .as_deref()
        .context("selected course did not contain an id")?;

    let announcements: AnnouncementsResponse = google_get(
        &http,
        &access_token,
        &format!("{CLASSROOM_API_BASE}/courses/{course_id}/announcements?pageSize=50"),
    )?;
    let course_work: CourseWorkResponse = google_get(
        &http,
        &access_token,
        &format!("{CLASSROOM_API_BASE}/courses/{course_id}/courseWork?pageSize=50"),
    )?;
    let materials: CourseWorkMaterialsResponse = google_get(
        &http,
        &access_token,
        &format!("{CLASSROOM_API_BASE}/courses/{course_id}/courseWorkMaterials?pageSize=50"),
    )?;

    print_announcements(announcements.announcements.unwrap_or_default());
    print_course_work(course_work.course_work.unwrap_or_default());
    print_materials(materials.course_work_materials.unwrap_or_default());

    Ok(())
}

fn read_required_env(name: &str) -> Result<String> {
    std::env::var(name).with_context(|| format!("missing required environment variable {name}"))
}

fn choose_course(courses: &[Course]) -> Result<&Course> {
    print!("\nChoose a course by number: ");
    std::io::stdout()
        .flush()
        .context("failed to flush stdout")?;

    let mut input = String::new();
    std::io::stdin()
        .read_line(&mut input)
        .context("failed to read selection from stdin")?;

    let index: usize = input
        .trim()
        .parse()
        .context("please enter a valid number")?;

    if !(1..=courses.len()).contains(&index) {
        bail!("selection out of range")
    }

    Ok(&courses[index - 1])
}

fn get_access_token(
    http: &Client,
    client_id: &str,
    client_secret: Option<&str>,
    token_path: &PathBuf,
) -> Result<String> {
    if let Some(saved) = load_token(token_path)? {
        if !saved.is_expired() {
            return Ok(saved.access_token);
        }

        if let Some(refresh_token) = saved.refresh_token.clone() {
            let refreshed = refresh_access_token(http, client_id, client_secret, &refresh_token)?;
            save_token(token_path, &refreshed)?;
            return Ok(refreshed.access_token);
        }
    }

    let fresh = login_via_browser(http, client_id, client_secret)?;
    save_token(token_path, &fresh)?;
    Ok(fresh.access_token)
}

fn load_token(path: &PathBuf) -> Result<Option<SavedToken>> {
    if !path.exists() {
        return Ok(None);
    }

    let raw = fs::read_to_string(path)
        .with_context(|| format!("failed to read token file {}", path.display()))?;
    let token = serde_json::from_str(&raw)
        .with_context(|| format!("failed to parse token file {}", path.display()))?;
    Ok(Some(token))
}

fn save_token(path: &PathBuf, token: &SavedToken) -> Result<()> {
    let raw = serde_json::to_string_pretty(token).context("failed to serialize token")?;
    fs::write(path, raw).with_context(|| format!("failed to write {}", path.display()))
}

fn refresh_access_token(
    http: &Client,
    client_id: &str,
    client_secret: Option<&str>,
    refresh_token: &str,
) -> Result<SavedToken> {
    let mut params = vec![
        ("client_id", client_id.to_string()),
        ("grant_type", "refresh_token".to_string()),
        ("refresh_token", refresh_token.to_string()),
    ];

    if let Some(secret) = client_secret {
        params.push(("client_secret", secret.to_string()));
    }

    let response: TokenResponse = http
        .post(TOKEN_URL)
        .form(&params)
        .send()
        .context("failed to refresh Google access token")?
        .error_for_status()
        .context("Google rejected refresh token request")?
        .json()
        .context("failed to decode refresh token response")?;

    Ok(SavedToken::from_token_response(
        response,
        Some(refresh_token.to_string()),
    ))
}

fn login_via_browser(
    http: &Client,
    client_id: &str,
    client_secret: Option<&str>,
) -> Result<SavedToken> {
    let listener =
        TcpListener::bind("127.0.0.1:0").context("failed to bind local callback port")?;
    let port = listener
        .local_addr()
        .context("failed to read callback port")?
        .port();
    let redirect_uri = format!("http://127.0.0.1:{port}/callback");

    let state = random_url_safe(24);
    let verifier = random_url_safe(64);
    let challenge = pkce_challenge(&verifier);
    let scope = SCOPES.join(" ");

    let auth_url = Url::parse_with_params(
        AUTH_URL,
        &[
            ("client_id", client_id),
            ("redirect_uri", redirect_uri.as_str()),
            ("response_type", "code"),
            ("scope", scope.as_str()),
            ("access_type", "offline"),
            ("prompt", "consent"),
            ("state", state.as_str()),
            ("code_challenge", challenge.as_str()),
            ("code_challenge_method", "S256"),
        ],
    )
    .context("failed to build Google authorization URL")?;

    println!("Open this URL in your browser to sign in with Google:\n{auth_url}\n");
    let _ = webbrowser::open(auth_url.as_str());

    let code = wait_for_oauth_code(listener, &state)?;

    let mut params = vec![
        ("client_id", client_id.to_string()),
        ("code", code),
        ("code_verifier", verifier),
        ("grant_type", "authorization_code".to_string()),
        ("redirect_uri", redirect_uri),
    ];

    if let Some(secret) = client_secret {
        params.push(("client_secret", secret.to_string()));
    }

    let response: TokenResponse = http
        .post(TOKEN_URL)
        .form(&params)
        .send()
        .context("failed to exchange Google authorization code")?
        .error_for_status()
        .context("Google rejected authorization code exchange")?
        .json()
        .context("failed to decode token exchange response")?;

    Ok(SavedToken::from_token_response(response, None))
}

fn wait_for_oauth_code(listener: TcpListener, expected_state: &str) -> Result<String> {
    let (mut stream, _) = listener
        .accept()
        .context("failed to accept OAuth callback")?;
    let mut buffer = [0_u8; 4096];
    let bytes_read = stream
        .read(&mut buffer)
        .context("failed to read OAuth callback request")?;
    let request = String::from_utf8_lossy(&buffer[..bytes_read]);
    let first_line = request
        .lines()
        .next()
        .context("OAuth callback request was empty")?;
    let path = first_line
        .split_whitespace()
        .nth(1)
        .context("OAuth callback request line was malformed")?;

    let callback_url = Url::parse(&format!("http://localhost{path}"))
        .context("failed to parse OAuth callback URL")?;
    let params: Vec<(String, String)> = callback_url.query_pairs().into_owned().collect();

    let returned_state = params
        .iter()
        .find(|(key, _)| key == "state")
        .map(|(_, value)| value.as_str())
        .ok_or_else(|| anyhow!("OAuth callback did not contain state"))?;

    if returned_state != expected_state {
        bail!("OAuth callback state mismatch")
    }

    if let Some(error) = params
        .iter()
        .find(|(key, _)| key == "error")
        .map(|(_, value)| value.clone())
    {
        bail!("Google login returned error: {error}")
    }

    let code = params
        .iter()
        .find(|(key, _)| key == "code")
        .map(|(_, value)| value.clone())
        .ok_or_else(|| anyhow!("OAuth callback did not contain authorization code"))?;

    let body = "<html><body><h2>Google login complete</h2><p>You can return to the console app.</p></body></html>";
    let response = format!(
        "HTTP/1.1 200 OK\r\ncontent-type: text/html; charset=utf-8\r\ncontent-length: {}\r\nconnection: close\r\n\r\n{}",
        body.len(),
        body
    );
    stream
        .write_all(response.as_bytes())
        .context("failed to write OAuth callback response")?;

    Ok(code)
}

fn pkce_challenge(verifier: &str) -> String {
    let digest = Sha256::digest(verifier.as_bytes());
    base64::engine::general_purpose::URL_SAFE_NO_PAD.encode(digest)
}

fn random_url_safe(size: usize) -> String {
    let bytes: Vec<u8> = (0..size).map(|_| rand::random::<u8>()).collect();
    base64::engine::general_purpose::URL_SAFE_NO_PAD.encode(bytes)
}

fn google_get<T: DeserializeOwned>(http: &Client, access_token: &str, url: &str) -> Result<T> {
    http.get(url)
        .bearer_auth(access_token)
        .send()
        .with_context(|| format!("failed to call Google API: {url}"))?
        .error_for_status()
        .with_context(|| format!("Google API returned an error for {url}"))?
        .json()
        .with_context(|| format!("failed to decode Google API response from {url}"))
}

fn print_announcements(announcements: Vec<Announcement>) {
    println!("Announcements:");
    if announcements.is_empty() {
        println!("  none\n");
        return;
    }

    for item in announcements {
        println!(
            "  - {}",
            item.text.unwrap_or_else(|| "(no text)".to_string())
        );
        if let Some(updated_at) = item.update_time {
            println!("    updated: {updated_at}");
        }
    }
    println!();
}

fn print_course_work(items: Vec<CourseWork>) {
    println!("Course work:");
    if items.is_empty() {
        println!("  none\n");
        return;
    }

    for item in items {
        println!(
            "  - {} [{}]",
            item.title
                .unwrap_or_else(|| "Untitled work item".to_string()),
            item.work_type.unwrap_or_else(|| "UNKNOWN".to_string())
        );
        if let Some(description) = item.description {
            println!("    description: {}", single_line(&description));
        }
        if let Some(due_date) = item.due_date {
            println!(
                "    due: {:04}-{:02}-{:02}",
                due_date.year.unwrap_or_default(),
                due_date.month.unwrap_or_default(),
                due_date.day.unwrap_or_default()
            );
        }
    }
    println!();
}

fn print_materials(items: Vec<CourseWorkMaterial>) {
    println!("Course materials:");
    if items.is_empty() {
        println!("  none");
        return;
    }

    for item in items {
        println!(
            "  - {}",
            item.title
                .unwrap_or_else(|| "Untitled course material".to_string())
        );
        if let Some(description) = item.description {
            println!("    description: {}", single_line(&description));
        }
        if let Some(materials) = item.materials {
            println!("    attachments: {}", materials.len());
        }
    }
}

fn single_line(text: &str) -> String {
    text.split_whitespace().collect::<Vec<_>>().join(" ")
}

#[derive(Debug, Serialize, Deserialize)]
struct SavedToken {
    access_token: String,
    refresh_token: Option<String>,
    expires_at_epoch_seconds: u64,
}

impl SavedToken {
    fn from_token_response(
        response: TokenResponse,
        refresh_token_override: Option<String>,
    ) -> Self {
        let now = SystemTime::now()
            .duration_since(UNIX_EPOCH)
            .unwrap_or_default()
            .as_secs();
        let refresh_token = response.refresh_token.or(refresh_token_override);

        Self {
            access_token: response.access_token,
            refresh_token,
            expires_at_epoch_seconds: now + response.expires_in.saturating_sub(30),
        }
    }

    fn is_expired(&self) -> bool {
        let now = SystemTime::now()
            .duration_since(UNIX_EPOCH)
            .unwrap_or_default()
            .as_secs();
        now >= self.expires_at_epoch_seconds
    }
}

#[derive(Debug, Deserialize)]
struct TokenResponse {
    access_token: String,
    expires_in: u64,
    #[serde(default)]
    refresh_token: Option<String>,
}

#[derive(Debug, Deserialize)]
struct CoursesResponse {
    #[serde(default)]
    courses: Option<Vec<Course>>,
}

#[derive(Debug, Deserialize)]
struct Course {
    id: Option<String>,
    name: Option<String>,
    section: Option<String>,
    #[serde(rename = "descriptionHeading")]
    description_heading: Option<String>,
}

#[derive(Debug, Deserialize)]
struct AnnouncementsResponse {
    #[serde(default)]
    announcements: Option<Vec<Announcement>>,
}

#[derive(Debug, Deserialize)]
struct Announcement {
    text: Option<String>,
    #[serde(rename = "updateTime")]
    update_time: Option<String>,
}

#[derive(Debug, Deserialize)]
struct CourseWorkResponse {
    #[serde(default, rename = "courseWork")]
    course_work: Option<Vec<CourseWork>>,
}

#[derive(Debug, Deserialize)]
struct CourseWork {
    title: Option<String>,
    description: Option<String>,
    #[serde(rename = "workType")]
    work_type: Option<String>,
    #[serde(rename = "dueDate")]
    due_date: Option<DateValue>,
}

#[derive(Debug, Deserialize)]
struct DateValue {
    year: Option<i32>,
    month: Option<u32>,
    day: Option<u32>,
}

#[derive(Debug, Deserialize)]
struct CourseWorkMaterialsResponse {
    #[serde(default, rename = "courseWorkMaterial")]
    course_work_materials: Option<Vec<CourseWorkMaterial>>,
}

#[derive(Debug, Deserialize)]
struct CourseWorkMaterial {
    title: Option<String>,
    description: Option<String>,
    materials: Option<Vec<serde_json::Value>>,
}
