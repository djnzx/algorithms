package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.data.NonEmptyList
import cats.implicits.catsSyntaxEitherId
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax

class P11bParserOpsSpec extends P11zSpecSupport {

  import InputSyntax._

  test("noop-emptyline") {
    NoOpEmptyLine.parser.run("") shouldBe NoOpEmptyLine.asRight
  }

  test("noop-comment") {
    NoOpComment.parser.run("% todo") shouldBe NoOpComment(" todo").asRight
  }

  test("noop") {
    NoOp.parser.run("") shouldBe NoOpEmptyLine.asRight
    NoOp.parser.run("% blah") shouldBe NoOpComment(" blah").asRight
  }

  test("quit") {
    QuitCommand.parser.run("quit!") shouldBe QuitCommand.asRight
  }

  test("rule-fact") {
    Rule.Fact.parser.run("a.") shouldBe
      Rule.Fact("a".stn).asRight

    Rule.Fact.parser.run("[p: #x, y].") shouldBe
      Rule.Fact(
        StRelation("p".id, NonEmptyList.of("x".stv, "y".stn))
      ).asRight
  }

  test("rule-complex") {
    val x1 = Rule.Complex.parser.run("{() => b}.")
    pprint.log(x1)

    val x2 = Rule.Complex.parser.run("{(a, [b: c], #c) => d}.")
    pprint.log(x2)

    val x3 = Rule.Complex.parser.run("{([q: y], <y /= #x>) => [p: #x]}.")
    pprint.log(x3)
  }

  test("rule.RuleFact.StRelationTerm") {
    Rule.parser.run("[crime-scene: CountryHouse].") shouldBe Rule.Fact(
      StRelation("crime-scene".id, NonEmptyList.of(StName("CountryHouse".id)))
    ).asRight

    Rule.parser.run("[loves: ColTravis, Martha].") shouldBe Rule.Fact(
      StRelation(
        "loves".id,
        NonEmptyList.of(StName("ColTravis".id), StName("Martha".id))
      )
    ).asRight

    Rule.parser.run("[spouse-of: ColTravis, Martha].") shouldBe Rule.Fact(
      StRelation(
        "spouse-of".id,
        NonEmptyList.of(StName("ColTravis".id), StName("Martha".id))
      )
    ).asRight
  }

  test("rule.RuleComplex") {
    Rule.parser.run("{(a, #b) => c}.") shouldBe
      Rule.Complex(List("a".stn, "b".stv), "c".stn).asRight
  }

  test("query") {
    Query.parser.run("()?") shouldBe
      Query(Nil).asRight

    Query.parser.run("(a)?") shouldBe
      Query(List("a".stn)).asRight

    Query.parser.run("([q: y], <y /= #x>, #z)?") shouldBe
      Query(
        List(
          StRelation("q".id, NonEmptyList.of("y".stn)),
          NonEqualityAssertion("y".stn, "x".stv),
          "z".stv
        )
      ).asRight
  }

  test("op") {
    Op.parser.run("quit!") shouldBe QuitCommand.asRight
    Op.parser.run("% comment") shouldBe NoOpComment(" comment").asRight
    Op.parser.run("a.") shouldBe Rule.Fact(StName("a".id)).asRight
    Op.parser.run("(a)?") shouldBe Query(List(StName("a".id))).asRight
  }
}
