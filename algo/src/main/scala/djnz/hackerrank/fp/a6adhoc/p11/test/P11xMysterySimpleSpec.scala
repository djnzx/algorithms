package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.SimpleTerm
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StVariable
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.QueryCompiler
import djnz.hackerrank.fp.a6adhoc.p11.SolutionRepresentation._

class P11xMysterySimpleSpec extends P11zSpecSupport {

  private val mysteryProgram = List(
    "{([means: #x], [motive: #x], [opportunity: #x]) => [suspect: #x]}.",
    "{([doctor: #x], [poisoned: #y]) => [means: #x]}.",
    "{([nurse: #x], [poisoned: #y]) => [means: #x]}.",
    "{([veterinarian: #x], [poisoned: #y]) => [means: #x]}.",
    "{([owns-firearm: #x], [shot: #y]) => [means: #x]}.",
    "{([strong: #x], [strangled: #y]) => [means: #x]}.",
    "{([jealous-of: #x, #y], [victim: #y]) => [motive: #x]}.",
    "{([inherits-from: #x, #y], [financial-trouble: #x], [victim: #y]) => [motive: #x]}.",
    "{([witnessed-by-at-on: #x, #z, #s, #t], [crime-scene: #s], [time-of-death: #t]) => [opportunity: #x]}.",
    "{([has-no-alibi-for: #x, #t], [time-of-death: #t]) => [opportunity: #x]}.",
    "{([poisoned: #x]) => [victim: #x]}.",
    "{([shot: #x]) => [victim: #x]}.",
    "{([strangled: #x]) => [victim: #x]}.",
    "{([loves: #x, #y], [loves: #z, #y]) => [jealous-of: #x, #z]}.",
    "{([son-of: #x, #y]) => [inherits-from: #x, #y]}.",
    "{([daughter-of: #x, #y]) => [inherits-from: #x, #y]}.",
    "{([spouse-of: #x, #y]) => [inherits-from: #x, #y]}.",
    "[crime-scene: CountryHouse].",
    "[time-of-death: Wednesday].",
    "[poisoned: ColTravis].",
    "ColTravis.",
    "[spouse-of: ColTravis, Martha].",
    "[loves: ColTravis, Martha].",
    "[soldier: ColTravis].",
    "[doctor: ColTravis].",
    "[owns-firearm: ColTravis].",
    "[strong: ColTravis].",
    "[witnessed-by-at-on: ColTravis, Martha, CountryHouse, Tuesday].",
    "[witnessed-by-at-on: ColTravis, Martha, CountryHouse, Wednesday].",
    "Martha.",
    "[spouse-of: Martha, ColTravis].",
    "[nurse: Martha].",
    "[has-no-alibi-for: Martha, Wednesday].",
    "Jeffrey.",
    "[son-of: Jeffrey, ColTravis].",
    "[son-of: Jeffrey, Martha].",
    "[golden-youth: Jeffrey].",
    "[owns-firearm: Jeffrey].",
    "[strong: Jeffrey].",
    "[financial-trouble: Jeffrey].",
    "[witnessed-by-at-on: Jeffrey, Mordred, CountryHouse, Wednesday].",
    "Susan.",
    "[daughter-of: Susan, ColTravis].",
    "[daughter-of: Susan, Martha].",
    "[doctor: Susan].",
    "[witnessed-by-at-on: Susan, Martha, CountryHouse, Tuesday].",
    "Mordred.",
    "[loves: Mordred, Martha].",
    "[veterinarian: Mordred].",
    "[witnessed-by-at-on: Mordred, Susan, CountryHouse, Tuesday].",
    "[has-no-alibi-for: Mordred, Wednesday]."
  )

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
