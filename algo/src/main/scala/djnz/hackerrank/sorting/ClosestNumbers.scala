package djnz.hackerrank.sorting

/** https://www.hackerrank.com/challenges/closest-numbers/problem */
object ClosestNumbers extends App {

  def closestNumbers(arr: Array[Int]): Array[Int] =
    arr.sorted
      .iterator
      .sliding(2)
      .toArray
      .groupMap { case Seq(a, b) => math.abs(a - b) } { case Seq(a, b) => (a, b) }
      .minBy { case (diff, _) => diff }
      ._2
      .sorted
      .flatMap { case (a, b) => Array(a, b) }

  val as = Array(1, 5, 7, 100, 101)
  val s = closestNumbers(as)
  println(s.mkString("Array(", ", ", ")"))

}
