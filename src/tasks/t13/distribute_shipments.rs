use rand::Rng;

fn gen_shipments(n: usize) -> Vec<u32> {
    let mut tr = rand::thread_rng();
    let mut next = || tr.gen_range(1..10);

    let mut data = Vec::new();
    for _ in 0..n - 1 {
        data.push(next());
    }

    let total_without_last = data
        .iter()
        .sum::<u32>();
    let avg = total_without_last as f64 / (n - 1) as f64;
    let avg_ceil = avg.ceil() as u32;
    let total = avg_ceil * n as u32;
    let last = total - total_without_last;
    data.push(last);
    data
}

fn count_permutation(shipments: &Vec<u32>) -> usize {
    let total = shipments
        .iter()
        .sum::<u32>() as i32;

    let avg = total / shipments.len() as i32;

    shipments
        .iter()
        .map(|x| avg - (*x as i32))
        .filter(|x| *x > 0)
        .sum::<i32>() as usize
}

#[test]
fn test() {
    let xs = gen_shipments(5);
    println!("{:?}", xs);
    println!(
        "avg:{}",
        xs.iter()
            .sum::<u32>()
            / xs.len() as u32
    );
    let count = count_permutation(&xs);
    println!("{:?}", count);
}
