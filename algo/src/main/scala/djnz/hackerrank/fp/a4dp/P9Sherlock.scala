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

    type Cache = Map[(Int, Int, Int, Dir), Int]
    val memo0: Cache = Map.empty

    def go(m0: Int, n0: Int, k: Int, dir0: Dir, memo: Cache): (Int, Cache) = {
      val (m, n, dir) = if (m0 < n0)
        (m0, n0, dir0)
      else
        (n0, m0, dir0.another)

      val key = (m, n, k, dir)

      val x = memo.get(key) match {
        case Some(x)                      => Right(x -> memo)
        case _ if k < 0 || m < 1 || n < 1 => Left(0 -> memo)
        case _ if m == 1 && n == 1        => Left(1 -> memo)
        case _ if dir == R                =>
          val (x1, memo1) = go(m - 1, n, k, R, memo)
          val (x2, memo2) = go(m, n - 1, k - 1, D, memo1)
          val x = (x1 + x2) % modulo
          Left(x -> memo2)
        case _ if dir == D                =>
          val (x1, memo1) = go(m - 1, n, k - 1, R, memo)
          val (x2, memo2) = go(m, n - 1, k, D, memo1)
          val x = (x1 + x2) % modulo
          Left(x -> memo2)
        case _                            => sys.error("never by design")
      }
      x match {
        case Left((x, c)) => x -> (c + (key -> x))
        case Right(x)     => x
      }
    }

    (m, n) match {
      case (1, 1) => 1
      case (m, n) =>
        val (x1, memo1) = go(m - 1, n, k, R, memo0)
        val (x2, memo2) = go(m, n - 1, k, D, memo1)
        (x1 + x2) % modulo
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
