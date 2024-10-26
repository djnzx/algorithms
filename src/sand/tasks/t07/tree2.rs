fn calc_max_width(n: u8) -> u8 {
    (n - 1) * 2 + 1
}

#[test]
fn calc_width_test() {
    assert_eq!(calc_max_width(1), 1);
    assert_eq!(calc_max_width(2), 3);
    assert_eq!(calc_max_width(3), 5);
    assert_eq!(calc_max_width(4), 7);
    assert_eq!(calc_max_width(5), 9);
    assert_eq!(calc_max_width(6), 11);
}

fn calc_height(n: u8) -> u8 {
    n
}

#[test]
fn calc_height_test() {
    assert_eq!(calc_height(1), 1);
    assert_eq!(calc_height(2), 2);
    assert_eq!(calc_height(3), 3);
    assert_eq!(calc_height(4), 4);
    assert_eq!(calc_height(5), 5);
    assert_eq!(calc_height(6), 6);
}

fn calc_total_height(n: u8) -> u8 {
    (1..=n)
        .into_iter()
        .map(calc_height)
        .sum()
}

#[test]
fn calc_total_height_test() {
    assert_eq!(calc_total_height(1), 1);
    assert_eq!(calc_total_height(2), 3);
    assert_eq!(calc_total_height(3), 6);
    assert_eq!(calc_total_height(4), 10);
    assert_eq!(calc_total_height(5), 15);
}

fn calc_tree_starts_at(n: u8) -> u8 {
    (1..n)
        .into_iter()
        .map(calc_height)
        .sum()
}

#[test]
fn tree_starts_test() {
    assert_eq!(calc_tree_starts_at(1), 0);
    assert_eq!(calc_tree_starts_at(2), 1);
    assert_eq!(calc_tree_starts_at(3), 3);
    assert_eq!(calc_tree_starts_at(4), 6);
    assert_eq!(calc_tree_starts_at(5), 10);
}

fn calc_start_finish(n: u8) -> Vec<(u8, u8)> {
    (1..)
        .map(|k| {
            let st = calc_tree_starts_at(k);
            let fi = st + calc_height(k) - 1;
            (st, fi)
        })
        .take(n as usize)
        .collect::<Vec<_>>()
}

#[test]
fn calc_st_fi_test() {
    let x = calc_start_finish(6);
    println!("{:?}", x); // [(0, 0), (1, 2), (3, 5), (6, 9), (10, 14), (15, 20)]
}

fn calc_tree_y(n: u8, y: u8) -> u8 {
    calc_start_finish(n) // [(0, 0), (1, 2), (3, 5), (6, 9), (10, 14), (15, 20)]
        .into_iter()
        .skip_while(|(st, fi)| y > *fi)
        .map(|(st, _)| y - st)
        .next()
        .unwrap()
}

#[test]
fn calc_tree_y_test() {
    (0..21).for_each(|y| println!("{:2} : {}", y, calc_tree_y(10, y)));
}

#[test]
fn tree2() {
    const N: u8 = 6;
    let h_total = calc_total_height(N);
    let max_width = calc_max_width(N);

    for k in 0..(max_width * h_total) {
        let x = k % max_width;
        let global_y = k / max_width;
        let tree_y = calc_tree_y(N, global_y);

        let is_white = {
            let inv_x = max_width - 1 - x;
            let l = x + tree_y < N - 1;
            let r = inv_x + tree_y < N - 1;
            l || r
        };

        let c = if is_white { ' ' } else { '*' };
        print!("{c}");
        if x == max_width - 1 {
            println!();
        }
    }
}
