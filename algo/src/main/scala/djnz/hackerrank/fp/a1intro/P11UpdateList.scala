package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-update-list/problem */
object P11UpdateList {

  /** tail recursive */
  def f1(arr: List[Int]): List[Int] = {

    def go(xs: List[Int], acc: List[Int]): List[Int] = xs match {
      case h :: t => go(t, scala.math.abs(h) :: acc)
      case _      => acc
    }

    go(arr, List.empty[Int]).reverse
  }

  /** classic recursion */
  def f(arr: List[Int]): List[Int] = arr match {
    case h :: t => scala.math.abs(h) :: f(t)
    case Nil    => Nil
  }

}
