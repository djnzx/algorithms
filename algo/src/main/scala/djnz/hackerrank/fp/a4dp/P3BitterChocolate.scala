package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/bitter-chocolate/problem */
object P3BitterChocolate {

  def next() = scala.io.StdIn.readLine()
  //               bottom                top
  case class Plate(row1: Int, row2: Int, row3: Int) {
    def eat(x: Int, y: Int): Plate = y match {
      case 2 => Plate(row1, row2, x)             // top
      case 1 => Plate(row1, x, row3 min x)       // middle
      case 0 => Plate(x, row2 min x, row3 min x) // bottom
    }
  }

  private def allPlatesUpTo(mx: Int): Vector[Plate] =
    (0 to mx).flatMap { a =>
      (0 to a).flatMap { b =>
        (0 to b).map(c => Plate(a, b, c))
      }
    }.toVector.sortBy(p => p.row1 + p.row2 + p.row3)

  private def moves(p: Plate): Iterator[Plate] = {
    val m0 = (0 until p.row1).iterator.map(x => p.eat(x, 0))
    val m1 = (0 until p.row2).iterator.map(x => p.eat(x, 1))
    val m2 = (0 until p.row3).iterator.map(x => p.eat(x, 2))
    m0 ++ m1 ++ m2
  }

  def mkAllSolutions(mx: Int): Map[Plate, Boolean] =
    allPlatesUpTo(mx)
      .foldLeft(
        Map(Plate(0, 0, 0) -> true)
      ) {
        case (sols, p) if sols.contains(p) => sols
        case (sols, p)                     =>
          val sol = moves(p).exists(p => !sols(p))
          sols + (p -> sol)
      }

  val show: Boolean => String = {
    case true => "WIN"
    case _    => "LOSE"
  }

  def main(args: Array[String]): Unit = {
    val in = (1 to next().toInt)
      .map(_ => next().split(" ").map(_.toInt))
      .map { case Array(a, b, c) => Plate(a, b, c) }

    val max = in.foldLeft(0) { case (mx, Plate(a, b, c)) => mx max a max b max c }
    val all = mkAllSolutions(max)

    in.map(all)
      .map(show)
      .foreach(println)
  }

}
