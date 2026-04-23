### Google Classroom console app

This repository now includes a small Rust console app that:

- signs in with Google OAuth for a desktop app
- lists your active Google Classroom courses
- lets you choose one course in the terminal
- prints announcements, course work, and course materials for that course

Source file:

- `src/bin/google_classroom_console.rs`

#### Google Cloud setup

1. Create or select a Google Cloud project.
2. Enable the Google Classroom API.
3. Configure the OAuth consent screen.
4. Create an OAuth client of type `Desktop app`.
5. Export your client ID before running the app:

```bash
export GOOGLE_CLIENT_ID='your-desktop-client-id.apps.googleusercontent.com'
```

Optional:

```bash
export GOOGLE_CLIENT_SECRET='your-client-secret'
```

#### Run

```bash
cargo run --bin google_classroom_console
```

The app opens your browser for Google login and stores the token in:

```text
.google_classroom_token.json
```

Delete that file if you want to force a fresh login.

### Google Classroom browser app

If you cannot use the Classroom API with your school-managed account, there is also a browser-session version:

- source: `src/bin/google_classroom_browser.rs`
- uses your existing logged-in Google Chrome session
- does not ask for your Google password
- does not use Google Classroom API tokens

Run:

```bash
cargo run --bin google_classroom_browser
```

What it does:

- opens Google Classroom in Chrome
- reads the visible course cards from the Classroom home page
- lets you choose a course in the terminal
- opens that course and tries to scrape the visible `Stream` and `Classwork` pages
- saves results to:
    - `classroom_browser_dump.json`
    - `classroom_browser_dump.txt`

Notes:

- Chrome may ask for macOS Automation permission the first time.
- In Chrome, enable `View -> Developer -> Allow JavaScript from Apple Events` so the app can read the current page.
- This approach depends on the current Classroom page structure, so it is more brittle than the API-based version.
