package djnz.hackerrank.fp.a2recursion

import djnz.tools.ASuite
import djnz.tools.Console

/** https://www.hackerrank.com/challenges/crosswords-101/problem */
object P10Crosswords {

  def readBoard(console: Console) =
    (1 to 10)
      .foldLeft(List.empty[String]) { case (acc, _) => console.readLine() :: acc }
      .reverse

  def readWords(console: Console) = console.readLine().split(";").toList

  val indexes = (0 to 9).toList

  sealed trait Dir
  object Dir {
    case object Hor extends Dir
    case object Ver extends Dir
    val all: List[Dir] = List(Hor, Ver)
  }

  type Board = List[String]

  def transpose(board: Board): Board =
    board.head.indices.toList
      .map { x =>
        board.indices
          .foldLeft(List.empty[Char]) { case (acc, y) => board(y)(x) :: acc }
          .reverse
          .mkString
      }

  def tryToReplaceAt(row: String, word: String, at: Int): Option[String] = {
    val L = row.length

    def combine(i: Int, acc: List[Char]): Option[String] = i match {
      case L                                            => Some(acc.reverse.mkString)          // done
      case i if i < at || i >= word.length + at         => combine(i + 1, row(i) :: acc)       // copy original
      case i if row(i) == '-' || row(i) == word(i - at) => combine(i + 1, word(i - at) :: acc) // take letter from the word
      case _                                            => None
    }

    if (word.length + at > row.length) None
    else combine(0, Nil)
  }

  def tryToFitH(word: String, board: Board, x: Int, y: Int): Option[Board] =
    tryToReplaceAt(board(y), word, x)
      .map { newRow =>
        val before = board.take(y)
        val after = board.drop(y + 1)
        before ++ (newRow +: after)
      }

  def tryToFitV(word: String, board: Board, x: Int, y: Int): Option[Board] =
    Some(transpose(board))
      .flatMap(tryToFitH(word, _, y, x))
      .map(transpose)

  def tryToFit(word: String, board: Board, x: Int, y: Int, dir: Dir): Option[Board] =
    dir match {
      case Dir.Hor => tryToFitH(word, board, x, y)
      case Dir.Ver => tryToFitV(word, board, x, y)
    }

  def fitWord(word: String, board: Board): List[Board] =
    Dir.all.flatMap { dir =>
      indexes.flatMap { x =>
        indexes.flatMap(y => tryToFit(word, board, x, y, dir))
      }
    }

  def solve(words: List[String], board: Board): List[Board] =
    words match {
      case Nil           => List(board)
      case word :: words => fitWord(word, board).flatMap(b => solve(words, b))
    }

  def doSolve(console: Console) = {
    val board = readBoard(console)
    val words = readWords(console)
    solve(words, board).head
      .foreach(console.printLine)
  }

  def main(args: Array[String]): Unit = doSolve(Console.real)

}

class P10Crosswords extends ASuite {

  import P10Crosswords._

  test("replace") {
    (0 to 9).foreach { x =>
      val outcome = tryToReplaceAt("---I------", "INDIA", x)
      pprint.pprintln(outcome)
    }
  }

  test("transpose") {
    val board = List(
      "ABCD",
      "EFGH",
      "IJKL",
    )
    val transposedExp = List(
      "AEI",
      "BFJ",
      "CGK",
      "DHL",
    )
    val transposed = transpose(board)
    transposed.foreach(x => pprint.pprintln(x))
    transposed shouldBe transposedExp
  }

  val boardInput = List(
    "+-++++++++",
    "+-++++++++",
    "+-++++++++",
    "+-----++++",
    "+-+++-++++",
    "+-+++-++++",
    "+++++-++++",
    "++------++",
    "+++++-++++",
    "+++++-++++",
  )
  val wordsRaw = "LONDON;DELHI;ICELAND;ANKARA"
  val words = wordsRaw.split(";").toList
  val boardOutput = List(
    "+L++++++++",
    "+O++++++++",
    "+N++++++++",
    "+DELHI++++",
    "+O+++C++++",
    "+N+++E++++",
    "+++++L++++",
    "++ANKARA++",
    "+++++N++++",
    "+++++D++++",
  )

  test("solve") {
    solve(words, boardInput).head shouldBe boardOutput
  }

  test("whole app") {
    val testConsole = Console.test(boardInput :+ wordsRaw)

    doSolve(testConsole)
    testConsole.output shouldBe boardOutput
  }

}
