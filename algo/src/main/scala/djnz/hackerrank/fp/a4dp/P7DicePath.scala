package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/dice-path/problem */
object P7DicePath {

  import scala.util.chaining.scalaUtilChainingOps
  case class Dice(t: Int = 1, b: Int = 6, l: Int = 3, r: Int = 4, f: Int = 2, k: Int = 5) {
    lazy val value = t
    lazy val turnR = copy(r = t, t = l, l = b, b = r)
    lazy val turnD = copy(t = k, k = b, b = f, f = t)
  }
  val s0 = Dice()
  case class Cell(dice: Dice, sum: Int) {
    def turnR: Cell = dice.turnR.pipe(dice => Cell(dice, sum + dice.value))
    def turnD: Cell = dice.turnD.pipe(dice => Cell(dice, sum + dice.value))
  }

  def solveToMax(m: Int, n: Int) = {
    val a = Array.fill[Iterable[Cell]](m, n)(None)
    val indicesY = a.indices
    val indicesX = a(0).indices

    indicesX.foldLeft(s0) { (dice, x) =>
      a(0)(x) = Some(Cell(dice, dice.value + (if (x == 0) 0 else a(0)(x - 1).head.sum)))
      dice.turnR
    }

    indicesY.foldLeft(s0) { (dice, y) =>
      a(y)(0) = Some(Cell(dice, dice.value + (if (y == 0) 0 else a(y - 1)(0).head.sum)))
      dice.turnD
    }

    indicesY.drop(1).foreach { y =>
      indicesX.drop(1).foreach { x =>
        val top = a(y - 1)(x).map(_.turnD)
        val left = a(y)(x - 1).map(_.turnR)
        a(y)(x) = (left ++ top).groupMapReduce(_.dice)(_.sum)(_ max _).map(Cell.tupled)
      }
    }

    a
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val testcases = (1 to n)
      .map(_ => next())
      .map(_.split(" ").map(_.toInt))
      .flatMap {
        case Array(m, n) => Some(m -> n)
        case _           => None
      }

    val (maxM, maxN) = testcases
      .foldLeft(0 -> 0) { case ((maxM, maxN), (m, n)) => (maxM max m) -> (maxN max n) }

    val all = solveToMax(maxM, maxN)

    testcases
      .map { case (m, n) => all(m - 1)(n - 1).maxBy(_.sum).sum }
      .foreach(println)
  }

}
