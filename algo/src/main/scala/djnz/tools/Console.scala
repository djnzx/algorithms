package djnz.tools

import scala.collection.mutable
import scala.io.StdIn

trait Console {
  def readLine(): String
  def printLine(line: String): Unit
}

object Console {

  private object Real extends Console {

    override def readLine(): String =
      StdIn.readLine()

    override def printLine(line: String): Unit =
      println(line)

  }

  class Test(input: Iterable[String]) extends Console {
    private val it =
      input.iterator

    private val buffer =
      mutable.ListBuffer.empty[String]

    override def readLine(): String =
      it.nextOption().getOrElse("")

    override def printLine(line: String): Unit =
      buffer.append(line)

    def output: Seq[String] =
      buffer.toSeq

  }

  val real: Console = Real
  def test(input: Iterable[String]): Test = new Test(input)

}
