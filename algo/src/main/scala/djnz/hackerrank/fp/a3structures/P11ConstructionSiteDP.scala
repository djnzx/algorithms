package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/mirko-at-construction-site/problem */
object P11ConstructionSiteDP {

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val (_, q) = next().split(" ").map(_.toInt) match { case Array(x, y) => x -> y }

    val base = next().split(" ").map(_.toInt)
    val increments = next().split(" ").map(_.toInt)

    case class Query(idx: Int, time: Int)
    val queries = (0 until q).map(i => Query(i, next().toInt)).sortBy(_.time)

    case class Building(idx: Int, base: Long, inc: Long) extends Ordered[Building] {
      override def compare(that: Building): Int =
        if (this.inc < that.inc) -1
        else if (this.inc > that.inc) 1
        else if (this.base < that.base) -1
        else if (this.base > that.base) 1
        else this.idx.compareTo(that.idx)
    }

    val buildings: List[Building] = base.indices
      .map(i => Building(i + 1, base(i), increments(i)))
      .groupBy(_.inc)
      .map { case (_, list) => list.maxBy(_.base) }
      .toList
      .sorted

    def cross(b0: Building, b1: Building) = (b1.base.toDouble - b0.base) / (b0.inc.toDouble - b1.inc)

    @scala.annotation.tailrec
    def sort(buildings: List[Building], acc: List[Building]): List[Building] = (buildings, acc) match {
      case (Nil, _)            => acc              // done
      case (v :: Nil, _)       => v :: acc         // last one, done
      case (h :: t, Nil)       => sort(t, List(h)) // start
      case (b1 :: b2 :: bs, h :: _) if {
            val is1 = cross(h, b1)
            val is2 = cross(h, b2)
            is1 < is2 || is1 == is2 && b1.idx > b2.idx
          } => sort(b2 :: bs, b1 :: acc)
      case (_ :: bs, _ :: Nil) => sort(bs, acc)
      case (_ :: bs, h :: hs)  => sort(h :: bs, hs)
    }

    val buildingsReordered = sort(buildings, Nil).reverse

    case class Pair(qIdx: Int, highestIdx: Int)
    case class State(buildings: List[Building], highest: List[Pair])
    val s0 = State(buildingsReordered, Nil)

    queries
      .foldLeft(s0) { (s, q) =>

        @scala.annotation.tailrec
        def highest(buildings: List[Building]): State = buildings match {
          case b :: Nil => State(s.buildings, Pair(q.idx, b.idx) :: s.highest)
          case b1 :: b2 :: _ if {
                val is = cross(b1, b2)
                is > q.time || is == q.time && b1.idx > b2.idx
              } => State(buildings, Pair(q.idx, b1.idx) :: s.highest)
          case _ :: bs  => highest(bs)
          case Nil      => sys.error("impossible, but req for exhaustiveness")
        }

        highest(s.buildings)
      }
      .highest
      .sortBy(_.qIdx)
      .map(_.highestIdx)
      .foreach(println)
  }

}
