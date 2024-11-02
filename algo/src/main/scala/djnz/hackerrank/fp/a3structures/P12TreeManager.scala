package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/tree-manager/problem
  * Immutable State,
  * Immutable Tree
  */
object P12TreeManager {
  sealed trait Command
  sealed trait CmdModify extends Command
  sealed trait CmdDisplay extends Command
  object Command {
    import scala.util.Try
    final case object CmdPrint extends CmdDisplay             // print
    final case class CmdChangeValue(x: Int) extends CmdModify // change current value
    final case object CmdVisitLeft extends CmdModify          // navigate left
    final case object CmdVisitRight extends CmdModify         // navigate right
    final case object CmdVisitParent extends CmdModify        // navigate parent
    final case class CmdVisitChild(n: Int) extends CmdModify  // navigate to child N (from 1)
    final case class CmdInsertLeft(x: Int) extends CmdModify  // insert left rel to current
    final case class CmdInsertRight(x: Int) extends CmdModify // insert right rel to current
    final case class CmdInsertChild(x: Int) extends CmdModify // add a new child (to 0 pos)
    final case object CmdDelete extends CmdModify
    // command parser
    def parse(s: String): Option[Command] = Try(s match {
      case "print"            => CmdPrint
      case s"change $x"       => CmdChangeValue(x.toInt)
      case "visit left"       => CmdVisitLeft
      case "visit right"      => CmdVisitRight
      case "visit parent"     => CmdVisitParent
      case s"visit child $n"  => CmdVisitChild(n.toInt)
      case s"insert left $x"  => CmdInsertLeft(x.toInt)
      case s"insert right $x" => CmdInsertRight(x.toInt)
      case s"insert child $x" => CmdInsertChild(x.toInt)
      case "delete"           => CmdDelete
      case _                  => ???
    }).toOption
  }

  // tree representation
  final case class Node(value: Int, children: Vector[Node] = Vector.empty)
  // path node representation
  final case class PathNode(link: Node, pos: Int, parent: Option[Node])
  // recursively update two immutable structures
  def updated(chain: List[PathNode], newNode: Node): List[PathNode] = chain match {
    case h :: Nil =>
      val newPathNode = h.copy(link = newNode)
      newPathNode :: Nil
    case h :: t   =>
      val parent = h.parent.get
      val newChildren = parent.children.updated(h.pos - 1, newNode)
      val newParent = h.parent.get.copy(children = newChildren)
      val newPathNode = h.copy(link = newNode, parent = Some(newParent))
      newPathNode :: updated(t, newParent)
    case _        => ???
  }
  // path representation: List[PathNode]
  case class Path(chain: List[PathNode]) {
    private def curr = chain.head
    private def currNode = curr.link
    private def curSiblings = curr.parent.get.children
    private def insertAt[A](v: Vector[A], pos: Int, x: A): Vector[A] =
      (v.slice(0, pos), v.slice(pos, v.size)) match {
        case (l, r) => l ++ Vector(x) ++ r
      }
    private def removeAt[A](v: Vector[A], pos: Int): Vector[A] = {
      val left = v.slice(0, pos)
      val right = v.slice(pos + 1, v.size)
      left ++ right
    }

    def value: Int = currNode.value
    def moveL = chain match {
      case h :: t => Path(h.copy(pos = h.pos - 1, link = curSiblings(h.pos - 2)) :: t)
      case _      => ???
    }
    def moveR = chain match {
      case h :: t => Path(h.copy(pos = h.pos + 1, link = curSiblings(h.pos)) :: t)
      case _      => ???
    }
    def moveUp = Path(chain.tail)
    def moveDown(n: Int) = Path(PathNode(currNode.children(n - 1), n, Some(currNode)) :: chain)
    def changeValue(x: Int) = {
      val newNode = currNode.copy(value = x)
      Path(updated(chain, newNode))
    }
    def insertLeft(x: Int) = {
      val newParent = curr.parent.get.copy(children = insertAt(curSiblings, curr.pos - 1, Node(x)))
      Path(chain match {
        case h :: t => h.copy(parent = Some(newParent), pos = h.pos + 1) :: updated(t, newParent)
        case _      => ???
      })
    }
    def insertRight(x: Int) = {
      val newParent = curr.parent.get.copy(children = insertAt(curSiblings, curr.pos, Node(x)))
      Path(chain match {
        case h :: t => h.copy(parent = Some(newParent)) :: updated(t, newParent)
        case _      => ???
      })
    }
    def insertChild(x: Int) = {
      val newChildren = Node(x) +: currNode.children
      val newNode = currNode.copy(children = newChildren)
      Path(updated(chain, newNode))
    }
    def delete = {
      val newParentChildren = removeAt(curSiblings, curr.pos - 1)
      val newParent = curr.parent.get.copy(children = newParentChildren)
      Path(chain match {
        case _ :: t => updated(t, newParent)
        case _      => ???
      })
    }
  }
  // run one command on the state
  import Command._
  def run(path: Path, cmd: Command): (Path, Option[Int]) = cmd match {
    case CmdPrint       => (path, Some(path.value))
    case cmd: CmdModify =>
      val path2 = cmd match {
        case CmdChangeValue(x) => path.changeValue(x)
        case CmdVisitLeft      => path.moveL
        case CmdVisitRight     => path.moveR
        case CmdVisitParent    => path.moveUp
        case CmdVisitChild(n)  => path.moveDown(n)
        case CmdInsertLeft(x)  => path.insertLeft(x)
        case CmdInsertRight(x) => path.insertRight(x)
        case CmdInsertChild(x) => path.insertChild(x)
        case CmdDelete         => path.delete
      }
      path2 -> None
  }
  // process all commands
  def solve(data: Seq[String]) = {
    // initial state - list from one node points to our list with 1 elem
    val p0 = Path(List(PathNode(Node(0), 1, None)))
    val (_, outcome) = data
      .flatMap(parse)
      .foldLeft(
        (p0, List.empty[Int]),
      ) { case ((p, acc), cmd) =>
        run(p, cmd) match {
          case (p2, Some(v)) => (p2, v :: acc)
          case (p2, None)    => (p2, acc)
        }
      }
    outcome.reverse.mkString("\n")
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val N = next().toInt
    val list = (1 to N).map(_ => next())
    val r = solve(list)
    println(r)
  }

}
