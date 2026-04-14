package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/game-of-kyles/problem */
object P9GameOfKyles {

  import scala.io.StdIn.readLine

  def mkGroups(s: String) = s.foldLeft(List(0)) {
    case (h :: t, 'I') => (h + 1) :: t
    case (gs, _)       => 0 :: gs
  }.filter(_ > 0)

  // first non existent
  def mex(states: Set[Int]): Int =
    LazyList.from(0).find(i => !states.contains(i)).get

  // solutions
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

  def number(v: Int): Int = grundy(v)

  def solve(gs: List[Int]) = gs.map(number).foldLeft(0)(_ ^ _)

  def main(args: Array[String]): Unit =
    (1 to readLine.toInt)
      .foreach { _ =>
        val _ = readLine()
        val gs = mkGroups(readLine())
        val x = solve(gs)
        println(if (x == 0) "LOSE" else "WIN")
      }

}
