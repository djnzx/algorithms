package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.data.NonEmptyList
import cats.implicits.catsSyntaxEitherId
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax
import djnz.hackerrank.fp.a6adhoc.p11.ParsersCore

class P11aParserTermsSpec extends P11zSpecSupport {

  import InputSyntax._
  import ParsersCore.ParserImpl
  import ParsersCore.ParserImpl._

  private def rel(name: String, terms: SimpleTerm*): StRelation =
    StRelation(name.id, NonEmptyList.fromListUnsafe(terms.toList))

  test("identifier") {
    Id.parser.run("a") shouldBe "a".id.asRight
    Id.parser.run("a1") shouldBe "a1".id.asRight
    Id.parser.run("a-b") shouldBe "a-b".id.asRight
    Id.parser.run("a-b-c") shouldBe "a-b-c".id.asRight
    Id.parser.run("ColTravis") shouldBe "ColTravis".id.asRight
  }

  test("StName") {
    StName.parser.run("a") shouldBe "a".stn.asRight
  }

  test("StVariable") {
    StVariable.parser.run("#a") shouldBe "a".stv.asRight
  }

  test("StRelationTerm") {
    StRelation.parser.run("[a: b]") shouldBe
      rel("a", "b".stn).asRight

    StRelation.parser.run("[a: #x, b]") shouldBe
      rel("a", "x".stv, "b".stn).asRight

    StRelation.parser.run("[a: [b: c], #x]") shouldBe
      rel("a", rel("b", "c".stn), "x".stv).asRight

    StRelation.parser.run("[loves: ColTravis, Martha]") shouldBe
      rel("loves", "ColTravis".stn, "Martha".stn).asRight

    StRelation.parser.run("[spouse-of: ColTravis, Martha]") shouldBe
      rel("spouse-of", "ColTravis".stn, "Martha".stn).asRight
  }

  test("many") {
    val p = ParserImpl.char('z').many
    val x = p.run("zzz")
    pprint.log(x)
  }

  test("nelcs") {
    val p2 = nelcs(char('z')).run("z, z, z")
    pprint.log(p2)
    val p3 = nelcs(Id.parser).run("z")
    pprint.log(p3)
  }

  test("nullcs") {
    val x1 = nullcs(StName.parser).run("")
    pprint.log(x1)

    val x2 = nullcs(StName.parser).run("a")
    pprint.log(x2)

    val x3 = nullcs(StName.parser).run("a, b, c")
    pprint.log(x3)
  }

  test("SimpleTerm") {
    Seq(
      "a-b",
      "#a",
      "[a: b]",
      "[a: #b]",
      "[a: #b, c]",
      "[a: #b, c, [d: e, #f]]",
    )
      .map(SimpleTerm.parser.run)
      .foreach {
        case Right(x) => pprint.log(x)
        case Left(x)  => pprint.err.log(x)
      }
  }

  test("EqualityAssertion") {
    EqualityAssertion.parser.run("<a = #b>") shouldBe
      EqualityAssertion("a".stn, "b".stv).asRight

    EqualityAssertion.parser.run("<[p: a] = [p: #x, y]>") shouldBe
      EqualityAssertion(
        rel("p", "a".stn),
        rel("p", "x".stv, "y".stn)
      ).asRight
  }

  test("NonEqualityAssertion") {
    NonEqualityAssertion.parser.run("<a /= #b>") shouldBe
      NonEqualityAssertion("a".stn, "b".stv).asRight

    NonEqualityAssertion.parser.run("<[p: a] /= [p: #x]>") shouldBe
      NonEqualityAssertion(
        rel("p", "a".stn),
        rel("p", "x".stv)
      ).asRight
  }

  test("ComplexTerm") {
    val ct1 = ComplexTerm.parser.run("a")
    pprint.log(ct1)
    ct1 shouldBe "a".stn.asRight

    val ct2 = ComplexTerm.parser.run("#ab")
    pprint.log(ct2)
    ct2 shouldBe "ab".stv.asRight

    val ct3 = ComplexTerm.parser.run("<a = #b>")
    pprint.log(ct3)
    ct3 shouldBe EqualityAssertion("a".stn, "b".stv).asRight

    val ct4 = ComplexTerm.parser.run("<a /= #b>")
    pprint.log(ct4)
    ct4 shouldBe NonEqualityAssertion("a".stn, "b".stv).asRight
  }
}
