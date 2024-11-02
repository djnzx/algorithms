package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/valid-bst/problem
  * https://www.geeksforgeeks.org/check-if-a-given-array-can-represent-preorder-traversal-of-binary-search-tree/
  */
object P3IsPreorderBST {

  case class XState(stack: List[Int], root: Int, valid: Boolean = true)
  val s0 = XState(Nil, Integer.MIN_VALUE)

  def isBST(data: Vector[Int]): Boolean = data.foldLeft(s0) {
    case (st @ XState(_, _, false), _) => st
    case (st, x) if x < st.root        => st.copy(valid = false)
    case (st, x)                       =>
      def go(s0: List[Int], r0: Int): (List[Int], Int) = s0 match {
        case h :: t if h < x => go(t, h)
        case s0              => (s0, r0)
      }

      go(st.stack, st.root) match {
        case (s2, r2) => XState(x :: s2, r2)
      }
  }
    .valid

  def solve(line1: String, tree: String): Boolean = {
    val data = tree.split(" ").map(_.toInt).toVector

    isBST(data)
  }

  def next() = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit =
    (1 to next().toInt)
      .map(_ => solve(next(), next()))
      .map(if (_) "YES" else "NO")
      .foreach(println)

}
