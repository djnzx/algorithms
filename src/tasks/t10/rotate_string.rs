fn rotate(s: String, n: isize) -> String {
    match s.len() as isize {
        0 => s,
        len => match n % len {
            n if n > 0 => match s.split_at((len - n) as usize) {
                (p1, p2) => p2.to_string() + p1,
            },
            n if n < 0 => match s.split_at(-n as usize) {
                (p1, p2) => p2.to_string() + p1,
            },
            _ => s,
        },
    }
}

fn rotateV2(s: String, n: isize) -> String {
    match s.len() as isize {
        0 => s,
        len => {
            let at = if n % len > 0 { len - n } else { -n } as usize;
            match at {
                0 => s,
                at => match s.split_at(at) {
                    (p1, p2) => p2.to_string() + p1,
                },
            }
        }
    }
}

fn rotate2(s: &str, n: &i32) -> String {
    rotate(s.to_owned(), *n as isize)
}

#[test]
fn test() {
    let s = "abcdefgh";
    let shifts = [
        (0, "abcdefgh"),
        (8, "abcdefgh"),
        (-8, "abcdefgh"),
        (1, "habcdefg"),
        (2, "ghabcdef"),
        (10, "ghabcdef"),
        (-1, "bcdefgha"),
        (-2, "cdefghab"),
        (-10, "cdefghab"),
    ];

    shifts
        .iter()
        .for_each(|(n, exp)|
            assert_eq!(rotate2(s, n), exp.to_string())
        );
}
