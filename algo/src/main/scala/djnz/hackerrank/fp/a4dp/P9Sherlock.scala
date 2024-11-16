package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/sherlock-and-the-maze/problem */
object P9Sherlock {
  /*
  the number of distinct ways: 1,1 -> M,N

   (M-1 + N-1)!
  --------------
  (M-1)! x (N-1)!
   */

  def next() = scala.io.StdIn.readLine()
  val modulo = 1000000007
  sealed trait Dir {
    def another: Dir = this match {
      case D => R
      case R => D
    }
  }

  case object R extends Dir
  case object D extends Dir

  def solve(n: Int, m: Int, k: Int): Int = {
    // TODO: implement in a functional fold
    val memo = scala.collection.mutable.Map.empty[(Int, Int, Int, Dir), Int]

    def go(m0: Int, n0: Int, k: Int, dir0: Dir): Int = {
      val (m, n, dir) = if (m0 < n0)
        (m0, n0, dir0)
      else
        (n0, m0, dir0.another)

      val key = (m, n, k, dir)

      val x = memo.get(key) match {
        case Some(x)                      => Right(x)
        case _ if k < 0 || m < 1 || n < 1 => Left(0)
        case _ if m == 1 && n == 1        => Left(1)
        case _ if dir == R                => Left((go(m - 1, n, k, R) + go(m, n - 1, k - 1, D)) % modulo)
        case _ if dir == D                => Left((go(m - 1, n, k - 1, R) + go(m, n - 1, k, D)) % modulo)
        case _                            => sys.error("never by design")
      }
      x.fold(x => { memo.put(key, x); x }, identity)
    }

    (m, n) match {
      case (1, 1) => 1
      case (m, n) => (go(m - 1, n, k, R) + go(m, n - 1, k, D)) % modulo
    }
  }

  def main(args: Array[String]): Unit =
    (1 to next().toInt)
      .map(_ => next())
      .map(_.split(" "))
      .map(_.map(_.toInt))
      .map { case Array(n, m, k) => solve(n, m, k) }
      .foreach(println)

}
