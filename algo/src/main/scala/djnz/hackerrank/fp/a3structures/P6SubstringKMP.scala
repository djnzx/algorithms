package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/kmp-fp/problem
  *
  * Knuth–Morris–Pratt algorithm
  * https://en.wikipedia.org/wiki/Knuth–Morris–Pratt_algorithm
  */
object P6SubstringKMP {

  def next() = scala.io.StdIn.readLine()

  def buildLps(w: String): IndexedSeq[Int] = {
    val n = w.length
    val lps = Array.ofDim[Int](n)
    lps(0) = 0

    @scala.annotation.tailrec
    def go(i: Int, len: Int): Unit =
      if (i < n) {
        val (nextI, nextLen) =
          if (w(i) == w(len)) (i + 1, len + 1)
          else if (len == 0) (i + 1, 0)
          else (i, lps(len - 1))

        lps(i) = nextLen
        go(nextI, nextLen)
      }

    go(1, 0)
    lps.toIndexedSeq
  }

  def search(pat: String, txt: String): List[Int] = {
    val lps = buildLps(pat)

    @scala.annotation.tailrec
    def go(i: Int, j: Int, acc: List[Int]): List[Int] =
      if (i < txt.length) {
        val (nextI, nextJ) =
          if (pat(j) == txt(i)) (i + 1, j + 1)
          else if (j == 0) (i + 1, 0)
          else (i, lps(j - 1))

        val found = nextJ == pat.length
        val xx = if (found) lps(nextJ - 1) else nextJ
        val yy = if (found) nextI - nextJ :: acc else acc
        go(nextI, xx, yy)
      } else acc

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
