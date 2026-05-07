package djnz.hackerrank.fp.a6adhoc.p11

// Model (input syntax) + Parsers
object InputSyntax {

  import ParsersCore.Parser
  import ParsersCore.ParserImpl
  import ParsersCore.ParserImpl._
  import cats.Show
  import cats.data._
  import cats.implicits.toShow

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

  case class Id(value: String) extends AnyVal
  object Id {
    // [a-zA-Z][a-zA-Z0-9\\-]*
    val parser: Parser[Id] =
      regex("[a-zA-Z][a-zA-Z0-9\\-]*".r).map(Id.apply)
  }

  sealed trait ComplexTerm
  object ComplexTerm {
    // <complex-term> ::= <simple-term> | <equality-assertion> | <non-equality-assertion>
    val parser: Parser[ComplexTerm] =
      EqualityAssertion.parser.attempt | NonEqualityAssertion.parser.attempt | SimpleTerm.parser.attempt
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

  /// ============ SIMPLE TERM =================
  sealed trait SimpleTerm extends ComplexTerm
  object SimpleTerm {
    // <simple-term> ::= <name> | <variable> | <relational-term>
    def parser: Parser[SimpleTerm] =
      StName.parser | StVariable.parser | StRelation.parser
  }

  case class StName(value: Id) extends SimpleTerm
  object StName {
    def parser: Parser[StName] = Id.parser.map(StName.apply)
  }
  case class StVariable(value: Id) extends SimpleTerm
  object StVariable {
    // <variable> ::= "#" <name>
    def parser: Parser[StVariable] =
      (char('#') *> Id.parser).map(StVariable.apply)
  }
  case class StRelation(name: Id, terms: NonEmptyList[SimpleTerm]) extends SimpleTerm
  object StRelation {
    // <relational-term> ::= "[" <name> ": " <simple-term> <simple-terms> "]"
    // <simple-terms> ::= "" | ", " <simple-term> <simple-terms>
    def parser: Parser[StRelation] =
      ((Id.parser <* string(": ")) ** nelcs(SimpleTerm.parser))
        .surround(char('['), char(']'))
        .map { case (name, sts) => StRelation(name, NonEmptyList.fromListUnsafe(sts)) }
  }
  /// ==========================================

  sealed trait Op
  object Op {
    // <op> ::= <rule> | <query> | <command> | <no-op>
    def parser: Parser[Op] =
      QuitCommand.parser | NoOp.parser | Rule.parser | Query.parser
  }

  case object QuitCommand extends Op {
    // <command> ::= "quit!"
    def parser: Parser[QuitCommand.type] = string("quit!").as(QuitCommand)
  }

  sealed trait NoOp extends Op
  object NoOp {
    // <no-op> ::= "" | "%" <comment>
    def parser: Parser[NoOp] =
      NoOpComment.parser | NoOpEmptyLine.parser
  }

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

  sealed trait Rule extends Op
  object Rule {
    // <rule> ::= <simple-term> "." | "{(" <complex-terms> ") => " <simple-term> "}."
    def parser: Parser[Rule] = attempt(Fact.parser) | attempt(Complex.parser)

    case class Fact(st: SimpleTerm) extends Rule
    object Fact {
      // <simple-term> "."
      def parser: Parser[Fact] =
        (SimpleTerm.parser <* char('.')).map(Fact.apply)
    }

    case class Complex(premise: List[ComplexTerm], conclusion: SimpleTerm) extends Rule
    object Complex {
      // "{(" <complex-terms> ") => " <simple-term> "}."
      def parser: Parser[Complex] =
        ((nullcs(ComplexTerm.parser)
          .surround("(", ")") <* string(" => ")) ** SimpleTerm.parser)
          .surround("{", "}")
          .map { case (prem, con) => Complex(prem, con) }
    }

  }

  case class Query(terms: List[ComplexTerm]) extends Op
  object Query {
    // <query> ::= "(" <complex-terms> ")?"
    def parser: Parser[Query] =
      (nullcs(ComplexTerm.parser)
        .surround("(", ")") <* char('?'))
        .map(Query.apply)
  }

  // Show[A] instances
  implicit val showId: Show[Id] = Show.show(_.value)

  implicit val showName: Show[StName] =
    Show.show(_.value.show)

  implicit val showVariable: Show[StVariable] =
    Show.show(x => s"#${x.value.show}")

  implicit lazy val showRelation: Show[StRelation] =
    Show.show(r => s"[${r.name.show}: ${r.terms.toList.map(_.show).mkString(", ")}]")

  implicit lazy val showSimpleTerm: Show[SimpleTerm] =
    Show.show {
      case x: StName     => x.show
      case x: StVariable => x.show
      case x: StRelation => x.show
    }

  implicit val showComplexTerm: Show[ComplexTerm] = Show.show {
    case st: SimpleTerm               => st.show
    case EqualityAssertion(lt, rt)    => s"<${lt.show} = ${rt.show}>"
    case NonEqualityAssertion(lt, rt) => s"<${lt.show} /= ${rt.show}>"
  }

  implicit class ParserOps2[A](pa: Parser[A]) {
    def run(raw: String) = ParserImpl.run(pa)(raw)
  }

}
