package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-array-of-n-elements/problem */
object P7GenListOfN {

  /** stdlib */
  def f1(num: Int): List[Int] =
    Array.fill[Int](num)(1).toList

  /** stdlib */
  def f2(num: Int): List[Int] =
    1 to num toList

  /** pure functional without any magic numbers */
  def f(num: Int): List[Int] = num match {
    case 0 => Nil
    case n => n :: f(n - 1)
  }

}
