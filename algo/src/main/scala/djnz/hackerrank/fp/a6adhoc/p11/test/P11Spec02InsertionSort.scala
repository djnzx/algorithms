package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.data.NonEmptyList
import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.SimpleTerm
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StRelation
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.QueryCompiler

class P11Spec02InsertionSort extends P11SpecSupport {

  private def cons(head: SimpleTerm, tail: SimpleTerm): StRelation =
    StRelation("cons".id, NonEmptyList.of(head, tail))

  private val insertionSortProgram = ra.file("in/input02.txt")

  test("insertion sort sample queries") {
    val knowledge = buildKnowledge(insertionSortProgram)

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([lt: #x, #y])?"))) shouldBe
      Vector(
        Vector("x".stv -> "a".stn, "y".stv -> "b".stn),
        Vector("x".stv -> "b".stn, "y".stv -> "c".stn),
        Vector("x".stv -> "c".stn, "y".stv -> "d".stn),
        Vector("x".stv -> "d".stn, "y".stv -> "e".stn),
        Vector("x".stv -> "a".stn, "y".stv -> "c".stn),
        Vector("x".stv -> "a".stn, "y".stv -> "d".stn),
        Vector("x".stv -> "a".stn, "y".stv -> "e".stn),
        Vector("x".stv -> "b".stn, "y".stv -> "d".stn),
        Vector("x".stv -> "b".stn, "y".stv -> "e".stn),
        Vector("x".stv -> "c".stn, "y".stv -> "e".stn)
      ).some

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([sorted: [cons: a, [cons: b, nil]], #z])?"))) shouldBe
      Vector(
        Vector("z".stv -> cons("a".stn, cons("b".stn, "nil".stn)))
      ).some

    knowledge.query(QueryCompiler.queryToGoals(parseQuery("([sorted: [cons: b, [cons: d, [cons: a, [cons: b, [cons: c, [cons: e, nil]]]]]], #z])?"))) shouldBe
      Vector(
        Vector("z".stv -> cons("a".stn, cons("b".stn, cons("b".stn, cons("c".stn, cons("d".stn, cons("e".stn, "nil".stn)))))))
      ).some
  }
}
