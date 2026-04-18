package djnz.hackerrank.fp.a6adhoc.p11

import cats.data.NonEmptyList
import cats.implicits.catsSyntaxOptionId
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/** https://www.hackerrank.com/challenges/elementary-watson/problem = 200 */
// pure Horn clause logic with explicit disequality constraints
object P11 {

  def main(args: Array[String]): Unit = {
    println("coming soon...")
  }

}

object Model {

  case class Name(value: String) extends AnyVal

  sealed trait Op

  case object QuitCommand extends Op

  sealed trait NoOp extends Op
  case object NoOpEmptyLine extends NoOp
  case class NoOpComment(value: String) extends NoOp

  sealed trait Rule extends Op
  case class RuleFact(st: SimpleTerm) extends Rule
  case class RuleComplex(from: List[ComplexTerm], to: SimpleTerm) extends Rule

  case class Query(terms: List[ComplexTerm]) extends Op

  sealed trait ComplexTerm
  case class EqualityAssertion(lt: SimpleTerm, rt: SimpleTerm) extends ComplexTerm
  case class NonEqualityAssertion(lt: SimpleTerm, rt: SimpleTerm) extends ComplexTerm

  sealed trait SimpleTerm extends ComplexTerm
  case class StName(name: Name) extends SimpleTerm
  case class StVariable(name: Name) extends SimpleTerm
  case class StRelationTerm(name: Name, terms: NonEmptyList[SimpleTerm]) extends SimpleTerm

}

object ParsersInst {
  import Parsers.Parser
  import Parsers.ParserImpl._
  val identifier: Parser[String] = regex("[a-z][a-zA-Z0-9\\-]*".r)

}

class P11Spec extends AnyFunSuite with Matchers {

  case class Assignment(variable: String, value: String)
  object Assignment {
    def parse(raw: String): Option[Assignment] = {
      import Parsers.ParserImpl
      import Parsers.ParserImpl._

      val parser = ((identifier <* char('=')) ** number)
        .map { case (a, b) => Assignment(a, b.toString) }
      ParserImpl.run(parser.opt)(raw).toOption.flatten
    }
  }

  test("1") {
    Assignment.parse("a=1") shouldBe Assignment("a", "1").some
    Assignment.parse("a_123b=345") shouldBe Assignment("a_123b", "345").some
    Assignment.parse("a123=") shouldBe None
  }

}
