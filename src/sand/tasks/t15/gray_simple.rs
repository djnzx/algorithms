// https://aperiodic.net/pip/scala/s-99/#p49

fn gray(n: u8) -> Vec<String> {
    let mut a = vec![String::new()];
    for _ in 0..n {
        let a0 = a.iter().map(|s| format!("0{s}"));
        let a1 = a.iter().map(|s| format!("1{s}"));
        a = a0.chain(a1).collect::<Vec<_>>();
    }
    a
}

#[test]
fn test() {
    let test_data =
        [
            (0, vec!("")),
            (1, vec!("0", "1")),
            (2, vec!("00", "01", "10", "11")),
            (3, vec!("000", "001", "010", "011", "100", "101", "110", "111")),
            (4, vec!("0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111")),
        ];

    test_data
        .iter()
        .for_each(|(n, out)|
            assert_eq!(gray(*n), *out)
        );
}
