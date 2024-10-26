package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/super-queens-on-a-chessboard/problem */
object P13SuperQueens {

  case class Pos(x: Int, y: Int)

  def noConflict(p: Pos, qs: List[Pos]): Boolean = qs.forall { q =>
    val dx = p.x - q.x
    val dy = math.abs(p.y - q.y)
    val c1 = !(p.y == q.y)
    val c2 = !(dy == dx)
    val c3 = !(dx == 2 && dy == 1 || dx == 1 && dy == 2)
    c1 && c2 && c3
  }

  def solveNSuperQueens(n: Int): Int = {

    def go(rest: Int, acc: List[Pos]): Int = rest match {
      case 0    => 1
      case rest =>
        (0 until n).map { y =>
          val p = Pos(n - rest, y)
          noConflict(p, acc) match {
            case true => go(rest - 1, p :: acc)
            case _    => 0
          }
        }.sum
    }

    go(n, Nil)
  }

  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readInt()
    val x = solveNSuperQueens(n)
    println(x)
  }

}
