package djnz.hackerrank.fp.a2recursion

// https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
/** https://www.hackerrank.com/challenges/the-tree-of-life/problem */
object P18TreeOfLife {

  sealed trait Tree {
    def value: Boolean

    def doApply(rule: Int, parentValue: Boolean = false): Tree

    final def change(rule: Int, steps: Int): Tree = steps match {
      case 0 => this
      case _ => doApply(rule).change(rule, steps - 1)
    }

    protected def next(rule: Int, parentValue: Boolean, leftValue: Boolean, rightValue: Boolean): Boolean = {
      def toBit(value: Boolean, index: Int) = (if (value) 1 else 0) << index

      val b15 = toBit(parentValue, 3) + toBit(leftValue, 2) + toBit(value, 1) + toBit(rightValue, 0)
      (rule & (1 << b15)) != 0
    }
  }

  case class Node(value: Boolean, left: Tree, right: Tree) extends Tree {
    def doApply(rule: Int, parentValue: Boolean): Tree = Node(
      this.next(rule, parentValue, left.value, right.value),
      left.doApply(rule, value),
      right.doApply(rule, value)
    )
  }
  case class Leaf(value: Boolean) extends Tree {
    def doApply(rule: Int, parentValue: Boolean): Tree = Leaf(
      this.next(rule, parentValue, leftValue = false, rightValue = false)
    )
  }
  object On extends Leaf(true)
  object Off extends Leaf(false)
  object Empty extends Tree {
    def value: Boolean = throw new Exception("Value of empty tree")
    def doApply(rule: Int, parentValue: Boolean): Tree = this
  }
  object Tree {

    private def go(cs: List[Char]): (Tree, List[Char]) = cs match {
      case Nil       => Empty -> Nil
      case '.' :: cs => Off   -> cs
      case 'X' :: cs => On    -> cs
      case '(' :: cs =>
        val (l, tail1) = go(cs)
        val (n, tail2) = go(tail1)
        val (r, tail3) = go(tail2)
        Node(n.value, l, r) -> tail3
      case _ :: cs   => go(cs)
    }

    def parse(cs: List[Char]): Tree = go(cs)._1
  }

  def atPath(t: Tree, path: List[Char]): Boolean = (path, t) match {
    case (']' :: _, tree)             => tree.value
    case ('<' :: path, Node(_, l, _)) => atPath(l, path)
    case ('>' :: path, Node(_, _, r)) => atPath(r, path)
    case _                            => sys.error("wrong path / tree entry")
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val rule = next().toInt
    val tree = Tree.parse(next().toList)
    val n = next().toInt

    case class Query(index: Int, time: Int, path: List[Char])

    def readQueries(rest: Int, time0: Int, acc: List[Query]): List[Query] = rest match {
      case 0 => acc
      case _ =>
        val (steps, path) = next().split(" ") match { case Array(a, b) => a.toInt -> b.toList.drop(1) }
        val time = time0 + steps
        readQueries(rest - 1, time, Query(n - rest, time, path) :: acc)
    }

    val queries = readQueries(n, 0, Nil).sortBy(_.time)

    case class Item(index: Int, value: Boolean) {
      def rep = value match {
        case true => 'X'
        case _    => '.'
      }
    }
    case class State(tree: Tree, time: Int, items: List[Item])
    val s0 = State(tree, 0, Nil)

    val outcome =
      queries
        .foldLeft(s0) { (s, q) =>
          val tree = s.tree.change(rule, q.time - s.time)
          State(
            tree,
            q.time,
            Item(q.index, atPath(tree, q.path)) :: s.items
          )
        }
        .items
        .sortBy(_.index)
        .map(_.rep)
        .mkString("\n")

    println(outcome)
  }

}
