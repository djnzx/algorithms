package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.data.NonEmptyList
import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.SimpleTerm
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StRelation
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.QueryCompiler

class P11yInsertionSortSpec extends P11zSpecSupport {

  private def cons(head: SimpleTerm, tail: SimpleTerm): StRelation =
    StRelation("cons".id, NonEmptyList.of(head, tail))

  private val insertionSortProgram = List(
    "[lt-n: a, b].",
    "[lt-n: b, c].",
    "[lt-n: c, d].",
    "[lt-n: d, e].",
    "{([lt-n: #x, #y]) => [lt: #x, #y]}.",
    "{([lt-n: #x, #y], [lt: #y, #z]) => [lt: #x, #z]}.",
    "{([lt: #x, #y]) => [ge: #y, #x]}.",
    "{(<#x = #y>) => [ge: #x, #y]}.",
    "[sorted: nil, nil].",
    "{([sorted: #t, #y], [insert: #h, #y, #z]) => [sorted: [cons: #h, #t], #z]}.",
    "[insert: #h, nil, [cons: #h, nil]].",
    "{([lt: #h, #h2]) => [insert: #h, [cons: #h2, #t], [cons: #h, [cons: #h2, #t]]]}.",
    "{([ge: #h, #h2], [insert: #h, #t, #z]) => [insert: #h, [cons: #h2, #t], [cons: #h2, #z]]}."
  )

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
