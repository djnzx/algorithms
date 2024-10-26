package djnz.combinatorics.nqueens

import org.scalatest.funsuite.AnyFunSuite
import scala.annotation.tailrec

/** https://en.wikipedia.org/wiki/Eight_queens_puzzle */
object NQueens {

  def nQueens(n: Int): List[List[Int]] = {

    def isInConflict(newCol: Int, queens: List[Int]): Boolean = {

      /** row: 1..n, col: 1..n */
      def isInConflictOne(newCol: Int, col: Int, row: Int) =
        newCol == col ||           // same column
          row == (newCol - col) || // same diagonal 1
          row == (col - newCol)    // same diagonal 2

      (queens zip LazyList.from(1))
        .exists { case (c, r) => isInConflictOne(newCol, c, r) }
    }

    @tailrec
    def nq(curPos: Int, curQs: List[Int], solutions: List[List[Int]]): List[List[Int]] = () match {
      case _ if curPos > n && curQs.isEmpty => solutions
      case _ if curPos > n                  => nq(curQs.head + 1, curQs.tail, solutions)
      case _ if isInConflict(curPos, curQs) => nq(curPos + 1, curQs, solutions)
      case _ if curQs.length == n - 1       => nq(curPos + 1, curQs, (curPos :: curQs) :: solutions)
      case _                                => nq(1, curPos :: curQs, solutions)
    }

    nq(1, Nil, Nil) // start from column 1
  }

}

class NQueens extends AnyFunSuite {

  import NQueens._

  test("board 4x4") {
    val r = nQueens(4)
    println(r.size)
    r.foreach(println)
  }

  test("board 5x5") {
    val r = nQueens(5)
    println(r.size)
    r.foreach(println)
  }

  test("board 6x6") {
    val r = nQueens(6)
    println(r.size)
    r.foreach(println)
  }

  test("board 7x7") {
    val r = nQueens(7)
    println(r.size)
    r.foreach(println)
  }

  test("board 8x8") {
    val r = nQueens(8)
    println(r.size)
    r.foreach(println)
  }

}
