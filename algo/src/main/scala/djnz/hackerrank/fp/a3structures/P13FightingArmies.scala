package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/fighting-armies/problem */
object P13FightingArmies {

  import scala.collection.immutable.TreeMap

  sealed trait Army {
    def strongest: Int
    def soldiers: TreeMap[Int, Int]
    def recruit(c: Int): Army = AWith(this, c)
    def kill(): Army = AWithout(this)
    def merge(that: Army): Army = () match {
      case _ if this.isEmpty => that
      case _ if that.isEmpty => this
      case _                 => AMerged(this, that)
    }
    def isEmpty: Boolean = false
  }

  case object AEmpty extends Army {
    def strongest = ???
    def soldiers = TreeMap.empty
    override def recruit(c: Int) = AOfSingle(c)
    override def kill() = this
    override def merge(that: Army) = that
    override def isEmpty = true
  }

  case class AOfSingle(strongest: Int) extends Army {
    lazy val soldiers = TreeMap(strongest -> 1)
    override def kill() = AEmpty
  }

  case class AWith(that: Army, c: Int) extends Army {
    lazy val strongest = that.strongest max c
    lazy val soldiers = that.soldiers + (c -> (that.soldiers.getOrElse(c, 0) + 1))
  }

  case class AWithout(that: Army) extends Army {
    lazy val strongest = soldiers.lastKey
    lazy val soldiers = that.soldiers.last match {
      case (key, 1)     => that.soldiers - key
      case (key, value) => that.soldiers + (key -> (value - 1))
    }
  }

  case class AMerged(l: Army, r: Army) extends Army {
    lazy val strongest = l.strongest max r.strongest
    lazy val soldiers = l.soldiers
      .foldLeft(r.soldiers) { case (both, (sid, pow)) =>
        val pow2 = both.getOrElse(sid, 0) + pow
        both + (sid -> pow2)
      }
  }

  sealed trait Command
  case class CmdFindStrongest(i: Int) extends Command
  sealed trait CmdModify extends Command
  case class CmdKillStrongest(i: Int) extends CmdModify
  case class CmdRecruit(i: Int, c: Int) extends CmdModify
  case class CmdMerge(i: Int, j: Int) extends CmdModify
  object Command {
    def parse(xs: Seq[Int]): Command = xs match {
      case Seq(1, i)    => CmdFindStrongest(i)
      case Seq(2, i)    => CmdKillStrongest(i)
      case Seq(3, i, c) => CmdRecruit(i, c)
      case Seq(4, i, j) => CmdMerge(i, j)
      case _            => ???
    }
  }

  def next() = scala.io.StdIn.readLine()

  type State = TreeMap[Int, Army]

  /** we use mutable builder here since it is performance only, not affecting functional approach in solution */
  def mk0(n: Int): State =
    TreeMap.newBuilder[Int, Army].addAll((1 to n).map(_ -> AEmpty)).result()

  def invoke(s: State, cmd: Command): (State, Option[Int]) = cmd match {
    case CmdFindStrongest(i) => (s, Some(s(i).strongest))
    case cmd: CmdModify      =>
      val s2 = cmd match {
        case CmdKillStrongest(i) => s.updated(i, s(i).kill())
        case CmdRecruit(i, c)    => s.updated(i, s(i).recruit(c))
        case CmdMerge(i, j)      => s.updated(i, s(i).merge(s(j))).updated(j, AEmpty)
      }
      (s2, None)
  }

  def main(args: Array[String]): Unit = {
    val (n, q) = next().split(" ").map(_.toInt) match { case Array(n, q) => (n, q) }

    (1 to q)
      .map(_ => next().split(" ").map(_.toInt))
      .map(Command.parse(_))
      .foldLeft(mk0(n)) { (s, cmd) =>
        val (s2, xo) = invoke(s, cmd)
        xo.foreach(println)
        s2
      }
  }
}
