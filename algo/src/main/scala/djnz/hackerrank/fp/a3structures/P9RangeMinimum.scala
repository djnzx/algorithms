package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/range-minimum-query/problem */
object P9RangeMinimum {

  def next() = scala.io.StdIn.readLine()

  case class Node(left: Int, right: Int, min: Int)

  class SegmentTree(values: Array[Int]) {
    // TODO: immutable ?
    private val nodes = Array.fill[Node](4 * values.length)(null)
    build(1, 0, values.length - 1)

    private def build(index: Int, left: Int, right: Int): Node = {
      val min = if (left == right) values(left)
      else {
        val median = (left + right) / 2
        val l = build(2 * index, left, median)
        val r = build(2 * index + 1, median + 1, right)
        l.min min r.min
      }
      val node = Node(left, right, min)
      nodes(index) = node
      node
    }

    private def findMin(index: Int, left: Int, right: Int): Int = nodes(index) match {
      case cur if contains(cur, left, right)   => cur.min
      case cur if intersects(cur, left, right) =>
        findMin(2 * index, left, right) min findMin(2 * index + 1, left, right)
      case _                                   => Integer.MAX_VALUE
    }

    def findMin(left: Int, right: Int): Int = findMin(1, left, right)

    private def intersects(current: Node, left: Int, right: Int): Boolean =
      left <= current.left && current.left <= right ||
        current.left <= left && left <= current.right

    private def contains(node: Node, left: Int, right: Int): Boolean =
      left <= node.left && node.right <= right
  }

  /** Segment tree, complexity Log N */
  def solve(st: SegmentTree, mnx: (Int, Int)): Int = st.findMin(mnx._1, mnx._2)

  def main(args: Array[String]): Unit = {
    val n = next().split(" ").map(_.toInt).drop(1).head
    val data = next().split(" ").map(_.toInt)
    val st = new SegmentTree(data)

    (1 to n)
      .map { _ =>
        next().split(" ").map(_.toInt) match {
          case Array(min, max) => solve(st, min -> max)
        }
      }
      .foreach(println)
  }

}
