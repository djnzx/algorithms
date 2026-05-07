package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/subset-sum/problem */
object P10SubsetSum {
  import scala.collection.Searching
  import scala.io.StdIn.readLine

  def solve(agg: Array[Long], s: Long): Option[Int] = agg.search(s) match {
    case Searching.Found(i)                             => Some(i + 1)
    case Searching.InsertionPoint(i) if i >= agg.length => None
    case Searching.InsertionPoint(i)                    => Some(i + 1)
  }

  def main(args: Array[String]): Unit = {
    val _ = readLine
    val ns = readLine().split(" ").flatMap(_.trim.toLongOption).sortBy(identity)(Ordering.Long.reverse)
    // cumulative aggregation
    val agg = ns.foldLeft(List.empty[Long]) {
      case (Nil, n)        => List(n)
      case (l @ h :: _, n) => (n + h) :: l
    }.reverse.toArray

    val n = readLine.trim.toInt
    (1 to n)
      .foreach { _ =>
        val s = readLine.trim.toLong
        val x = solve(agg, s).getOrElse(-1)
        println(x)
      }
  }

}
