package djnz.hackerrank.fp.a6adhoc.p11

import cats.implicits._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/** https://www.hackerrank.com/challenges/elementary-watson/problem = 200 */
// pure Horn clause logic with explicit disequality constraints
object P11 {

  def main(args: Array[String]): Unit =
    println("coming soon...")

}

object Model {

  import Parsers.Parser
  import Parsers.ParserImpl
  import Parsers.ParserImpl._
  import cats.data._

  // Non-Empty List, comma-separated
  def nelcs[A](pa: Parser[A]): Parser[List[A]] =
    (pa ** (string(", ") *> pa).many.opt).map {
      case (a, None)     => List(a)
      case (a, Some(as)) => a :: as
    }

  case class Name(value: String) extends AnyVal
  object Name {
    // [a-z][a-zA-Z0-9\\-]*
    val parser: Parser[Name] =
      regex("[a-z][a-zA-Z0-9\\-]*".r).map(Name.apply)
  }

  sealed trait ComplexTerm
  case class EqualityAssertion(lt: SimpleTerm, rt: SimpleTerm) extends ComplexTerm
  case class NonEqualityAssertion(lt: SimpleTerm, rt: SimpleTerm) extends ComplexTerm

  sealed trait SimpleTerm extends ComplexTerm
  case class StName(name: Name) extends SimpleTerm
  object StName {
    def parser: Parser[StName] = Name.parser.map(StName.apply)
  }
  case class StVariable(name: Name) extends SimpleTerm
  object StVariable {
    // <variable> ::= "#" <name>
    def parser: Parser[StVariable] =
      (char('#') *> Name.parser).map(StVariable.apply)
  }
  case class StRelationTerm(name: Name, terms: NonEmptyList[SimpleTerm]) extends SimpleTerm
  object StRelationTerm {
    // <relational-term> ::= "[" <name> ": " <simple-term> <simple-terms> "]"
    def parser: Parser[StRelationTerm] =
      ((Name.parser <* string(": ")) ** nelcs(SimpleTerm.parser))
        .surround(char('['), char(']'))
        .map { case (name, sts) => StRelationTerm(name, NonEmptyList.fromListUnsafe(sts)) }
  }
  object SimpleTerm {
    def parser: Parser[SimpleTerm] = StName.parser | StVariable.parser | StRelationTerm.parser
  }

  sealed trait Op
  object Op {

    def parser: Parser[Op] =
      // 1. quit
      // 2. noop (comment, nl)
      // 3. rule (Fact(Simple), Complex(Complex, Simple))
      // 4. query (List[ComplexTerm])
      ???
  }

  case object QuitCommand extends Op {
    def parser: Parser[QuitCommand.type] = ???
  }

  sealed trait NoOp extends Op
  case object NoOpEmptyLine extends NoOp
  case class NoOpComment(value: String) extends NoOp
  object NoOp {
    def parser: Parser[NoOp] = ???
  }

  sealed trait Rule extends Op
  case class RuleFact(st: SimpleTerm) extends Rule
  case class RuleComplex(from: List[ComplexTerm], to: SimpleTerm) extends Rule
  object Rule {
    def parser: Parser[Rule] = ???
  }

  case class Query(terms: List[ComplexTerm]) extends Op
  object Query {
    def parser: Parser[Query] = ???
  }

  implicit class ParserOps2[A](pa: Parser[A]) {
    def run(raw: String) = ParserImpl.run(pa)(raw)
  }

  implicit class IdentifierOps(s: String) {
    def id = Name(s)
  }

}

class P11Spec extends AnyFunSuite with Matchers {

  import Model._
  import Parsers.Parser
  import Parsers.ParserImpl
  import Parsers.ParserImpl._

  test("name") {
    Name.parser.run("a") shouldBe Name("a").asRight
    Name.parser.run("a1") shouldBe Name("a1").asRight
    Name.parser.run("a-b") shouldBe Name("a-b").asRight
    Name.parser.run("a-b-c") shouldBe Name("a-b-c").asRight
  }

  test("stName") {
    StName.parser.run("a") shouldBe StName("a".id).asRight
    StVariable.parser.run("#a") shouldBe StVariable("a".id).asRight
  }

  test("many") {
    val p = ParserImpl.char('z').many
    val p2 = p.run("zzz")
    pprint.log(p2)
  }

  test("nelcs") {
    val p2 = nelcs(char('z')).run("z, z, z")
    pprint.log(p2)
    val p3 = nelcs(Name.parser).run("z")
    pprint.log(p3)
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



}
