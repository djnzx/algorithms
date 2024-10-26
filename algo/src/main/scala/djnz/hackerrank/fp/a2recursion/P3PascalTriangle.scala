package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/pascals-triangle/problem */
object P3PascalTriangle {

  def fact(n: Int): Long = (1 to n).foldLeft(1L)((acc, x) => acc * x)
  def cell(row: Int, col: Int): Long = fact(row) / (fact(col) * fact(row - col))

  def line(n_row: Int): List[Long] = {

    def go(n_col: Int, acc: List[Long]): List[Long] =
      if (n_col > n_row) acc.reverse
      else go(n_col + 1, cell(n_row, n_col) :: acc)

    go(0, Nil)
  }

  def pascalTriangle(rows: Int): List[List[Long]] = {

    def go(row: Int, acc: List[List[Long]]): List[List[Long]] = row match {
      case `rows` => acc.reverse
      case _      => go(row + 1, line(row) :: acc)
    }

    go(0, Nil)
  }

  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readInt()
    val s = pascalTriangle(n)
      .map(_.mkString(" "))
      .mkString("\n")

    println(s)
  }

}
