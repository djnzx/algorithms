use anyhow::{bail, Context, Result};
use std::process::Command;

pub fn ensure_chrome_available() -> Result<()> {
    let version = run_osascript(&["tell application \"Google Chrome\" to get version"])?;
    if version.trim().is_empty() {
        bail!("Google Chrome is not available")
    }
    Ok(())
}

pub fn chrome_open_url(url: &str) -> Result<()> {
    let escaped_url = apple_string(url);
    run_osascript(&[
        "tell application \"Google Chrome\"",
        "activate",
        "if (count of windows) = 0 then make new window",
        &format!("set URL of active tab of front window to \"{escaped_url}\""),
        "end tell",
    ])?;
    Ok(())
}

pub fn chrome_eval_json(js: &str) -> Result<String> {
    let wrapped = format!("JSON.stringify((() => {{ {} }})())", js);
    let escaped = apple_string(&wrapped);
    run_osascript(&[
        "tell application \"Google Chrome\"",
        &format!("execute active tab of front window javascript \"{escaped}\""),
        "end tell",
    ])
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

    Ok(String::from_utf8(output.stdout)?.trim().to_string())
}

fn apple_string(value: &str) -> String {
    value.replace('\\', "\\\\").replace('"', "\\\"")
}
