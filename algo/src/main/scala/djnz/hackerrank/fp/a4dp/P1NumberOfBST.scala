package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/number-of-binary-search-tree/problem */
object P1NumberOfBST {

  def next() = scala.io.StdIn.readLine()
  val mod = 100000007

  def mkAllCatalanUpTo(n: Int): Map[Int, Int] = {
    import scala.collection.immutable.TreeMap
    val memo0 = TreeMap(0 -> 1, 1 -> 1)

    /** catalan numbers */
    def catalan(n: Int, memo: Map[Int, Int]): (Int, Map[Int, Int]) = memo.get(n) match {
      case Some(x) => x -> memo
      case None    =>
        val (x, memoN) = (0 until n)
          .foldLeft((0, memo)) { case ((a, memo), leftCount) =>
            val rightCount = n - leftCount - 1 // left + right + 1 = N
            val (lc, memo2) = catalan(leftCount, memo)
            val (rc, memo3) = catalan(rightCount, memo2)
            val x = (a + (lc.toLong * rc % mod).toInt) % mod
            val r = (x, memo3)
//            pprint.log((n, a, leftCount, rightCount, lc, rc, x))
            r
          }
        (x, memoN + (n -> x))
    }

    catalan(n, memo0) match {
      case (_, memo) => memo
    }
  }

  def solve(ns: Seq[Int]): Seq[Int] = {
    val all = mkAllCatalanUpTo(ns.max)
    ns.map(all(_))
  }

  def number_of_bst_classic(n: Int): Long = {
    val memo = Array.fill(n + 1)(0L)
    memo(0) = 1
    memo(1) = 1

    for (node <- 2 to n)
      for (prev <- 0 until node)
        memo(node) = (memo(node) + (memo(prev) * memo(node - prev - 1) % mod).toInt) % mod

    memo(n)
  }

  def main(args: Array[String]): Unit = {
    val t = next().toInt
    val ns = (1 to t).map(_ => next().toInt)
    solve(ns)
      .foreach(println)
  }

}
