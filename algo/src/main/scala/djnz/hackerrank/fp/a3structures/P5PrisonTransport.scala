package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/prison-transport/problem
  * https://cp-algorithms.com/data_structures/disjoint_set_union.html
  */
object P5PrisonTransport {

  def next() = scala.io.StdIn.readLine()

  def price1g(n: Int): Int = math.ceil(math.sqrt(n)).toInt
  def priceNg(xs: Iterable[Int]): Int = xs.foldLeft(0)((a, x) => a + price1g(x))
  def price(n: Int, xs: Iterable[Int]): Int = n - xs.sum + priceNg(xs)

  case class Tree(id: Int, links: Set[Int])

  def mkLinks(xs: Seq[(Int, Int)]) =
    (xs ++ xs.map { case (x, y) => (y, x) })
      .groupMap(_._1)(_._2)
      .map { case (k, ls) => k -> Tree(k, ls.toSet) }

  def split(links: Map[Int, Tree]): List[Int] = {

    def without(links: Map[Int, Tree], p: Tree): Map[Int, Tree] = {
      val l0 = links - p.id
      p.links.foldLeft(l0) { (links, id) =>
        links.updated(id, Tree(id, links(id).links - p.id))
      }
    }

    case class State(links: Map[Int, Tree], len: Int)

    def extractChain(links: Map[Int, Tree], p: Tree): State =
      p.links
        .foldLeft(State(without(links, p), 1)) {
          case (acc, id) if acc.links.contains(id) =>
            val next = extractChain(acc.links, acc.links(id))
            State(next.links, acc.len + next.len)
          case (acc, _)                            => acc
        }

    @scala.annotation.tailrec
    def go(links: Map[Int, Tree], lengths: List[Int]): List[Int] = links match {
      case m if m.isEmpty => lengths
      case _              =>
        val p = links.values.head
        val next = extractChain(links, p)
        go(next.links, next.len :: lengths)
    }

    go(links, Nil)
  }

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val nPairs = next().toInt
    val pairs = (1 to nPairs)
      .map(_ =>
        next().split(" ").map(_.toInt) match {
          case Array(a, b) => (a, b)
          case _           => ???
        }
      )

    val links: Map[Int, Tree] = mkLinks(pairs)
    val chains: List[Int] = split(links)
    val outcome: Int = price(n, chains)
    println(outcome)
  }
}
