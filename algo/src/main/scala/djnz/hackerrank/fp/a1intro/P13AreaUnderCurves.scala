package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/area-under-curves-and-volume-of-revolving-a-curv/problem */
object P13AreaUnderCurves {

  def readLine() = scala.io.StdIn.readLine()
  val STEP = 0.001
  val STEPW = (1 / STEP).toInt

  def pow(x: Double, n: Int): Double = {
    val v = (1 to scala.math.abs(n)).foldLeft(1.0)((acc, _) => acc * x)
    n match {
      case n if n > 0 => v
      case n if n < 0 => 1 / v
      case _          => 1
    }
  }

  val square = (v: Double) => v * STEP
  val volume = (v: Double) => pow(v, 2) * scala.math.Pi * STEP

  def valueAt(ks: List[Int], ps: List[Int], x: Double)(fx: Double => Double): Double =
    fx((ks zip ps).foldLeft(0.0) { case (acc, (k, p)) => acc + k * pow(x, p) })

  def f(ks: List[Int], ps: List[Int], x: Double): Double =
    valueAt(ks, ps, x)(square)

  def area(ks: List[Int], ps: List[Int], x: Double): Double =
    valueAt(ks, ps, x)(volume)

  def range(l: Int, r: Int): Seq[Double] =
    (l * STEPW to r * STEPW).map(_.toDouble * STEP).toVector

  def summation(
    func: (List[Int], List[Int], Double) => Double,
    upperLimit: Int,
    lowerLimit: Int,
    coefficients: List[Int],
    powers: List[Int]
  ): Double =
    range(lowerLimit, upperLimit)
      .foldLeft(0.0) { (acc, x) =>
        acc + func(coefficients, powers, x)
      }

}
