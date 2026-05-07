package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.data.NonEmptyList
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax._
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson._

class P11cResolveSpec extends P11zSpecSupport {

  private def subst(pairs: (Id, SimpleTerm)*): Substitution =
    Substitution(Map(pairs: _*))

  test("resolve - when variable is unbound - returns it unchanged") {
    subst()
      .resolve("x".stv) shouldBe "x".stv
  }

  test("resolve - when variable is bound to a name - returns the name") {
    subst(
      "x".id -> "a".stn
    )
      .resolve("x".stv) shouldBe "a".stn
  }

  test("resolve - when chain ends at an unbound variable - returns that variable") {
    subst(
      "x".id -> "y".stv
    )
      .resolve("x".stv) shouldBe "y".stv
  }

  test("resolve - when variable is bound through another variable to a name - follows the chain") {
    subst(
      "x".id -> "y".stv,
      "y".id -> "a".stn
    )
      .resolve("x".stv) shouldBe "a".stn
  }

  test("resolve - when chain has multiple hops - follows to the end") {
    subst(
      "a".id -> "b".stv,
      "b".id -> "c".stv,
      "c".id -> "z".stn
    )
      .resolve("a".stv) shouldBe "z".stn
  }

  test("resolve - when term is a name - returns it unchanged") {
    subst(
      "a".id -> "other".stn
    )
      .resolve("a".stn) shouldBe "a".stn
  }

  test("resolve - when term is a relation - returns it unchanged without recursing into arguments") {
    val rel = StRelation("p".id, NonEmptyList.of("x".stv))
    subst(
      "x".id -> "a".stn
    ).resolve(rel) shouldBe rel
  }

  test("resolve - when variable is bound to a relation - returns the relation") {
    val rel = StRelation("p".id, NonEmptyList.of("a".stn))
    subst(
      "x".id -> rel
    ).resolve("x".stv) shouldBe rel
  }

}
