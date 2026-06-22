package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.SimpleTerm
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StVariable
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.QueryCompiler
import djnz.hackerrank.fp.a6adhoc.p11.SolutionRepresentation._

class P11Spec01MysterySimple extends P11SpecSupport {

  private val mysteryProgram = ra.file("in/input01.txt")

  test("mystery simple suspect queries") {
    val knowledge = buildKnowledge(mysteryProgram)

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([suspect: Jeffrey])?"))) shouldBe
      none[Vector[Vector[(StVariable, SimpleTerm)]]]

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([suspect: Susan])?"))) shouldBe
      none[Vector[Vector[(StVariable, SimpleTerm)]]]

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([suspect: #X])?"))) shouldBe
      Vector(
        Vector("X".stv -> "ColTravis".stn),
        Vector("X".stv -> "Mordred".stn)
      ).some
  }

  test("mystery simple non-matching clauses backtrack instead of aborting") {
    val knowledge = buildKnowledge(mysteryProgram)

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([suspect: Jeffrey])?"))) shouldBe
      none[Vector[Vector[(StVariable, SimpleTerm)]]]
  }

  test("mystery simple suspect rendering") {
    val outcome: Option[Vector[Vector[(StVariable, SimpleTerm)]]] = Vector(
      Vector("X".stv -> "ColTravis".stn),
      Vector("X".stv -> "Mordred".stn)
    ).some

    showOutcome.show(outcome) shouldBe
      """SAT:
        |=====
        |#X := ColTravis
        |SAT:
        |=====
        |#X := Mordred
        |Ready.""".stripMargin
  }
}
