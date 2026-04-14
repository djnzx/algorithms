package djnz.hackerrank.fp.a5persistent

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/** https://www.hackerrank.com/challenges/minimum-multiple/problem */
object P1MinimumMultipleSlow {
  import scala.collection.immutable.LazyList.#::
  private type Fac = Map[Int, Int]

  def next() = scala.io.StdIn.readLine()
  val modulo = 1_000_000_007

  // P99-31
  def isPrime(x: Int): Boolean = (x > 1) && primes.takeWhile(_ <= math.sqrt(x.toDouble)).forall(x % _ != 0)
  val primes: LazyList[Int] = 2 #:: LazyList.from(3, 2).filter(isPrime)

  def primeFactors(n: Int): Fac = {

    // P99-35
    def go(n: Int, primes: LazyList[Int], factors: List[Int]): List[Int] = (n, primes) match {
      case (1, _) => factors.sorted
      case (_, p #:: _) if n % p == 0 => go(n / p, primes, p :: factors)
      case (_, _ #:: ps) => go(n, ps, factors)
    }

    go(n, primes, Nil)
      .groupMapReduce(identity)(_ => 1)(_ + _)
  }

  // for lowest common multiplier
  def mergeMaxVal(a: Fac, b: Fac): Fac =
    a.foldLeft(b) { case (acc, (p, e)) =>
      acc.get(p) match {
        case Some(e2) if e2 >= e => acc
        case _                   => acc.updated(p, e)
      }
    }

  // for multiplying numbers
  def addExponents(a: Fac, b: Fac): Fac =
    a.foldLeft(b) { case (acc, (p, e)) =>
      acc.updated(p, acc.getOrElse(p, 0) + e)
    }

  def powMod(base0: Long, exp0: Int): Long = {

    @annotation.tailrec
    def go(b: Long, e: Int, r: Long): Long =
      if (e == 0) r
      else {
        val b2 = (b * b) % modulo
        val e2 = e >> 1

        if ((e & 1) == 1)
          go(b2, e2, (r * b) % modulo)
        else
          go(b2, e2, r)
      }

    val b0 = Math.floorMod(base0, modulo)
    go(b0, exp0, 1L)
  }

  def lcmValue(f: Fac): Int =
    f.foldLeft(1L) { case (acc, (p, e)) => (acc * powMod(p.toLong, e)) % modulo }.toInt

  trait Monoid[A] {
    def empty: A
    def combine(a1: A, a2: A): A
  }

  sealed trait ST[G] {
    def agg: G
  }
  case class Leaf[G](idx: Int, agg: G) extends ST[G]
  case class Node[G](idxL: Int, idxR: Int, agg: G, l: ST[G], r: ST[G]) extends ST[G]
  object ST {
    def build[A, G](xs: IndexedSeq[A], f: A => G)(implicit mg: Monoid[G]): ST[G] = {

      def go(idxL: Int, idxR: Int): ST[G] =
        if (idxL == idxR)
          Leaf(idxL, f(xs(idxL)))
        else {
          val m = (idxL + idxR) >> 1
          val l = go(idxL, m)
          val r = go(m + 1, idxR)
          Node(idxL, idxR, mg.combine(l.agg, r.agg), l, r)
        }

      go(0, xs.length - 1)
    }
  }

  def doMultiply(qIdx: Int, m: Int): ST[Fac] => ST[Fac] = {
    def go: ST[Fac] => ST[Fac] = {
      case Leaf(`qIdx`, agg)                                         => Leaf(qIdx, addExponents(agg, primeFactors(m)))
      case Node(idxL, idxR, _, l, r) if idxL <= qIdx && qIdx <= idxR =>
        val l2 = go(l)
        val r2 = go(r)
        Node(idxL, idxR, mergeMaxVal(l2.agg, r2.agg), l2, r2)
      case x                                                         => x
    }
    go
  }

  def query(ql: Int, qr: Int)(implicit mg: Monoid[Fac]): ST[Fac] => Fac = {
    def go(st: ST[Fac]): Fac = st match {
      case Leaf(idx, agg) if ql <= idx && idx <= qr                => agg
      case Leaf(_, _)                                              => mg.empty
      case Node(idxL, idxR, _, _, _) if qr < idxL || ql > idxR     => mg.empty
      case Node(idxL, idxR, agg, _, _) if ql <= idxL && idxR <= qr => agg
      case Node(_, _, _, stL, stR)                                 => mg.combine(go(stL), go(stR))
    }
    go
  }

  implicit val mf0: Monoid[Fac] = new Monoid[Fac] {
    def empty: Fac = Map.empty
    def combine(a1: Fac, a2: Fac): Fac = mergeMaxVal(a1, a2)
  }

  def doQuery(l: Int, r: Int): ST[Fac] => ST[Fac] =
    st => {
      val ff = (query(l, r) _)(st)
      val aw = lcmValue(ff)
      println(aw)
      st
    }

  sealed trait Cmd
  case class Q(l: Int, r: Int) extends Cmd
  case class U(idx: Int, m: Int) extends Cmd
  object Cmd {
    def parse(raw: String): Cmd =
      raw.trim.split("\\s+") match {
        case Array("Q", l, r)   => Q(l.toInt, r.toInt)
        case Array("U", idx, m) => U(idx.toInt, m.toInt)
      }
  }

  def handleCmd(st: ST[Fac], cmd: Cmd): ST[Fac] = cmd match {
    case U(idx, m) => doMultiply(idx, m)(st)
    case Q(l, r)   => doQuery(l, r)(st)
  }

  def lazySeq(n: Int) =
    Iterator.unfold(n) {
      case 0 => None
      case n => Some(() -> (n - 1))
    }

  def main(args: Array[String]): Unit = {
    val _ = next()
    val data = next().split("\\s").map(_.toInt)
    val st = ST.build(data, primeFactors)

    lazySeq(next().toInt)
      .map(_ => next())
      .map(Cmd.parse)
      .foldLeft(st)(handleCmd)
  }

}

class P1MinimumMultipleTest extends AnyFunSuite with Matchers {

  import P1MinimumMultipleSlow._

  test("0") {
    lazySeq(3)
      .foreach(x => pprint.log(x))
  }

  test("2") {
    primeFactors(315) shouldBe Map(3 -> 2, 5 -> 1, 7 -> 1)
  }

  test("nc1") {
    powMod(5, 1) shouldBe 5
    powMod(3, 2) shouldBe 9
    powMod(2, 5) shouldBe 32
  }

  test("combine") {
    mergeMaxVal(Map.empty[Int, Int], Map.empty[Int, Int]) shouldBe Map.empty[Int, Int]

    mergeMaxVal(Map.empty[Int, Int], Map(1 -> 10)) shouldBe Map(1 -> 10)
    mergeMaxVal(Map(1 -> 10), Map.empty[Int, Int]) shouldBe Map(1 -> 10)

    mergeMaxVal(Map(1 -> 10), Map(2 -> 5)) shouldBe Map(1 -> 10, 2 -> 5)

    mergeMaxVal(Map(1 -> 10, 2 -> 5, 3 -> 1), Map(2 -> 1, 3 -> 3)) shouldBe Map(1 -> 10, 2 -> 5, 3 -> 3)
  }

  test("mergeSum") {
    addExponents(Map.empty[Int, Int], Map.empty[Int, Int]) shouldBe Map.empty[Int, Int]
    addExponents(Map.empty[Int, Int], Map(1 -> 10)) shouldBe Map(1 -> 10)
    addExponents(Map(1 -> 10), Map.empty[Int, Int]) shouldBe Map(1 -> 10)
    addExponents(Map(1 -> 10), Map(2 -> 5)) shouldBe Map(1 -> 10, 2 -> 5)
    addExponents(Map(1 -> 10, 2 -> 5, 3 -> 1), Map(2 -> 1, 3 -> 3)) shouldBe Map(1 -> 10, 2 -> 6, 3 -> 4)
  }

}
