package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/fighting-armies/problem */
object P13FightingArmies {

  import scala.collection.immutable.TreeMap

  sealed trait Army {
    def strongest: Int
    def values: TreeMap[Int, Int]
    def add(c: Int): Army = new AddedArmy(this, c)
    def remove(): Army = new RemovedArmy(this)
    def merge(that: Army): Army = () match {
      case _ if this.isEmpty => that
      case _ if that.isEmpty => this
      case _                 => new MergedArmy(this, that)
    }
    def isEmpty: Boolean = false
  }

  case object Empty extends Army {
    override def isEmpty: Boolean = true
    override def strongest: Int = ???
    override def add(c: Int): Army = new SingleArmy(c)
    override def remove(): Army = ???
    override def merge(that: Army): Army = that
    override def values: TreeMap[Int, Int] = ???
  }

  class SingleArmy(val strongest: Int) extends Army {
    override lazy val values: TreeMap[Int, Int] = TreeMap(strongest -> 1)
    override def remove(): Army = Empty
  }

  class AddedArmy(army: Army, c: Int) extends Army {
    override lazy val strongest: Int = math.max(army.strongest, c)
    override lazy val values: TreeMap[Int, Int] =
      army.values + (c -> (army.values.getOrElse(c, 0) + 1))
  }

  class RemovedArmy(army: Army) extends Army {
    override lazy val strongest: Int = values.lastKey
    override lazy val values: TreeMap[Int, Int] =
      army.values.last match {
        case (key, 1)     => army.values - key
        case (key, value) => army.values + (key -> (value - 1))
      }
  }

  class MergedArmy(left: Army, right: Army) extends Army {
    override lazy val strongest: Int = math.max(left.strongest, right.strongest)
    override lazy val values: TreeMap[Int, Int] =
      left.values.foldLeft(right.values) { (acc, v) =>
        val nextValue = acc.getOrElse(v._1, 0) + v._2
        acc + (v._1 -> nextValue)
      }
  }

  sealed trait Command
  case class CmdFindStrongest(i: Int) extends Command
  case class CmdKillStrongest(i: Int) extends Command
  case class CmdRecruit(i: Int, c: Int) extends Command
  case class CmdMerge(i: Int, j: Int) extends Command
  object Command {
    def parse(xs: Seq[Int]): Command = xs match {
      case Seq(1, i)    => CmdFindStrongest(i - 1)
      case Seq(2, i)    => CmdKillStrongest(i - 1)
      case Seq(3, i, c) => CmdRecruit(i - 1, c)
      case Seq(4, i, j) => CmdMerge(i - 1, j)
      case _            => ???
    }
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val (n, q) = next().split(" ").map(_.toInt) match { case Array(n, q) => (n, q) }

    // TODO: implement via immutable state
    val aa = Array.fill[Army](n)(Empty)

    def solve(cmd: Command) = cmd match {
      case CmdFindStrongest(i) => Some(aa(i).strongest)
      case CmdKillStrongest(i) => aa(i) = aa(i).remove(); None
      case CmdRecruit(i, c)    => aa(i) = aa(i).add(c); None
      case CmdMerge(i, j)      =>
        aa(i) = aa(i).merge(aa(j - 1))
        aa(j - 1) = Empty
        None
    }

    (1 to q)
      .map(_ => next().split(" ").map(_.toInt))
      .map(Command.parse(_))
      .flatMap(solve)
      .foreach(println)
  }
}
