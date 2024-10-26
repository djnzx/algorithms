package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/functions-or-not/problem */
object P23FunctionsOrNot {

  /** 1. group by input
    * 2. if there is more output to one input the answer in NO
    */
  def couldBeFunction1(in: Seq[(Int, Int)]): String =
    in.groupMapReduce { case (in, out) => in } { case (in, out) => Set(out) }(_ ++ _)
      .forall { case (_, outs) => outs.size == 1 } match {
      case true => "YES"
      case _    => "NO"
    }

  def couldBeFunction(in: Seq[(Int, Int)]): String =
    in.groupBy { case (in, out) => in }
      .map { case (in, outs) => in -> outs.map { case (in, out) => out }.toSet }
      .forall { case (_, outs) => outs.size == 1 } match {
      case true => "YES"
      case _    => "NO"
    }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit =
    (1 to next().toInt)
      .map { _ =>
        (1 to next().toInt)
          .map(_ => next())
          .map(_.split(" "))
          .map(_.map(_.toInt))
          .map { case Array(a, b) => (a, b) }
      }
      .foreach(couldBeFunction _ andThen println)

}
