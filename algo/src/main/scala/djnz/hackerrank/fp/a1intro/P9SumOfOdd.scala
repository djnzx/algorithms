package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-sum-of-odd-elements/problem */
object P9SumOfOdd {

  /** stdlib foldLeft */
  def f1(arr: List[Int]): Int =
    arr.foldLeft(0) {
      case (acc, x) if x % 2 != 0 => acc + x
      case (acc, _) => acc
    }

  def f(delim: Int, arr: List[Int]): List[Int] = arr match {
    case Nil                 => Nil
    case h :: t if h < delim => h :: f(delim, t)
    case _ :: t              => f(delim, t)
  }

  def f(arr: List[Int]): Int = {

    def isOdd(x: Int) = x % 2 != 0

    def go(arr: List[Int], acc: Int): Int = arr match {
      case Nil                => acc
      case h :: t if isOdd(h) => go(t, acc + h)
      case _ :: t             => go(t, acc)
    }

    go(arr, 0)
  }

}
