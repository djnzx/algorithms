package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/kmp-fp/problem
  *
  * Knuth–Morris–Pratt algorithm
  * https://en.wikipedia.org/wiki/Knuth–Morris–Pratt_algorithm
  */
object P6SubstringKMP {

  def next() = scala.io.StdIn.readLine()

  /** find the longer proper suffix */
  def buildLps(w: String): IndexedSeq[Int] = {
    val n = w.length
    val lps = Array.ofDim[Int](n)
    lps(0) = 0

    @scala.annotation.tailrec
    def go(i: Int, len: Int): Unit =
      if (i < n) {
        val (i2, len2) = () match {
          case _ if w(i) == w(len) => (i + 1, len + 1)
          case _ if len == 0       => (i + 1, 0)
          case _                   => (i, lps(len - 1))
        }

        lps(i) = len2
        go(i2, len2)
      }

    go(1, 0)
    lps.toIndexedSeq
  }

  def search(pat: String, txt: String): List[Int] = {
    val lps = buildLps(pat)

    @scala.annotation.tailrec
    def go(i: Int, j: Int, acc: List[Int]): List[Int] = () match {
      case _ if i >= txt.length => acc
      case _                    =>
        val (i2, j2) =
          if (pat(j) == txt(i)) (i + 1, j + 1)
          else if (j == 0) (i + 1, 0)
          else (i, lps(j - 1))

        if (j2 == pat.length)
          go(i2, lps(j2 - 1), (i2 - j2) :: acc)
        else
          go(i2, j2, acc)
    }

    go(0, 0, Nil)
  }

  def main(args: Array[String]): Unit =
    (1 to next().toInt)
      .map { _ =>
        val s = next()
        val w = next()
        search(w, s)
      }
      .map(x => if (x.isEmpty) "NO" else "YES")
      .foreach(println)
}

class P6SubstringKMP extends ASuite {
  import P6SubstringKMP._

  test("1") {
    Seq(
      "AAAAAAAA",
      "ABBBBBBB",
      "AAAAAAAB",
      "ABCDEFGH",
      "AABBCC",
      "ABCDEDCBA",
      "ACEFECA",
      "ABCDABD",
    )
      .foreach { x =>
        val m = buildLps(x)
        println(x -> m)
      }

  }
}
