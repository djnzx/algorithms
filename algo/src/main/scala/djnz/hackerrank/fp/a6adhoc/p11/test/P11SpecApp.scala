package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.Knowledge
import djnz.hackerrank.fp.a6adhoc.p11.Solution
import djnz.hackerrank.fp.a6adhoc.p11.Solution.handleBulk

class P11SpecApp extends P11SpecSupport {

  test("1") {
    val kbq0 = ra.file("in/input01.txt")
    val out0 = ra.file("out/output01.txt")

    val (kb_in, qs_in) = kbq0.map {
      case s if s.startsWith("%")     => s.asLeft  // kb
      case s if s.startsWith("quit!") => s.asRight // questions
      case s if s.endsWith("?")       => s.asRight // questions
      case s                          => s.asLeft
    }.separateFoldable

    val (kb_out, q_out) = out0.map {
      case s if s == "Ok." => s.asLeft
      case s               => s.asRight
    }.separateFoldable

    val (k, k_out) = Solution.handleBulk(Knowledge.initial, kb_in.iterator)

    k_out shouldBe kb_out

    handleBulk(k, qs_in.iterator)._2.flatMap(_.split("\n")) shouldBe q_out

  }

}
