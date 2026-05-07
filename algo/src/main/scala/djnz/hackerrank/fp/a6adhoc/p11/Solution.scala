package djnz.hackerrank.fp.a6adhoc.p11

object SolutionRepresentation {

  import InputSyntax._
  import cats.Show
  import cats.syntax.show._

  val ready = "Ready."
  val sat = "SAT"
  val unsat = "UNSAT"
  val satWith = "SAT:\n====="

  implicit val showAssignment: Show[(StVariable, SimpleTerm)] = Show.show {
    case (v, term) => s"${v.show} := ${term.show}"
  }

  implicit val showAssignments: Show[Vector[(StVariable, SimpleTerm)]] = Show.show {
    _.sortBy { case (variable, _) => variable.value.value }
      .map(_.show)
      .mkString("\n")
  }

  implicit val showOutcome: Show[Option[Vector[Vector[(StVariable, SimpleTerm)]]]] = Show.show {
    case None              => s"$unsat\n$ready"
    case Some(derivations) =>
      val rep = derivations.map(_.show).map {
        case ""  => sat
        case ass => s"$satWith\n$ass"
      }
      (rep :+ ready).mkString("\n")
  }
}

object Solution {

  import InputSyntax.NoOp
  import InputSyntax.Op
  import InputSyntax.Query
  import InputSyntax.QuitCommand
  import InputSyntax.Rule
  import P11ElementaryWatson.Knowledge
  import P11ElementaryWatson.QueryCompiler
  import cats.implicits.catsSyntaxOptionId
  import cats.implicits.none

  def handleOp(k: Knowledge, step: Op) = (k, step) match {
    case (k, _: NoOp)     => k -> none[String]
    case (k, QuitCommand) => k -> "Bye.".some
    case (k, r: Rule)     =>
      val clause = QueryCompiler.ruleToClause(r)
      k.addClause(clause) -> "Ok.".some
    case (k, q: Query)    =>
      import cats.syntax.show._
      import SolutionRepresentation._
      val goals = QueryCompiler.queryToGoals(q)
      k -> k.query(goals).show.some
  }

  def parseUnsafe(raw: String): Op =
    Op.parser.run(raw).fold(e => sys.error(s"can't parse $e"), identity)

  def handleInputUnsafe(k: Knowledge, in: String) =
    handleOp(k, parseUnsafe(in))

  def handleBulk(k: Knowledge, in: Iterator[String]) =
    in.foldLeft(k -> List.empty[String]) { case ((k, out), in) =>
      handleInputUnsafe(k, in) match {
        case (k, None)      => k -> out
        case (k, Some(row)) => k -> (row :: out)
      }
    } match {
      case (k, out) => k -> out.reverse
    }

  def main(args: Array[String]): Unit =
    handleBulk(
      Knowledge.initial,
      scala.io.Source.stdin.getLines
    )._2
      .foreach(println)

}
