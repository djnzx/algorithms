package djnz.hackerrank.fp.a4dp

import djnz.tools.ASuite

/** https://www.hackerrank.com/challenges/klotski/problem */
object P12Klotski {

  case class Loc(y: Int, x: Int) {
    override def toString: String = s"($y,$x)"
  }
  case class Change(src: Loc, dst: Loc) {
    override def toString: String = s"$src $dst"
  }
  case class Move(name: String, change: Change) {
    override def toString: String =
      s"$name $change"
  }

  trait Figure {
    def name: String
    def points: Set[Loc]
    lazy val origin: Loc = points.foldLeft(Loc(Int.MaxValue, Int.MaxValue)) { case (Loc(y, x), l) => Loc(y min l.y, x min l.x) }
    private def move0(dy: Int, dx: Int): Figure = Figure.of(
      name,
      points.map { case Loc(y, x) => Loc(y + dy, x + dx) }
    )
    private def move(dy: Int = 0, dx: Int = 0): (Figure, Move) = {
      val f2 = move0(dy, dx)
      val src = origin
      val dst = Loc(origin.y + dy, origin.x + dx)
      f2 -> Move(name, Change(src, dst))
    }
    private lazy val l = move(dx = -1)
    private lazy val r = move(dx = 1)
    private lazy val u = move(dy = -1)
    private lazy val d = move(dy = 1)
    def allMoves = Set(l, u, r, d)
    def at(loc: Loc) = origin == loc
  }

  case class FigureImpl(name: String, points: Set[Loc]) extends Figure

  object Figure {
    def of(name: String, data: Set[Loc]): Figure = FigureImpl(name, data)
  }

  case class Board(data: Set[Figure], w: Int, h: Int) {
    lazy val figures: Set[String] = data.map(_.name)
    def figure(c: String) = data.find(_.name == c).get
    def hasFigureAt(c: String, loc: Loc) = figure(c).at(loc)
    def withoutF(c: String) = copy(data = data - figure(c))
    def withF(f: Figure) = copy(data = data + f)

    def isOnBoard(f: Figure) = f.points.forall(p => p.x >= 0 && p.y >= 0 && p.x < w && p.y < h)
    def noOverlap(f: Figure) = data.forall(_.points.forall(l => !f.points.contains(l)))

    override def toString: String =
      data.flatMap(f => f.points.map(l => l -> f.name)).toMap match {
        case field => (0 until h)
            .map(y => (0 until w).map(x => field.getOrElse(Loc(y, x), '.')).mkString(" "))
            .mkString("\n")
      }
  }

  object Board {
    def parse(raw: Array[Array[String]]) = {
      val data = raw.indices.flatMap { y =>
        raw.head.indices.map { x =>
          raw(y)(x) -> Loc(y, x)
        }
      }
        .groupMapReduce { case (n, _) => n } { case (_, l) => Set(l) }(_ ++ _)
        .filter(_._1.head.isLetter)
        .map { case (name, pos) => Figure.of(name, pos) }
        .toSet
      Board(data, w = raw.head.length, h = raw.length)
    }
  }

  /** moves ONE figure ONE stop ALL possible directions + onBoard + noOverlap */
  def moveOneFigOneStep(board: Board, c: String): Set[(Board, Move)] = {
    val f = board.figure(c)
    val withoutF = board.withoutF(c)
    f.allMoves
      .filter { case (f, _) => withoutF.isOnBoard(f) }
      .filter { case (f, _) => withoutF.noOverlap(f) }
      .map { case (f, m) => withoutF.withF(f) -> m }
  }

  /** moves ONE figure ALL possible places + onBoard + noOverlap */
  def moveOneFigAllPossible(board: Board, c: String): Set[(Board, Move)] = {

    def go(curr: Set[(Board, Option[Move])], src: Option[Loc]): Set[(Board, Option[Move])] =
      curr.flatMap { case (b, _) => moveOneFigOneStep(b, c) } match {
        case next if next.isEmpty                                                           => curr
        case next if next.forall { case (b, _) => curr.exists { case (b0, _) => b == b0 } } => curr
        case next                                                                           =>
          val (next2, src2) = src match {
            case None       =>
              val next2 = next.map { case (b, m) => b -> Some(m) }
              next2 -> next2.head._2.get.change.src
            case Some(src1) =>
              val next2 = next.map { case (b, Move(n, c)) => b -> Some(Move(n, c.copy(src = src1))) }
              next2 -> src1
          }
          go(curr ++ next2, Some(src2))
      }

    go(Set(board -> None), None)
      .flatMap {
        case (b, Some(m)) if b != board => Set(b -> m) // board present and is different
        case _                          => Set.empty
      }
  }

  /** moves ALL figures ALL possible places + onBoard + noOverlap */
  def nextStep(board: Board): Set[(Board, Move)] =
    board.figures.flatMap(f => moveOneFigAllPossible(board, f))

  /** combine and pick shorter path */
  def combine[A](base: Map[A, Int], increment: Map[A, Int]) =
    increment.foldLeft(base) { case (base, (a, step)) =>
      base.updatedWith(a) {
        case None        => Some(step)
        case Some(step0) => Some(step0 min step)
      }
    }

  def solve(board: Board, c: String, target: Loc): Int = {

    def go(visited: Set[Board], toVisit: Map[Board, Int], solution: Int): Int = ???
//      toVisit.headOption match {
//        case None                                        => solution
//        case Some((_, step)) if step >= solution         => go(visited, toVisit.tail, solution)
//        case Some((b, _)) if visited.contains(b)         => go(visited, toVisit.tail, solution)
//        case Some((b, step)) if b.hasFigureAt(c, target) => go(visited + b, toVisit.tail, solution min step)
//        case Some((b, step))                             =>
//          val inc = nextStep(b).map(b => b -> (step + 1)).toMap
//          val toVisit2 = combine(toVisit, inc)
//          go(visited + b, toVisit2, solution)
//      }

    go(Set.empty, Map(board -> 0), Int.MaxValue)
  }

  def next() = scala.io.StdIn.readLine()

  def main(args: Array[String]): Unit = {
    val (rows, cols) = next().split(" ").map(_.toInt) match { case Array(r, c) => (r, c) }
    val raw = (1 to rows).map { _ =>
      next().split(" ").take(cols)
    }.toArray
    val f = next().trim
    val targetLoc = next().split(" ").map(_.toInt) match { case Array(y, x) => Loc(y, x) }

    val board = Board.parse(raw)
    // debug
    pprint.log(board)
    pprint.log(f)
    pprint.log(targetLoc)
    val r = solve(board, f, targetLoc)
    println(r)
  }

}

class P12Klotski extends ASuite {
  import P12Klotski._

  test("origin1") {

    Figure.of("A", Set(Loc(y = 0, x = 0), Loc(y = 0, x = 1), Loc(y = 1, x = 0)))
      .origin shouldBe Loc(y = 0, x = 0)

    Figure.of("A", Set(Loc(y = 1, x = 1), Loc(y = 2, x = 0), Loc(y = 2, x = 1)))
      .origin shouldBe Loc(y = 1, x = 0)

  }

  test("move figure") {
    val raw = Array(
      "AAC.",
      "ABC.",
      "BB..",
    ).map(_.split(""))
    val b = Board.parse(raw)
    println(b)

    println("-" * 10)

    moveOneFigOneStep(b, "C")
      .foreach { case (b, m) => println(s"$b\n$m") }

    println("-" * 10)

    moveOneFigAllPossible(b, "C")
      .foreach { case (b, m) => println(s"$b\n$m") }

  }

  test("all possible next moves") {
    val raw = Array(
      "A..",
      "B..",
    ).map(_.split(""))
    val b = Board.parse(raw)

    nextStep(b)
      .foreach { case (b, m) => println(s"$b\n$m") }
  }

}
