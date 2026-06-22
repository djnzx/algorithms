package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.SimpleTerm
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StVariable
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.QueryCompiler
import djnz.hackerrank.fp.a6adhoc.p11.SolutionRepresentation._

class P11SpecApp extends P11SpecSupport {

  test("1") {
    val in = ra.file("input01.txt")
    val out = ra.file("output01.txt")

    val knowledge = buildKnowledge(in)

    ???

  }

}
