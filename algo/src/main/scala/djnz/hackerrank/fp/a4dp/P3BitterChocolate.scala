package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/bitter-chocolate/problem */
object P3BitterChocolate {

  def next() = scala.io.StdIn.readLine()
  //               bottom                top
  case class Plate(row1: Int, row2: Int, row3: Int)
  case class XY(x: Int, y: Int)

  // TODO: implement via pure functional structure
  private val data = scala.collection.mutable.Map[Plate, Boolean]()

  def eat(at: XY, p: Plate): Plate = at.y match {
    case 2 => Plate(p.row1, p.row2, at.x)                   // top row
    case 1 => Plate(p.row1, at.x, p.row3 min at.x)
    case 0 => Plate(at.x, p.row2 min at.x, p.row3 min at.x) // bottom row
    case _ => sys.error("never by design")
  }

  def solve(p: Plate): Boolean = data.getOrElseUpdate(
    p,
    p == Plate(0, 0, 0) ||
      p.row1 > 0 && (0 until p.row1).exists(x => !solve(eat(XY(x, 0), p))) ||
      p.row2 > 0 && (0 until p.row2).exists(x => !solve(eat(XY(x, 1), p))) ||
      p.row3 > 0 && (0 until p.row3).exists(x => !solve(eat(XY(x, 2), p)))
  )

  def main(args: Array[String]): Unit =
    (1 to next().toInt)
      .map(_ => next().split(" ").map(_.toInt))
      .map { case Array(a, b, c) => Plate(a, b, c) }
      .map(solve)
      .map {
        case true => "WIN"
        case _    => "LOSE"
      }

}
