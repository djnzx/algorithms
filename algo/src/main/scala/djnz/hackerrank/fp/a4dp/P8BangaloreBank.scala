package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/bangalore-bank/problem */
object P8BangaloreBank {

  def next() = scala.io.StdIn.readLine()

  type Cache = Map[(Int, Int, Int), Int]
  private val memo0 = Map.empty[(Int, Int, Int), Int]

  def solve(digits: Seq[Int]): Int = {

    /** time to press: 1.move and 2.press */
    def time(current: Int, digit: Int): Int = {
      def adj(v: Int) = if (v == 0) 10 else v
      math.abs(adj(current) - adj(digit)) + 1
    }

    def go(lf: Int, rf: Int, idx: Int, acc: Int, memo: Cache): (Int, Cache) = {
      val k = (lf, rf, idx)
      memo.get(k) match {
        case Some(x)                   => (x + acc) -> memo
        case _ if idx >= digits.length => (0 + acc) -> (memo + (k -> 0))
        case _                         =>
          val digit = digits(idx)
          val (x1, memo1) = go(digit, rf, idx + 1, time(lf, digit), memo)
          val (x2, memo2) = go(lf, digit, idx + 1, time(rf, digit), memo1)
          val x = math.min(x1, x2)
          (x + acc) -> (memo2 + (k -> x))
      }
    }

    (0 to 9)
      .foldLeft(Int.MaxValue -> memo0) { case ((v, memo), d) =>
        val (x, memo2) = go(digits.head, d, 0, 0, memo)
        (x min v) -> memo2
      }
      ._1
  }

  def main(args: Array[String]): Unit = {
    val _ = next()
    val account = next().split(" ").map(_.toInt)
    val r = solve(account)
    println(r)
  }

}
