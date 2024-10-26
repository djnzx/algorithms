package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/functional-programming-warmups-in-recursion---gcd/problem */
object P1GCD {

  def gcd(x: Int, y: Int): Int = (x, y) match {
    case (x, 0)          => x
    case (x, y) if x > y => gcd(y, x % y)
    case (x, y) if y > x => gcd(x, y % x)
    case _               => x
  }

}
