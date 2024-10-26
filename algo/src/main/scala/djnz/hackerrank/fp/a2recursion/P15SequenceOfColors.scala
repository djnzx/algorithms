package djnz.hackerrank.fp.a2recursion

/** https://www.hackerrank.com/challenges/sequence-full-of-colors/problem */
object P15SequenceOfColors {

  case class CC(r: Int, g: Int, y: Int, b: Int) {
    def process(c: Char): Option[CC] = {
      val cc = c match {
        case 'R' => this.copy(r = r + 1)
        case 'G' => this.copy(g = g + 1)
        case 'Y' => this.copy(y = y + 1)
        case 'B' => this.copy(b = b + 1)
      }
      val cond = (math.abs(cc.r - cc.g) <= 1) && (math.abs(cc.y - cc.b) <= 1)
      Option.when(cond)(cc)
    }
  }
  val cc0 = CC(0, 0, 0, 0)

  def processOne(s: String): Boolean = {

    def go(i: Int, cco: Option[CC]): Option[CC] = cco match {
      case None     => None
      case Some(cc) => if (i < s.length) go(i + 1, cc.process(s(i))) else cco
    }

    go(0, Some(cc0)) match {
      case None                 => false
      case Some(CC(r, g, y, b)) => r == g && y == b
    }
  }

  def represent(value: Boolean): String = value.toString.capitalize

  def solve(data: List[String]) = data.map(processOne).map(represent)

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val N = next().toInt
    val list = (1 to N).map(_ => next()).toList
    solve(list)
      .foreach(println)
  }

}
