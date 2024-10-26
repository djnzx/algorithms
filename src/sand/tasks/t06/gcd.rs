fn gcd1(a: u32, b: u32) -> u32 {
    if b == 0 { a } else { gcd1(b, a % b) }
}

fn gcd2(a: u32, b: u32) -> u32 {
    match (a, b) {
        (a, 0) => a,
        (a, b) => gcd2(b, a % b)
    }
}

fn gcd(a: u32, b: u32) -> u32 {
    gcd2(a, b)
}

#[test]
fn test() {
    let data =
        [
            ((24, 60), 12),
            ((15, 9), 3),
            ((15, 6), 3),
            ((140, 40), 20),
            ((24, 16), 8),
            ((100, 10), 10),
            ((120, 80), 40),
            ((80, 120), 40),
            ((100, 20), 20),
            ((37, 11), 1),
            ((120, 90), 30),
        ];

    for ((a, b), exp) in data.iter() {
        assert_eq!(*exp, gcd2(*a, *b));
        assert_eq!(*exp, gcd2(*b, *a));
    }
}