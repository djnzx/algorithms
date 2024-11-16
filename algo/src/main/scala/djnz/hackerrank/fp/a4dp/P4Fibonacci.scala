package djnz.hackerrank.fp.a4dp

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/fibonacci-fp/problem */
object P4Fibonacci {

  def next() = scala.io.StdIn.readLine()

  // TODO: what's wrong with scala BigDecimal ?
  type BD = java.math.BigDecimal
  val bd0 = new BD(0)
  val bd1 = new BD(1)
  val modulo = new BD(100000007)

  def makeAllFibosUpToN_array(total: Int): Array[BD] = {
    val fibos = Array.fill[BD](total)(null)

    def fibo(n: Int): BD = n match {
      case 0 => fibos(n) = bd0; fibos(n)
      case 1 => fibos(n) = bd1; fibos(n)
      case _ =>
        if (fibos(n - 1) == null) fibos(n - 1) = fibo(n - 1)
        if (fibos(n - 2) == null) fibos(n - 2) = fibo(n - 2)
        fibos(n - 1).add(fibos(n - 2))
    }

    fibo(total)
    fibos
  }

  def makeAllFibosUpToN(max: Int): Map[Int, BD] =
    LazyList.unfold((0, bd0, bd1)) {
      case (n, _, _) if n > max => None
      case (n, f1, f2)          =>
        val s2 = (n + 1, f2, f1.add(f2))
        val x = n -> f1
        Some(x -> s2)
    }
      .toMap

  def solve(xs: Seq[Int]) = {
    val all = makeAllFibosUpToN(xs.max + 1)
    xs.map(x => all(x))
      .map(_.remainder(modulo))
      .map(_.intValueExact)
  }

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val xs = (1 to n)
      .map(_ => next().toInt)

    solve(xs)
      .foreach(println)
  }

}

class P4Fibonacci extends ASuite {
  import P4Fibonacci._

  test("array") {
    makeAllFibosUpToN_array(10)
      .foreach(println)
  }

  test("1") {
    makeAllFibosUpToN(10)
      .toList
      .sorted
      .foreach(println)
  }
}
