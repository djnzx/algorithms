package djnz.hackerrank.sorting

/** https://www.hackerrank.com/challenges/closest-numbers/problem */
object ClosestNumbers extends App {

  def closestNumbers(arr: Array[Int]): Array[Int] =
    arr.sorted
      .iterator
      .sliding(2)
      .map { case Seq(a, b) => math.abs(a - b) -> (a, b) }
      .toList
      .groupMap { case (diff, _) => diff } { case (_, ab) => ab }
      .iterator
      .minBy { case (diff, _) => diff }
      ._2
      .sorted
      .flatMap { case (a, b) => Array(a, b) }
      .toArray

  val as = Array(1, 5, 7, 100, 101)
  val s = closestNumbers(as)
  println(s.mkString("Array(", ", ", ")"))

}
