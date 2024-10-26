package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-filter-positions-in-a-list/problem */
object P6FilterOutOdd {

  /** stdlib O(3*n) */
  def f3(arr: List[Int]): List[Int] =
    arr.zipWithIndex
      .filter(_._2 % 2 != 0)
      .map(_._1)

  /** stdlib O(2*n) */
  def f2(arr: List[Int]): List[Int] =
    arr.zipWithIndex
      .flatMap {
        case (n, idx) if idx % 2 != 0 => Some(n)
        case _ => None
      }

  /** stdlib, with state O(n) + reverse */
  def f1(arr: List[Int]): List[Int] =
    arr.foldLeft(0 -> List.empty[Int]) {
      case ((idx, acc), x) if idx % 2 != 0 => (1 - idx) -> (x :: acc)
      case ((idx, acc), _) => (1 - idx) -> acc
    } match {
      case (_, acc) => acc.reverse
    }

  /** pure functional, O(n) */
  def f(arr: List[Int]): List[Int] = {

    def go(idx: Int, xs: List[Int]): List[Int] = (idx, xs) match {
      case (1, h :: t)   => h :: go(1 - idx, t)
      case (idx, _ :: t) => go(1 - idx, t)
      case _             => Nil
    }

    go(0, arr)
  }

}
