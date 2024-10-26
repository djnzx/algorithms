package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-list-length/problem */
object P10Length {

  /** stdlib */
  def f2(arr: List[Int]): Int = arr.length

  /** tail recursive */
  def f1(arr: List[Int]): Int = {

    def go(xs: List[Int], acc: Int): Int = xs match {
      case _ :: t => go(t, acc + 1)
      case _      => acc
    }

    go(arr, 0)
  }

  /** classic recursive, but stack */
  def f(arr: List[Int]): Int = arr match {
    case Nil    => 0
    case _ :: t => 1 + f(t)
  }

}
