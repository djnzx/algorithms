package djnz.hackerrank.fp.a3structures

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/kmp-fp/problem
  *
  * Knuth–Morris–Pratt algorithm
  * https://en.wikipedia.org/wiki/Knuth–Morris–Pratt_algorithm
  */
object P6SubstringKMP {

  def next() = scala.io.StdIn.readLine()

  def buildMeta(word: String): Vector[Int] = {
    // mutable
    val meta = Array.fill[Int](word.length + 1)(-1)
    // mutable
    var pos = 1
    // mutable
    var cnd = 0

    while (pos < word.length) {
      if (word(pos) == word(cnd)) {
        /////////
        meta(pos) = meta(cnd)
      } else {
        /////////
        meta(pos) = cnd
        while (cnd >= 0 && word(pos) != word(cnd))
          cnd = meta(cnd)
      }
      pos += 1
      cnd += 1
    }

    meta(pos) = cnd
    meta.toVector
  }

  def solve(raw: String, word: String): Boolean = {
    val meta: Vector[Int] = buildMeta(word)
    // mutable
    var j = 0 // source (S) index
    // mutable
    var k = 0 // pattern (W) index
    // mutable
    val indexes = scala.collection.mutable.ListBuffer.empty[Int]

    // search using index (built with meta)
    while (j < raw.length)
      if (word(k) == raw(j)) {
        j += 1
        k += 1
        if (k == word.length) {
          indexes += j - k
          k = meta(k)
        }
      } else {
        k = meta(k)
        if (k < 0) {
          j += 1
          k += 1
        }
      }

    indexes.nonEmpty
  }

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    (1 to n)
      .map { _ =>
        val src = next()
        val w = next()
        solve(src, w)
      }
      .map(if (_) "YES" else "NO")
      .foreach(println)
  }
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
        val m = buildMeta(x)
        println(x -> m)
      }

  }
}
