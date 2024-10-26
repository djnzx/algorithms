package djnz.hackerrank.fp.a2recursion

/** 10 - 4
  * 11 - 44
  * 12 - 156
  * 13 - 1876
  * 14 - 5180
  */
object P13SuperQueenSlow extends App {
  case class Pos(x: Int, y: Int)

  def exclude(free: Set[Pos], queen: Pos): Set[Pos] = {
    def isua(x: Int, y: Int) =
      x == queen.x || y == queen.y ||
        (math.abs(x - queen.x) == math.abs(y - queen.y)) ||
        (math.abs(queen.x - x) <= 2 && math.abs(queen.y - y) <= 2)

    free.foldLeft(Set.newBuilder[Pos])((acc, p) => if (isua(p.x, p.y)) acc else acc.addOne(p)).result()
  }

  def distXY(xs: Set[Pos]) = xs
    .foldLeft((Set.newBuilder[Int], Set.newBuilder[Int])) { case ((accx, accy), p) =>
      accx.addOne(p.x) -> accy.addOne(p.y)
    } match {
    case (ax, ay) => ax.result().size -> ay.result().size
  }

  def solveNSuperQueens(N: Int): Int = {
    val range = 1 to N
    val cells = range.flatMap(x => range.map(y => Pos(x, y))).toSet

    def count(queens: Set[Pos], free: Set[Pos]): Int = {
      val need = N - queens.size
      if (need == 0) 1
      else if (
        free.size < need || {
          val (dx, dy) = distXY(free)
          dx < need || dy < need
        }
      ) 0
      else count(queens + free.head, exclude(free, free.head)) + count(queens, free.tail)
    }

    count(Set.empty, cells)
  }

  (10 to 14).foreach(x => println(solveNSuperQueens(x)))
}
