use anyhow::{anyhow, Context, Result};
use serde::de::DeserializeOwned;

pub fn parse_json<T: DeserializeOwned>(raw: &str) -> Result<T> {
    if raw.trim().is_empty() || raw.trim() == "undefined" {
        return Err(anyhow!("Chrome returned an empty result"));
    }

    serde_json::from_str(raw).with_context(|| format!("failed to parse Chrome JSON: {raw}"))
}
