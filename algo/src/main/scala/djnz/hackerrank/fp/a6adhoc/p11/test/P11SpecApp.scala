package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.Knowledge
import djnz.hackerrank.fp.a6adhoc.p11.Solution
import djnz.hackerrank.fp.a6adhoc.p11.Solution.handleBulk

class P11SpecApp extends P11SpecSupport {

  // renumbering = requires Freshener.freshClause to rename rule variables per use; verified empirically by
  // stubbing freshClause to `clause -> n` (a no-op) and rerunning all 15 cases against expected output.
  // 00 - basic: multi-arg predicates with variable projection, plus <#a = #b> / <#a /= #b> goals in the same query
  // 01 - mystery (simple): forward-chaining suspect search; jealous-of rule has no #x/=#z guard, so Col. Travis is jealous of (and a suspect in) himself
  // 02 - insertion sort: transitive closure (lt/ge) plus recursive insert/sorted relations building nested [cons: ...] lists
  // 03 - ancestry: transitive ancestor-of plus sibling-of/cousin-of, sibling-of guarded by <#x /= #y> to exclude a person being their own sibling
  // 04 - zebra puzzle: one large rule body with dozens of shared variables, tied together via structural <#Sol = [street: ...]> equality goals
  // 05 - cabbage/goat/wolf: state search with layered Eq/Neq guards (valid/invalid/non-final) and deferred (unresolved) neq constraints
  // 06 - towers of hanoi: peano-numeral-indexed recursion producing move lists; second query is deep enough to stress recursion/output size
  // 07 - merge sort: peano arithmetic (plus/div-2) combined with Neq guards (#len /= zero, #len /= [succ: zero]) to terminate the recursion
  // 08 - multiple derivations: duplicate facts are not deduped, so one query yields one SAT per matching clause, including repeats
  // 09 - mystery (full): same case as 01, but jealous-of adds <#x /= #z>, removing the self-suspect derivation that 01 exposed
  // 10 - occurs: <[r: #x] = #y> binds #y to a structure containing #x; unifying #y directly with #x then fails the occurs check (UNSAT)
  // 11 - professions: pure constraint puzzle (all Neq/Eq, no recursion); multiple SAT solutions with variables renumbered per solution
  // 12 - ancestry (simple): a zero-premise rule {() => ...} behaves as a fact; also tests an inequality guard <a /= #y> applied to a query result
  // 13 - vacuous truth: the empty query ()? is trivially SAT with no bindings
  // 14 - SKI combinators: Turing-completeness via lambda-calculus/SKI evaluation, deeply nested [app: ...] terms and long substitution chains

  test("all ") {

    val reqRenumbering = Set(1, 2, 3, 5, 6, 7, 9, 11, 12, 14)
    val reqNonEqualityCheck = Set(0, 3, 5, 7, 9, 11, 12)
    val plain = Set(4, 8, 10, 13)

    (plain ++ reqRenumbering ++ reqNonEqualityCheck)
      .map {
        case n if n < 10 => s"0$n"
        case n           => s"$n"
      }
      .foreach { ns =>
        val kbq0 = ra.file(s"in/input$ns.txt")
        val out0 = ra.file(s"out/output$ns.txt")

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

}
