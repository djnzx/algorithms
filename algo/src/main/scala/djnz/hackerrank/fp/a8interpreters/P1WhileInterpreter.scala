package djnz.hackerrank.fp.a8interpreters

/** https://www.hackerrank.com/challenges/while-language-fp/problem */
object P1WhileInterpreter {

  import scala.collection.mutable
  case class Ram(data: mutable.Map[String, Long])
  object Ram {
    val clean: Ram = Ram(mutable.Map.empty)
  }

  object algebra {
    sealed trait Expression

    sealed trait Keyword extends Expression
    case object If extends Keyword
    case object Then extends Keyword
    case object Else extends Keyword
    case object While extends Keyword
    case object Do extends Keyword

    sealed trait Operand extends Expression
    sealed trait BooleanValue extends Operand {
      def value: Boolean
    }

    sealed trait Evaluation extends Operand

    case class CurlyBrackets(expressions: List[Expression]) extends Expression
    sealed trait Operation extends Expression

    case class Statement(expressions: List[Expression]) extends Expression
    case class Lexeme(s: String) extends Expression
    sealed trait Operator extends Expression {
      def execute(ram: Ram): Unit
    }

    case class Variable(name: String) extends ArithmeticEvaluation {
      def eval(ram: Ram): Long = ram.data(name)
    }
    case class Number(value: Long) extends ArithmeticEvaluation {
      def eval(ram: Ram): Long = value
    }

    case class Parentheses(expressions: List[Expression]) extends ArithmeticEvaluation {
      private lazy val evaluation = toEvaluation.asInstanceOf[ArithmeticEvaluation]

      def toEvaluation: Evaluation = {
        val res = Parentheses.groups.foldLeft(expressions) { (acc, ops) =>
          def simplify(exs: List[Expression]): List[Expression] = exs match {
            case Nil                                                                                                            => Nil
            case (a: BooleanValue) :: Nil                                                                                       => simplify(ValueBool(a) :: Nil)
            case (a: BoolEvaluation) :: (op: BooleanOperation) :: (b: BoolEvaluation) :: exs if ops.contains(op)                =>
              simplify(BinaryBool(op, a, b) :: exs)
            case (a: Number) :: Nil                                                                                             => simplify(ValueArithmetic(a) :: Nil)
            case (a: Variable) :: Nil                                                                                           => simplify(ValueArithmetic(a) :: Nil)
            case (a: ArithmeticEvaluation) :: (op: ArithmeticOperation) :: (b: ArithmeticEvaluation) :: exs if ops.contains(op) =>
              simplify(BinaryArighmetic(op, a, b) :: exs)
            case (a: ArithmeticEvaluation) :: (op: RelationalOperation) :: (b: ArithmeticEvaluation) :: exs if ops.contains(op) =>
              simplify(BinaryRelational(op, a, b) :: exs)
            case ex :: exs                                                                                                      => ex :: simplify(exs)
          }

          simplify(acc)
        }

        res.head.asInstanceOf[Evaluation]
      }

      def eval(ram: Ram): Long = evaluation.eval(ram)
    }
    object Parentheses {
      val groups = Seq(
        Seq(Multiplication, Division),
        Seq(Addition, Subtraction),
        Seq(Greater, Less),
        Seq(And),
        Seq(Or),
        Seq(Assignment)
      )
    }

    trait ArithmeticOperation extends Operation {
      def eval(a: Long, b: Long): Long
    }

    trait Additive extends ArithmeticOperation
    case object Addition extends Additive {
      def eval(a: Long, b: Long): Long = a + b
    }
    case object Subtraction extends Additive {
      def eval(a: Long, b: Long): Long = a - b
    }

    trait Multiplicative extends ArithmeticOperation
    case object Multiplication extends Multiplicative {
      def eval(a: Long, b: Long): Long = a * b
    }
    case object Division extends Multiplicative {
      def eval(a: Long, b: Long): Long = a / b
    }

    trait RelationalOperation extends Operation {
      def eval(a: Long, b: Long): Boolean
    }
    case object Less extends RelationalOperation {
      def eval(a: Long, b: Long): Boolean = a < b
    }
    case object Greater extends RelationalOperation {
      def eval(a: Long, b: Long): Boolean = a > b
    }

    trait BooleanOperation extends Operation {
      def eval(a: Boolean, b: Boolean): Boolean
    }
    case object And extends BooleanOperation {
      def eval(a: Boolean, b: Boolean): Boolean = a && b
    }
    case object Or extends BooleanOperation {
      def eval(a: Boolean, b: Boolean): Boolean = a || b
    }

    case object Assignment extends Operation

    case object False extends BooleanValue {
      def value: Boolean = false
    }
    case object True extends BooleanValue {
      def value: Boolean = true
    }

    trait BoolEvaluation extends Evaluation {
      def eval(ram: Ram): Boolean
    }

    case class ValueBool(a: BooleanValue) extends BoolEvaluation {
      def eval(ram: Ram): Boolean = a.value
    }
    case class BinaryBool(op: BooleanOperation, a: BoolEvaluation, b: BoolEvaluation) extends BoolEvaluation {
      def eval(ram: Ram): Boolean = op.eval(a.eval(ram), b.eval(ram))
    }
    case class BinaryRelational(op: RelationalOperation, a: ArithmeticEvaluation, b: ArithmeticEvaluation) extends BoolEvaluation {
      def eval(ram: Ram): Boolean = op.eval(a.eval(ram), b.eval(ram))
    }

    trait ArithmeticEvaluation extends Evaluation {
      def eval(ram: Ram): Long
    }
    case class ValueArithmetic(a: ArithmeticEvaluation) extends ArithmeticEvaluation {
      def eval(ram: Ram): Long = a.eval(ram)
    }
    case class BinaryArighmetic(op: ArithmeticOperation, a: ArithmeticEvaluation, b: ArithmeticEvaluation) extends ArithmeticEvaluation {
      def eval(ram: Ram): Long = op.eval(a.eval(ram), b.eval(ram))
    }

    case class WhileOperator(condition: BoolEvaluation, body: List[Operator]) extends Operator {
      def execute(ram: Ram): Unit =
        while (condition.eval(ram))
          body.foreach(_.execute(ram))
    }
    case class IfOperator(condition: BoolEvaluation, op1: List[Operator], op2: List[Operator]) extends Operator {
      def execute(ram: Ram): Unit =
        if (condition.eval(ram)) op1.foreach(_.execute(ram))
        else op2.foreach(_.execute(ram))
    }
    case class AssignmentOperator(variable: Variable, evaluation: ArithmeticEvaluation) extends Operator {
      def execute(ram: Ram): Unit = ram.data(variable.name) = evaluation.eval(ram)
    }

  }

  import algebra._

  def parse(it: Iterator[String]): List[Statement] = {
    case class State(exs: List[Expression] = Nil, subExs: List[List[Expression]] = Nil)

    @scala.annotation.tailrec
    def extract(expressions: List[Expression], acc: List[Expression] = Nil): List[Expression] = expressions match {
      case Nil                    => Statement(acc) :: Nil
      case (st: Statement) :: exs => Statement(acc) :: st :: exs
      case ex :: exs              => extract(exs, ex :: acc)
    }

    val lexemes = Map(
      "+"     -> Addition,
      "-"     -> Subtraction,
      "*"     -> Multiplication,
      "/"     -> Division,
      "and"   -> And,
      "or"    -> Or,
      ">"     -> Greater,
      "<"     -> Less,
      ":="    -> Assignment,
      "true"  -> True,
      "false" -> False,
      "if"    -> If,
      "then"  -> Then,
      "else"  -> Else,
      "while" -> While,
      "do"    -> Do,
      "("     -> Lexeme("("),
      ")"     -> Lexeme(")"),
      "{"     -> Lexeme("{"),
      "}"     -> Lexeme("}"),
      ";"     -> Lexeme(";"),
    )

    @scala.annotation.tailrec
    def go(state: State): State = {

      def withEx(ex: Expression): State = State(ex :: state.exs, state.subExs)

      it.hasNext match {
        case true =>
          val next = it.next() match {
            case lx if lexemes.contains(lx) =>
              lexemes(lx) match {
                case Lexeme("(") | Lexeme("{") => State(Nil, state.exs :: state.subExs)
                case Lexeme(")")               => State(Parentheses(state.exs.reverse) :: state.subExs.head, state.subExs.tail)
                case Lexeme("}")               => State(CurlyBrackets(extract(state.exs)) :: state.subExs.head, state.subExs.tail)
                case Lexeme(";")               => State(extract(state.exs), state.subExs)
                case lex                       => withEx(lex)
              }

            case v if v.head.isLetter => State(Variable(v) :: state.exs, state.subExs)
            case n if n.head.isDigit  => State(Number(n.toLong) :: state.exs, state.subExs)
            case _                    => state
          }

          go(next)
        case _    => state
      }
    }

    val st = go(State())

    extract(st.exs)
      .map(_.asInstanceOf[Statement])
  }

  def mkFlow(statements: List[Statement], acc: List[Operator] = Nil): List[Operator] = statements match {
    case Nil       => acc
    case st :: sts =>
      val next = st.expressions match {
        case While :: (condition: Parentheses) :: Do :: CurlyBrackets(body) :: Nil                             =>
          WhileOperator(condition.toEvaluation.asInstanceOf[BoolEvaluation], mkFlow(body.map(_.asInstanceOf[Statement])))
        case If :: (condition: Parentheses) :: Then :: CurlyBrackets(op1) :: Else :: CurlyBrackets(op2) :: Nil =>
          IfOperator(
            condition.toEvaluation.asInstanceOf[BoolEvaluation],
            mkFlow(op1.map(_.asInstanceOf[Statement])),
            mkFlow(op2.map(_.asInstanceOf[Statement]))
          )
        case (variable: Variable) :: Assignment :: exs                                                         => AssignmentOperator(variable, Parentheses(exs).toEvaluation.asInstanceOf[ArithmeticEvaluation])
        case _                                                                                                 => throw new Exception("Unsupported statement")
      }
      mkFlow(sts, next :: acc)
  }

  def represent(ram: Ram) =
    ram.data
      .toSeq
      .sortBy { case (n, v) => n }
      .map { case (n, v) => s"$n $v" }
      .mkString("\n")

  def main(args: Array[String]): Unit = {
    val code = {
      val block = scala.io.Source.stdin
        .getLines()
        .mkString("\n")
        .split("""\s+""").iterator
      val statements = parse(block)
      mkFlow(statements)
    }
    val outcome = {
      val ram = Ram.clean
      code.foreach(_.execute(ram))
      represent(ram)
    }
    println(outcome)
  }

}
