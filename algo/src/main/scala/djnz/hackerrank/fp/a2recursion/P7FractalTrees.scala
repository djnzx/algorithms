package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/fractal-trees/problem */
object P7FractalTrees {
  val W = 64
  val H = 63
  val EMPTY = '_'
  val FILLED = '1'
  val OFFSET = 18
  type Board = Seq[Seq[Char]]
  val board0: Board = (1 to H).map(_ => (1 to W).map(_ => EMPTY))

  def isToShow(n: Int, x: Int, y: Int, w: Int, h: Int): Boolean = n match {
    case 0 => false
    case n =>
      val widthHalf = w >> 1
      val heightHalf = h >> 1
      val heightT = (h + 1) >> 2
      val c1 = (y >= 3 * heightT - 1) && (y < 4 * heightT - 1) && (x == widthHalf - 1)
      val c2 = (y >= 2 * heightT - 1) && (y < 3 * heightT - 1) && math.abs(widthHalf - 1 - x) == 3 * heightT - 1 - y
      val c3 = isToShow(n - 1, if (x < widthHalf) x else x - widthHalf, y, widthHalf, heightHalf)
      c1 || c2 || c3
  }

  def eval(board: Board, n: Int, w: Int, h: Int): Board =
    board.indices.map { y =>
      board(y).indices.map { x =>
        isToShow(n, x, y, w, h) match {
          case true => FILLED
          case _    => EMPTY
        }
      }
    }

  def show(board: Board): String =
    board
      .map { row =>
        val white = EMPTY.toString * OFFSET
        val data = row.mkString("")
        s"$white$data$white"
      }
      .mkString("\n")

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val board = eval(board0, n, W, H)
    val s = show(board)
    println(s)
  }

}
