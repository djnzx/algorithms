package djnz.hackerrank.fp.a2recursion

import scala.collection.mutable.ListBuffer

object P13SuperQueenMutable extends App {
  case class Pos(x: Int, y: Int)
  def exa(free: List[Pos], queen: Pos): List[Pos] = {
    def isua(x: Int, y: Int) =
      x == queen.x || y == queen.y ||
        (math.abs(x - queen.x) == math.abs(y - queen.y)) ||
        (math.abs(queen.x - x) <= 2 && math.abs(queen.y - y) <= 2)
    free.foldLeft(new ListBuffer[Pos])((acc, p) => if (isua(p.x, p.y)) acc else acc.addOne(p)).toList
  }

  def solveNSuperQueens(N: Int) = {
    val range = (1 to N).toList
    val all = range.flatMap(x => range.map(y => Pos(x, y)))

    def solve(start: Pos) = {

      def doSolve(queens: List[Pos], free: List[Pos]): List[Set[Pos]] =
        if (queens.size == N) List(queens.toSet)
        else if (free.length + queens.length < N) List.empty
        else
          doSolve(free.head :: queens, exa(free, free.head)) ++
            doSolve(queens, free.tail)

      doSolve(List(start), exa(all, start))
    }

    range
      .map(Pos(_, 1))
      .flatMap(solve)
      .size
  }

  (10 to 14)
    .foreach(x => println(solveNSuperQueens(x)))

}
