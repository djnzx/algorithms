package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/functions-and-fractals-sierpinski-triangles/problem */
object P4Sierpinski {
  type Triangle = Array[String]

  val EMPTY = "_"
  val FILLED = "1"

  def base(n: Int): Triangle = {
    val h = 1 << n
    (1 to h).map { x =>
      val pad = EMPTY * (h - x)
      pad + FILLED * (x * 2 - 1) + pad
    }.toArray
  }

  def scale(t: Triangle): Triangle = {
    val width = t(0).length
    val widthNew = width * 2 + 1
    val emptyWidth = (widthNew - width) / 2
    val emptyPart = EMPTY * emptyWidth
    val top = t.map(x => emptyPart + x + emptyPart)
    val bottom = t.map(x => x + EMPTY + x)
    top ++ bottom
  }

  def scale(n: Int, t: Triangle): Triangle = n match {
    case 0 => t
    case n => scale(n - 1, scale(t))
  }

  def make(n: Int): Triangle = scale(n, base(5 - n))

  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readInt()
    val t = make(n).mkString("\n")
    println(t)
  }

}
