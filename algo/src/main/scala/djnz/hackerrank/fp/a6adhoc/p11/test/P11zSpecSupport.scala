package djnz.hackerrank.fp.a6adhoc.p11.test

import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.Id
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.Query
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StName
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.StVariable
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.Knowledge
import djnz.hackerrank.fp.a6adhoc.p11.Solution
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

trait P11zSpecSupport extends AnyFunSuite with Matchers {

  implicit class IdentifierOps(s: String) {
    def id = Id(s)
    def stn = StName(s.id)
    def stv = StVariable(s.id)
  }

  def parseQuery(raw: String): Query =
    Solution.parseUnsafe(raw) match {
      case q: Query => q
      case other    => fail(s"expected Query, got $other")
    }

  def buildKnowledge(lines: List[String]): Knowledge =
    Solution.handleBulk(Knowledge.initial, lines.iterator)._1

}
