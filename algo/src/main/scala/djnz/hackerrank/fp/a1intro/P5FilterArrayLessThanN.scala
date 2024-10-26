package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-filter-array/problem */
object P5FilterArrayLessThanN {

  /** stdlib */
  def f1(delim: Int, arr: List[Int]): List[Int] =
    arr.filter(_ < delim)

  /** pure FP, recursion */
  def f(delim: Int, arr: List[Int]): List[Int] = arr match {
    case Nil                 => Nil
    case h :: t if h < delim => h :: f(delim, t)
    case _ :: t              => f(delim, t)
  }

}
