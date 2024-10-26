use std::collections::HashSet;

fn mk_number(a: u32, b: u32, c: u32, d: u32) -> u32 {
    a * 1000 + b * 100 + c * 10 + d
}

#[test]
fn mk_number_test() {
    assert_eq!(1234u32, mk_number(1, 2, 3, 4));
}

fn fits(xs: &[u32]) -> bool {
    match xs {
        [m, u, x, a, s, l, o, n] => mk_number(*m, *u, *x, *a) * *a == mk_number(*s, *l, *o, *n),
        _ => false,
    }
}

#[test]
fn fits_test() {
    assert_eq!(false, fits(&[1, 2, 3, 4, 5, 6, 7, 8]));
    assert_eq!(true, fits(&[1, 7, 8, 2, 3, 5, 6, 4]));
}

fn distinct(xs: &[u32]) -> bool {
    xs.iter()
        .collect::<HashSet<_>>()
        .len()
        == 8
}

#[test]
fn distinct_test() {
    assert_eq!(distinct(&[1, 2, 3, 4, 5, 6, 7, 8]), true);
    assert_eq!(distinct(&[1, 2, 3, 4, 5, 6, 7, 7]), false);
}

fn show(xs: &[u32]) {
    match xs {
        [m, u, x, a, s, l, o, n] => {
            println!(" {m}{u}{x}{a}");
            println!("x   {a}");
            println!(" -----");
            println!(" {s}{l}{o}{n}");
        }
        _ => panic!(),
    }
}

fn without_x<I>(r: I, x: u32) -> Box<dyn Iterator<Item = u32>>
where
    I: Iterator<Item = u32> + 'static, // Iterator must live for 'static to fit into the Box
{
    let filtered = r.filter(move |z| z != &x);
    Box::new(filtered)
}

#[test]
fn show_test() {
    let xs = [1, 2, 3, 4, 5, 6, 7, 8];
    show(&xs);
}

fn solve() -> Vec<[u32; 8]> {
    let mut solutions = vec![];

    let r1 = 1..=8;
    for m in r1.clone() {
        let r2 = r1
            .clone()
            .filter(|z| *z != m);
        for u in r2.clone() {
            let r3 = r2
                .clone()
                .filter(|z| *z != u);
            for x in r3.clone() {
                let r4 = r3
                    .clone()
                    .filter(|z| *z != x);
                for a in r4.clone() {
                    let r5 = r4
                        .clone()
                        .filter(|z| *z != a);
                    for s in r5.clone() {
                        let r6 = r5
                            .clone()
                            .filter(|z| *z != s);
                        for l in r6.clone() {
                            let r7 = r6
                                .clone()
                                .filter(|z| *z != l);
                            for o in r7.clone() {
                                let r8 = r7
                                    .clone()
                                    .filter(|z| *z != o);
                                for n in r8 {
                                    let aa = [m, u, x, a, s, l, o, n];
                                    if distinct(&aa) && fits(&aa) {
                                        solutions.push(aa);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    solutions
}

#[test]
fn muha_slon_solution_test() {
    solve()
        .iter()
        .for_each(|x| {
            show(x);
            println!();
        });
}
