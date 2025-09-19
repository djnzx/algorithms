package djnz.hackerrank.fp.a4dp

object P12KlotskiMutable {

  import scala.collection.immutable.TreeSet
  import scala.collection.mutable

  def next() = scala.io.StdIn.readLine()

  case class Loc(x: Int, y: Int) {
    def move(dx: Int, dy: Int): Loc = Loc(x + dx, y + dy)
    def move(dLoc: Loc): Loc = move(dLoc.x, dLoc.y)
  }

  case class Figure(name: String, at: List[Loc], id: Int)

  case class State(lastLoc: Loc, curLoc: Loc, topLefts: Map[Int, Set[Loc]]) extends Ordered[State] {
    lazy val _hashCode: Int = topLefts.hashCode()
    override def hashCode(): Int = 31 * lastLoc.hashCode() + _hashCode
    def compare(that: State): Int = data(that).item.step.compareTo(data(this).item.step)
  }

  case class Move(id: Int, prevLoc: Loc, nextLoc: Loc)

  case class Item(step: Int, moves: List[Move])

  case class Outcome(field: Set[Loc], item: Item)

  private val dirs = List(Loc(-1, 0), Loc(1, 0), Loc(0, -1), Loc(0, 1))

  // TODO
  private val data = mutable.Map.empty[State, Outcome]

  def solve(blocks: Map[Int, Set[Loc]], initialState: State, targetLoc: Loc, xSize: Int, ySize: Int): List[Move] = {

    def move(at: Set[Loc], topLeft: Loc): Set[Loc] = at.map(_.move(topLeft))

    def isCorrect(id: Int, topLeft: Loc, field: Set[Loc]): Boolean =
      move(blocks(id), topLeft)
        .forall(at => at.x >= 0 && at.x < xSize && at.y >= 0 && at.y < ySize && !field.contains(at))

    val loc2 = initialState.topLefts
      .flatMap { case (id, topLefts) => topLefts.flatMap(topLeft => move(blocks(id), topLeft)) }
      .toSet

    // TODO
    data += initialState -> Outcome(loc2, Item(0, Nil))

    // TODO
    val queue = mutable.PriorityQueue[State](initialState)

    // TODO
    var bestMoves: List[Move] = Nil
    // TODO
    var bestStep = Int.MaxValue

    case class Best(moves: List[Move], step: Int)
    object Best {
      def state0 = Best(List.empty, Int.MaxValue)
    }

    def traverse[S, A: Ordering](xs: TreeSet[A], state: S)(f: (S, A) => (S, IterableOnce[A])): S =
      xs.headOption match {
        case None    => state
        case Some(a) =>
          val (s2, aa) = f(state, a)
          traverse(xs.tail ++ aa, s2)(f)
      }

    while (queue.nonEmpty) {
      val state = queue.dequeue()

      val nextPairs =
        state.topLefts
          .flatMap { case (id, topLefts) =>
            topLefts.flatMap { topLeft =>
              dirs.map { dir =>
                val nextTopLeft = topLeft.move(dir)
                val nextState = State(
                  nextTopLeft,
                  if (topLeft == state.curLoc) nextTopLeft else state.curLoc,
                  state.topLefts + (id -> (state.topLefts(id) - topLeft + nextTopLeft))
                )

                val acc = data(state)
                val tempField = acc.field -- move(blocks(id), topLeft)
                val nextField = tempField ++ move(blocks(id), nextTopLeft)

                Option.when(isCorrect(id, nextTopLeft, tempField)) {
                  val isSame = topLeft == state.lastLoc
                  val nextStep = if (isSame) acc.item.step else acc.item.step + 1

                  val nextMoves =
                    if (isSame)
                      Move(id, acc.item.moves.head.prevLoc, nextTopLeft) :: acc.item.moves.tail
                    else
                      Move(id, topLeft, nextTopLeft) :: acc.item.moves

                  val nextItem = Item(nextStep, nextMoves)
                  val nextOutcome = Outcome(nextField, nextItem)

                  nextState -> nextOutcome
                }
              }
            }
          }.flatten

      nextPairs
        .foreach { case (nextState, nextOutcome) =>
          val maybeExistOutcome = data.get(nextState)

          if (maybeExistOutcome.isEmpty || nextOutcome.item.step < maybeExistOutcome.get.item.step) {
            if (nextOutcome.item.step < bestStep) {
              if (nextState.curLoc == targetLoc) {
                bestMoves = nextOutcome.item.moves // TODO
                bestStep = nextOutcome.item.step   // TODO
              }

              data(nextState) = nextOutcome // TODO
              queue += nextState            // TODO
            }
          }
        }
    }

    bestMoves.reverse
  }

  def main(args: Array[String]): Unit = {

    val Array(m, n) = next().split("\\s").map(_.toInt)

    case class Cell(x: Int, y: Int, c: String)

    val board = (0 until m)
      .flatMap(y =>
        next().split(" ").zipWithIndex
          .map { case (s, x) => Cell(x, y, s) }
      )

    val targetChar = next()

    val targetLoc = {
      val Array(targetY, targetX) = next().split("\\s").map(_.toInt)
      Loc(targetX, targetY)
    }

    val figures: Seq[(Loc, Figure)] = board
      .filter(_.c.exists(_ != '.'))
      .groupBy(_.c)
      .toSeq
      .map { case (c, list) =>
        val leftX = list.map(_.x).min
        val topY = list.map(_.y).min
        val coords = list.map(cell => Loc(cell.x - leftX, cell.y - topY))
        def toId(xs: Seq[Loc]): Int = xs.map(coord => 1 << (3 * coord.y + coord.x)).sum
        (Loc(leftX, topY), Figure(c, coords.toList, toId(coords)))
      }

    val tempBlocks = figures.map(_._2).toIndexedSeq
    // TODO
    val symbols: mutable.Map[Loc, String] = mutable.Map.empty[Loc, String] ++
      tempBlocks.indices.map(i => figures(i)._1 -> tempBlocks(i).name)

    val blocks = figures.map(_._2).groupBy(_.id).map { case (id, list) => id -> list.head.at.toSet }

    val initialLoc = figures(tempBlocks.indexWhere(_.name == targetChar))._1

    val state0 = State(
      Loc(-1, -1),
      initialLoc,
      figures.map { case (loc, block) => block.id -> loc }
        .groupBy(_._1)
        .map { case (id, list) => id -> list.map(_._2).toSet }
    )

    val solution = solve(blocks, state0, targetLoc, n, m)

    print(solution.length)

    val steps = solution
      .map { mv =>
        val symbol = symbols(mv.prevLoc)
        symbols -= mv.prevLoc
        symbols += mv.nextLoc -> symbol
        s"\n$symbol (${mv.prevLoc.y},${mv.prevLoc.x}) (${mv.nextLoc.y},${mv.nextLoc.x})"
      }
      .mkString

    println(steps)
  }

}
