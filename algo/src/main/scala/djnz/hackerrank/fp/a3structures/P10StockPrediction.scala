package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/stocks-prediction/problem */
object P10StockPrediction {
  // 0 < l < d < r < n

  def next() = scala.io.StdIn.readLine()
  case class Query(index: Int, d: Int, m: Int, min: Int, max: Int)

  // as - prices per day
  // d  - index
  // we need the longest subarray: all i: ad <= ai <= ad + m
  def query(as: Array[Int], d: Int, m: Int): Int = {
    val min = as(d)
    val max = min + m
    def fits(x: Int) = x >= min && x <= max

    def findL(it: Iterator[Int], acc: Int): Int = it.hasNext match {
      case false => acc
      case _     =>
        val i = it.next()
        fits(as(i)) match {
          case true  => findL(it, i)
          case false => acc
        }
    }

    def findR(it: Iterator[Int], acc: Int): Int = it.hasNext match {
      case false => acc
      case _     =>
        val i = it.next()
        fits(as(i)) match {
          case true  => findR(it, i)
          case false => acc
        }
    }

    val l = findL((0 to d).reverseIterator, d)
    val r = findR((d until as.length).iterator, d)
    r - l + 1
  }

  // TODO:sort by delta, ... optimize
  def solve(as: Array[Int], qs: Seq[Query]): Seq[Int] =
    qs.map(q => query(as, q.d, q.m))

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val as = next().split(" ").map(_.toInt).take(n)
    val nq = next().toInt
    val qs = (1 to nq)
      .map(i => next().split(" ").map(_.toInt) match { case Array(d, m) => Query(i, d, m, as(d), as(d) + m) })

    solve(as, qs)
      .foreach(println)
  }

}
