// https://aperiodic.net/pip/scala/s-99/#p49

fn gray(n: u8) -> Box<dyn Iterator<Item=String>> {
    match n {
        0 => Box::new([""].iter().map(|s| s.to_string())),
        n =>
            Box::new(gray(n - 1)
                .flat_map(move |s|
                    vec!(0, 1)
                        .iter()
                        .map(|v| s.clone() + v.to_string().as_str())
                        .collect::<Vec<_>>()
                ))
    }
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
        .for_each(|(n, out)| {
            println!("n: {:?}, out: {:?}", n, out);
            let real = gray(*n).collect::<Vec<_>>();
            let expected = out;
            assert_eq!(real, *expected);
        });
}


