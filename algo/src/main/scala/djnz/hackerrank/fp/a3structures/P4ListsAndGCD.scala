package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/lists-and-gcd/problem */
object P4ListsAndGCD {

  @scala.annotation.tailrec
  def toPairs(x: List[Int], acc: List[(Int, Int)]): List[(Int, Int)] = x match {
    case Nil            => acc.reverse
    case a :: b :: tail => toPairs(tail, (a, b) :: acc)
    case _              => sys.error("we don't handle odd count of elements")
  }

  def process(data: List[List[Int]]) = {
    val maps = data.map(toPairs(_, Nil)).map(_.toMap)
    maps
      .foldLeft(Set.empty[Int])((acc, item) => acc ++ item.keys)
      .map(p => (p, maps.map(_.getOrElse(p, 0)).min))
      .filter(_._2 > 0)
      .toList
      .sorted
      .map(t => s"${t._1} ${t._2}")
      .mkString(" ")
  }

  def next() = scala.io.StdIn.readLine()
  def read1() = next().split(" ").map(_.toInt).toList
  def readN(n: Int, acc: List[List[Int]]): List[List[Int]] = n match {
    case 0 => acc.reverse
    case _ => readN(n - 1, read1() :: acc)
  }

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val r = process(readN(n, Nil))
    println(r)
  }

}
