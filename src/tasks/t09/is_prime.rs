fn is_prime(n: &u32) -> bool {
    let limit = (*n as f64).sqrt().ceil() as u32;
    match n {
        0 | 1 => false,
        2 => true,
        n => (2..=limit).all(|x| n % x != 0)
    }
}

#[test]
fn test_is_prime() {
    let test_data = [
        (0, false),
        (1, false),
        (2, true),
        (3, true),
        (4, false),
        (5, true),
        (100, false),
        (10007, true),
    ];

    test_data
        .iter()
        .for_each(|(n, prime)|
            assert_eq!(is_prime(n), *prime)
        )
}
