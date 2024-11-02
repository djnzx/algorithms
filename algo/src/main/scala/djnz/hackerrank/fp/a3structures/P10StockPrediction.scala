package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/stocks-prediction/problem */
object P10StockPrediction {

  def next() = scala.io.StdIn.readLine()

  /** index from 0 */
  case class Query(index: Int, d: Int, up: Int)

  def solve(stocks: Seq[Int], queries: Seq[Query], n: Int) = {

    // queries per price
    val qpp: Map[Int, List[Int]] = stocks.zipWithIndex
      .foldLeft(Map.empty[Int, List[Int]]) {
        case (map, (price, idx)) =>
          val x = price -> (idx :: map.getOrElse(price, Nil))
          map + x
      }

    case class Item(price: Int, indexes: IndexedSeq[Int])

    // queries per price sorted by price
    val qppSorted: Seq[Item] = qpp
      .map { case (price, indexes) => Item(price, indexes.toIndexedSeq) }
      .toIndexedSeq
      .sorted(Ordering.by((x: Item) => x.price))

    import scala.collection.mutable
    def update(map: mutable.TreeMap[Int, Int], idx: Int): Unit = {

      def doUpdate(l: Int, r: Int): Unit =
        if (l <= r) map += (l -> r)
        else map -= l

      val (left, right) = map.rangeTo(idx).last
      doUpdate(left, idx - 1)
      doUpdate(idx + 1, right)
    }

    case class Bound(l: Int, r: Int)
    val minBounds = Array.ofDim[Bound](n)
    val minBoundMap = mutable.TreeMap[Int, Int](0 -> (n - 1))

    // TODO: make it more functional
    /** update minBoundMap, minBounds */
    qppSorted
      .flatMap { x =>
        val idxBound = x.indexes.map { i =>
          val (l, r) = minBoundMap.rangeTo(i).last
          i -> Bound(l, r)
        }
        idxBound.indices.foreach(i => update(minBoundMap, x.indexes(i)))
        idxBound
      }
      .foreach { case (index, bound) => minBounds(index) = bound }

    val maxBounds = Array.ofDim[Int](queries.length)
    val maxBoundMap = mutable.TreeMap[Int, Int](0 -> (n - 1))

    // TODO: make it more functional
    /** update minBoundMap, minBounds */
    var idx = qppSorted.length - 1
    queries
      .sortBy(_.up)(Ordering.Int.reverse)
      .map { q =>
        while (idx >= 0 && qppSorted(idx).price > q.up) {
          qppSorted(idx).indexes.foreach(update(maxBoundMap, _))
          idx -= 1
        }
        val (l, r) = maxBoundMap.rangeTo(q.d).last
        val minBound = minBounds(q.d)
        q.index -> (math.min(minBound.r, r) - math.max(minBound.l, l) + 1)

      }
      .foreach { case (index, answer) => maxBounds(index) = answer }

    maxBounds
  }

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val stocks = next().split(" ").map(_.toInt).take(n)
    val m = next().toInt
    val queries = (1 to m).map { i =>
      next().split(" ").map(_.toInt) match { case Array(d, m) => Query(i - 1, d, stocks(d) + m) }
    }

    solve(stocks, queries, n)
      .foreach(println)
  }
}
