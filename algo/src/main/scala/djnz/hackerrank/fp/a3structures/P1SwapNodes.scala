package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/swap-nodes/problem */
object P1SwapNodes {

  sealed trait Tree
  case class Node(value: Int, left: Tree, right: Tree) extends Tree
  case object Empty extends Tree

  object Tree {

    def parse(nodes: Seq[(Int, Int)]): Tree = {

      def go(index: Int): Tree = index match {
        case -1 => Empty
        case _  =>
          val (il, ir) = nodes(index - 1)
          Node(index, go(il), go(ir))
      }

      go(1)
    }

    def inOrder(t: Tree): Seq[Int] = t match {
      case Empty         => Seq.empty
      case Node(v, l, r) => (inOrder(l) :+ v) ++ inOrder(r)
    }

    def swap(t: Tree, k: Int, depth: Int): Tree = t match {
      case Empty         => t
      case Node(v, l, r) =>
        val (l2, r2) = depth % k match {
          case 0 => (r, l)
          case _ => (l, r)
        }
        Node(
          v,
          Tree.swap(l2, k, depth + 1),
          Tree.swap(r2, k, depth + 1)
        )

    }
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val n = next().toInt
    val nodes = (1 to n).map { _ =>
      next().split(" ").map(_.toInt) match {
        case Array(x, y) => x -> y
      }
    }

    val t0 = Tree.parse(nodes)

    val tc = next().toInt
    val queries = (1 to tc).map(_ => next().toInt)

    queries.foldLeft(t0) { (t, q) =>
      val t2 = Tree.swap(t, q, 1)
      val ino = Tree.inOrder(t2)
      println(ino.mkString(" "))
      t2
    }
  }

}
