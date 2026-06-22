package djnz.hackerrank.fp.a6adhoc

import java.util.StringJoiner
import scala.collection.immutable.TreeMap
import scala.io.StdIn.readLine

/** https://www.hackerrank.com/challenges/missing-numbers-fp/problem */
object P6MissingNumbers {

  // less allocations
  def lazyIterator(s: String): Iterator[String] = new Iterator[String] {
    private val n = s.length
    private var i = 0

    def hasNext: Boolean = {
      while (i < n && s.charAt(i) == ' ') i += 1
      i < n
    }

    def next(): String = {
      val start = i
      while (i < n && s.charAt(i) != ' ') i += 1
      s.substring(start, i)
    }
  }

  def main(args: Array[String]): Unit = {

    val _ = readLine
    val list1 = lazyIterator(readLine).map(_.toInt)
    val _ = readLine
    val list2 = lazyIterator(readLine).map(_.toInt)

    val bigger = list2.foldLeft(TreeMap.empty[Int, Int]) {
      (map, n) =>
        map.updatedWith(n) {
          case None    => Some(1)
          case Some(v) => Some(v + 1)
        }
    }

    val residual = list1.foldLeft(bigger) {
      (map, n) =>
        map.updatedWith(n) {
          case Some(1) => None
          case Some(v) => Some(v - 1)
          case None    => None
        }
    }

    val outcome = residual
      .keySet
      .foldLeft(new StringJoiner(" ")) { case (sj, n) => sj.add(n.toString) }
      .toString

    println(outcome)
  }

}
