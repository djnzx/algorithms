package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/game-of-kyles/problem */
object P9GameOfKyles {

  import scala.io.StdIn.readLine

  // "II.III..I" -> List(1,3,2)
  def mkGroups(s: String) = s.foldLeft(List(0)) {
    case (h :: t, 'I') => (h + 1) :: t
    case (gs, _)       => 0 :: gs
  }.filter(_ > 0)

  // Sprague–Grundy theory implemented for an impartial combinatorial game

  // minimum excluded nonnegative integer
  def mex(states: Set[Int]): Int =
    LazyList.from(0).find(i => !states.contains(i)).get

  lazy val grundy: LazyList[Int] = {

    def compute(v: Int): Int = {
      val wo1 = (0 until v)
        .map(i => grundy(i) ^ grundy(v - i - 1)).toSet

      val wo2 = (0 until (v - 1))
        .map(i => grundy(i) ^ grundy(v - i - 2)).toSet

      mex(wo1 ++ wo2)
    }

    LazyList.from(0).map(compute)
  }

  def solve(gs: List[Int]) = gs.map(grundy).foldLeft(0)(_ ^ _)

  def main(args: Array[String]): Unit =
    (1 to readLine.toInt)
      .foreach { _ =>
        val _ = readLine()
        val gs = mkGroups(readLine())
        val x = solve(gs)
        println(if (x == 0) "LOSE" else "WIN")
      }

}
