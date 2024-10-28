package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/valid-bst/problem
  * https://www.geeksforgeeks.org/check-if-a-given-array-can-represent-preorder-traversal-of-binary-search-tree/
  */
object P3IsPreorderBST {

  case class XState(stack: List[Int], valid: Boolean, root: Int)
  val s0 = XState(Nil, valid = true, Integer.MIN_VALUE)

  def isBST(data: Vector[Int]): Boolean =
    data.foldLeft(s0) {
        case (st@XState(_, false, _), _) => st
        case (st, x) if x < st.root => st.copy(valid = false)
        case (st, x) =>
          def refine(s0: List[Int], r0: Int): (List[Int], Int) =
            if (s0.nonEmpty && s0.head < x) refine(s0.tail, s0.head) else (s0, r0)

          val (s2, r2) = refine(st.stack, st.root)
          st.copy(stack = x :: s2, root = r2)
    }
      .valid

  def process(line1: String, tree: String): Boolean = {
    val data = tree.split(" ").map(_.toInt).toVector

    isBST(data)
  }

  def next() = scala.io.StdIn.readLine()

  def main(p: Array[String]): Unit =
    (1 to next().toInt)
      .map(_ => process(next(), next()))
      .map(if (_) "YES" else "NO")
      .foreach(println)

}
