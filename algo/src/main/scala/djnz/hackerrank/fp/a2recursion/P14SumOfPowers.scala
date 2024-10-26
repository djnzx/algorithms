package djnz.hackerrank.fp.a2recursion

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/functional-programming-the-sums-of-powers/problem */
object P14SumOfPowers {

  /** n^p^ */
  def pow(n: Int, p: Int): Int = (1 to p).foldLeft(1)((a, _) => a * n)
  case class NP(n: Int, np: Int) // (n, n^p)
  def maxPart(n: Int) = math.ceil(math.sqrt(n)).toInt
  def mkAllPowers(max: Int, n: Int) = (1 to max).map(x => NP(x, pow(x, n))).toList

  /** unique way to split po to powers x1^p^+x2^p^+...xn^p^ */
  def calcWays(X: Int, P: Int) = {
    val allPowers = mkAllPowers(maxPart(X), P)

    def split(options: List[NP], sub: Int, acc: List[Int]): List[Option[List[Int]]] =
      if (sub > X) List(None)
      else (sub - X, options) match {
        case (0, _)                        => List(Some(acc))
        case (_, Nil)                      => List(None)
        case (_, h :: t) if sub + h.np > X => split(t, sub, acc)
        case (_, h :: t)                   => split(t, sub + h.np, h.n :: acc) ::: split(t, sub, acc)
      }

    split(allPowers, 0, Nil).flatten
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val x = next().toInt
    val n = next().toInt
    val ways = calcWays(x, n)
    val r = ways.size
    println(r)
  }

}

class P14SumOfPowers extends ASuite {
  import P14SumOfPowers._

  test("maxPart") {
    List(10, 50, 100, 1000)
      .map(maxPart)
      .foreach(println)
  }

  test("powers") {
    List(10, 100, 1000)
      .map(x => mkAllPowers(maxPart(x), 2))
      .foreach(x => pprint.log(x))
  }
}
