package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/different-ways-fp/problem */
object P6DifferentWays {

  def next() = scala.io.StdIn.readLine()

  case class NK(n: Int, k: Int)
  type BD = java.math.BigDecimal
  val modulo = new BD(100000007)

  val bd1 = new BD(1)
  type Cache = Map[NK, BD]
  val cache0 = Map.empty[NK, BD]

  def countWays(nk: NK, cache: Cache): (BD, Cache) = {
    val cache4 = cache.get(nk) match {
      case Some(_)                        => cache
      case _ if nk.k == 0 || nk.k == nk.n => cache + (nk -> bd1)
      case _                              =>
        val (a, cache2) = countWays(NK(nk.n - 1, nk.k - 1), cache)
        val (b, cache3) = countWays(NK(nk.n - 1, nk.k), cache2)
        val c = a.add(b)
        cache3 + (nk -> c)
    }
    cache4(nk) -> cache4
  }

  case class State(list: List[Int], cache: Cache)

  def process(cases: Seq[NK]): List[Int] =
    cases
      .foldLeft(State(List.empty, cache0)) { (acc, a) =>
        countWays(a, acc.cache) match {
          case (rbd, newCache) =>
            State(rbd.remainder(modulo).intValueExact :: acc.list, newCache)
        }
      }
      .list
      .reverse

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val cases = (1 to n)
      .map(_ => next().split(" "))
      .map(_.map(_.toInt) match { case Array(n, k) => NK(n, k) })

    process(cases)
      .foreach(println)
  }
}
