use rand::Rng;

fn gen_random_vector(n: usize) -> Vec<i32> {
    let mut tr = rand::thread_rng();
    let mut next = || tr.gen_range(10..100);

    let mut data = Vec::new();
    for _ in 0..n {
        data.push(next());
    }
    data
}

fn min_adjacent_sum(data: &[i32]) -> usize {
    let mut prev = data[0];
    let mut min_sum = data[0] + data[1];
    let mut min_idx = 0;

    for (idx, x) in data
        .iter()
        .enumerate()
        .skip(1)
    {
        let sum = prev + *x;
        if sum < min_sum {
            min_idx = idx - 1;
            min_sum = sum;
        }
        prev = *x;
    }

    min_idx
}

fn represent(data: &Vec<i32>, idx: usize) {
    print!("indexes:");
    for i in 0..data.len() {
        print!("{i:2}. ");
    }
    println!();

    println!("data:  {:?}", data);

    print!("indexes:");
    for i in 0..data.len() {
        let s = match i as i32 - idx as i32 {
            0 => "\\__",
            1 => "__/",
            _ => "   ",
        };
        print!("{} ", s);
    }
    println!();

    let sum = data[idx] + data[idx + 1];
    println!("min adjacent sum={}+{}={} at indexes:{},{}", data[idx], data[idx + 1], sum, idx, idx + 1);
}

#[test]
fn playground() {
    let n: usize = 20;
    let data = gen_random_vector(n);
    let idx = min_adjacent_sum(&data);
    represent(&data, idx);
}
