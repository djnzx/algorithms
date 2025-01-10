use std::collections::HashSet;
use std::ops::Range;

#[derive(Hash, Eq, PartialEq, Debug)]
struct Point {
    x: i32,
    y: i32,
}

#[derive(Hash, Eq, PartialEq, Debug)]
struct Rectangle {
    a: Point,
    b: Point,
}

fn test_data() -> Vec<Rectangle> {
    vec![
        Rectangle {
            a: Point { x: 2, y: 9 },
            b: Point { x: 5, y: 3 },
        },
        Rectangle {
            a: Point { x: 1, y: 8 },
            b: Point { x: 11, y: 6 },
        },
        Rectangle {
            a: Point { x: 9, y: 10 },
            b: Point { x: 13, y: 2 },
        },
    ]
}

fn area(r: &Rectangle) -> i32 {
    (r.a.x - r.b.x).abs() * (r.a.y - r.b.y).abs()
}

impl Rectangle {
    fn area(&self) -> i32 {
        area(self)
    }
}

#[test]
fn area_test() {
    let xs = test_data();
    let r = xs
        .iter()
        .next()
        .unwrap();
    let a = r.area();
    assert_eq!(a, 18);

    let r = xs
        .iter()
        .skip(1)
        .next()
        .unwrap();
    let a = area(r);
    assert_eq!(a, 20);

    let r = xs
        .iter()
        .skip(2)
        .next()
        .unwrap();
    let a = area(r);
    assert_eq!(a, 32);
}

fn area_total(xs: &Vec<Rectangle>) -> i32 {
    xs.iter()
        .map(area)
        .sum()
}

#[test]
fn area_total_test() {
    let data = test_data();
    let total = area_total(&data);
    println!("{:?}", total);
    assert_eq!(total, 70);
}

fn mk_range<A: Ord>(x: A, y: A) -> Range<A> {
    if x <= y { x..y } else { y..x }
}

// The '_ lifetime refers to the lifetime of the borrowed Rectangle.
// It ensures that the iterator you return doesn't outlive the borrowed data,
// keeping it safe and valid as long as r is valid.
fn to_points(r: &Rectangle) -> impl Iterator<Item=Point> + '_ {
    let xs = mk_range(r.a.x, r.b.x);
    let ys = mk_range(r.a.y, r.b.y);

    xs.flat_map(move |x| ys.clone().map(move |y| Point { x, y }))
}

#[test]
fn to_points_test_single_call() {
    let r = Rectangle {
        a: Point { x: 10, y: 1 },
        b: Point { x: 15, y: 3 },
    };
    to_points(&r)
        // .iter()
        .for_each(|p| println!("{:?}", p));
}

#[test]
fn to_points_test_array() {
    let rs = [Rectangle {
        a: Point { x: 10, y: 1 },
        b: Point { x: 15, y: 3 },
    }];
    rs.iter()
        .flat_map(|r| to_points(&r))
        .for_each(|p| println!("{:?}", p));
}

fn area_occupied(xs: &Vec<Rectangle>) -> usize {
    xs
        .iter()
        .flat_map(|r| to_points(r))
        .collect::<HashSet<_>>()
        .len()
}

#[test]
fn area_occupied_test() {
    let data = test_data();
    data
        .iter()
        .for_each(|r| println!("{:?}", r));

    let occupied = area_occupied(&data);
    assert_eq!(occupied, 60);
}

