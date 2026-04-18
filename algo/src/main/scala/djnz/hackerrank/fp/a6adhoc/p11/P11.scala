package djnz.hackerrank.fp.a6adhoc.p11

import cats.data._
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

  // Nullable List, comma-separated
  def nullcs[A](pa: Parser[A]): Parser[List[A]] =
    pa.opt
      .flatMap {
        case None    => succeed(Nil)
        case Some(h) => (string(", ") *> pa).many.opt.map {
            case None    => List(h)
            case Some(t) => h :: t
          }
      }

  case class Name(value: String) extends AnyVal
  object Name {
    // [a-z][a-zA-Z0-9\\-]*
    val parser: Parser[Name] =
      regex("[a-z][a-zA-Z0-9\\-]*".r).map(Name.apply)
  }

  sealed trait ComplexTerm
  object ComplexTerm {
    // <complex-term> ::= <simple-term> | <equality-assertion> | <non-equality-assertion>
    val parser: Parser[ComplexTerm] =
      attempt(EqualityAssertion.parser) | attempt(NonEqualityAssertion.parser) | attempt(SimpleTerm.parser)
  }
  case class EqualityAssertion(lt: SimpleTerm, rt: SimpleTerm) extends ComplexTerm
  object EqualityAssertion {
    // <equality-assertion> ::= "<" <simple-term> " = " <simple-term> ">"
    val parser: Parser[EqualityAssertion] =
      ((SimpleTerm.parser <* string(" = ")) ** SimpleTerm.parser)
        .surround(char('<'), char('>'))
        .map { case (lt, rt) => EqualityAssertion(lt, rt) }
  }
  case class NonEqualityAssertion(lt: SimpleTerm, rt: SimpleTerm) extends ComplexTerm
  object NonEqualityAssertion {
    // <non-equality-assertion> ::= "<" <simple-term> " /= " <simple-term> ">"
    val parser: Parser[NonEqualityAssertion] =
      ((SimpleTerm.parser <* string(" /= ")) ** SimpleTerm.parser)
        .surround(char('<'), char('>'))
        .map { case (lt, rt) => NonEqualityAssertion(lt, rt) }
  }

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
    // <simple-terms> ::= "" | ", " <simple-term> <simple-terms>
    def parser: Parser[StRelationTerm] =
      ((Name.parser <* string(": ")) ** nelcs(SimpleTerm.parser))
        .surround(char('['), char(']'))
        .map { case (name, sts) => StRelationTerm(name, NonEmptyList.fromListUnsafe(sts)) }
  }
  object SimpleTerm {
    // <simple-term> ::= <name> | <variable> | <relational-term>
    def parser: Parser[SimpleTerm] =
      StName.parser | StVariable.parser | StRelationTerm.parser
  }

  sealed trait Op
  object Op {
    // <op> ::= <rule> | <query> | <command> | <no-op>
    def parser: Parser[Op] =
      QuitCommand.parser | NoOp.parser | Rule.parser | Query.parser
  }

  case object QuitCommand extends Op {
    // <command> ::= "quit!"
    def parser: Parser[QuitCommand.type] = root(string("quit!")).as(QuitCommand)
  }

  sealed trait NoOp extends Op

  case object NoOpEmptyLine extends NoOp {
    def parser: Parser[NoOpEmptyLine.type] =
      root(string("").as(NoOpEmptyLine))
  }
  case class NoOpComment(value: String) extends NoOp {}
  object NoOpComment {
    // "%" <comment>
    def parser: Parser[NoOpComment] =
      (char('%') *> regex("[^\n]*".r)).map(NoOpComment.apply)
  }
  object NoOp {
    // <no-op> ::= "" | "%" <comment>
    def parser: Parser[NoOp] =
      NoOpComment.parser | NoOpEmptyLine.parser
  }

  sealed trait Rule extends Op
  case class RuleFact(st: SimpleTerm) extends Rule
  object RuleFact {
    // <simple-term> "."
    def parser: Parser[RuleFact] =
      root((SimpleTerm.parser <* char('.')).map(RuleFact.apply))
  }
  case class RuleComplex(from: List[ComplexTerm], to: SimpleTerm) extends Rule
  object RuleComplex {
    // "{(" <complex-terms> ") => " <simple-term> "}."
    def parser: Parser[RuleComplex] =
      ((nullcs(ComplexTerm.parser)
        .surround("(", ")") <* string(" => ")) ** SimpleTerm.parser)
        .surround("{", "}")
        .map { case (cts, st) => RuleComplex(cts, st) }
  }
  object Rule {
    // <rule> ::= <simple-term> "." | "{(" <complex-terms> ") => " <simple-term> "}."
    def parser: Parser[Rule] = attempt(RuleFact.parser) | attempt(RuleComplex.parser)
  }

  case class Query(terms: List[ComplexTerm]) extends Op
  object Query {
    // <query> ::= "(" <complex-terms> ")?"
    def parser: Parser[Query] =
      (nullcs(ComplexTerm.parser)
        .surround("(", ")") <* char('.'))
        .map(Query.apply)

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

  test("EqualityAssertion") {
    EqualityAssertion.parser.run("<a = #b>") shouldBe
      EqualityAssertion(StName("a".id), StVariable("b".id)).asRight

    EqualityAssertion.parser.run("<[p: a] = [p: #x, y]>") shouldBe
      EqualityAssertion(
        StRelationTerm("p".id, NonEmptyList.of(StName("a".id))),
        StRelationTerm("p".id, NonEmptyList.of(StVariable("x".id), StName("y".id)))
      ).asRight
  }

  test("NonEqualityAssertion") {
    NonEqualityAssertion.parser.run("<a /= #b>") shouldBe
      NonEqualityAssertion(StName("a".id), StVariable("b".id)).asRight

    NonEqualityAssertion.parser.run("<[p: a] /= [p: #x]>") shouldBe
      NonEqualityAssertion(
        StRelationTerm("p".id, NonEmptyList.of(StName("a".id))),
        StRelationTerm("p".id, NonEmptyList.of(StVariable("x".id)))
      ).asRight
  }

  test("ComplexTerm") {
    val ct1 = ComplexTerm.parser.run("a")
    pprint.log(ct1)
    ct1 shouldBe StName("a".id).asRight

    val ct2 = ComplexTerm.parser.run("#ab")
    pprint.log(ct2)
    ct2 shouldBe StVariable("ab".id).asRight

    val ct3 = ComplexTerm.parser.run("<a = #b>")
    pprint.log(ct3)
    ct3 shouldBe EqualityAssertion(StName("a".id), StVariable("b".id)).asRight

    val ct4 = ComplexTerm.parser.run("<a /= #b>")
    pprint.log(ct4)
    ct4 shouldBe NonEqualityAssertion(StName("a".id), StVariable("b".id)).asRight
  }

  test("noop-emptyline") {
    val x = NoOpEmptyLine.parser.run("\n")
    pprint.log(x)
  }

  test("noop-comment") {
    val x = NoOpComment.parser.run("% todo")
    pprint.log(x)
  }

  test("noop") {
    Seq(
      "\n",
      "% blah"
    )
      .map(NoOp.parser.run)
      .foreach {
        case Right(x) => pprint.log(x)
        case Left(x)  => pprint.err.log(x)
      }
  }

  test("quit") {
    val x = QuitCommand.parser.run("quit!")
    pprint.log(x)
  }

  test("rule-fact") {
    RuleFact.parser.run("a.") shouldBe
      RuleFact(StName("a".id)).asRight

    RuleFact.parser.run("[p: #x, y].") shouldBe
      RuleFact(
        StRelationTerm("p".id, NonEmptyList.of(StVariable("x".id), StName("y".id)))
      ).asRight
  }

  test("rule-complex") {
    val x1 = RuleComplex.parser.run("{() => b}.")
    pprint.log(x1)

    val x2 = RuleComplex.parser.run("{(a, [b: c], #c) => d}.")
    pprint.log(x2)

    val x3 = RuleComplex.parser.run("{([q: y], <y /= #x>) => [p: #x]}.")
    pprint.log(x3)
  }

  test("nullcs") {
    val x1 = nullcs(StName.parser).run("")
    pprint.log(x1)

    val x2 = nullcs(StName.parser).run("a")
    pprint.log(x2)

    val x3 = nullcs(StName.parser).run("a, b, c")
    pprint.log(x3)
  }

  test("query") {
    Query.parser.run("().") shouldBe
      Query(Nil).asRight

    Query.parser.run("(a).") shouldBe
      Query(List(StName("a".id))).asRight

    Query.parser.run("([q: y], <y /= #x>, #z).") shouldBe
      Query(
        List(
          StRelationTerm("q".id, NonEmptyList.of(StName("y".id))),
          NonEqualityAssertion(StName("y".id), StVariable("x".id)),
          StVariable("z".id)
        )
      ).asRight
  }

}
