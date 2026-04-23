pub fn classwork_url(course_url: &str) -> String {
    if course_url.contains("/w/") && course_url.contains("/t/") {
        return course_url.to_string();
    }

    if let Some(course_id) = course_url
        .split("/c/")
        .nth(1)
        .and_then(|part| part.split('/').next())
    {
        return format!("https://classroom.google.com/u/0/w/{course_id}/t/all");
    }

    course_url.to_string()
}
