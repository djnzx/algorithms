package djnz.tools

trait StdInput {

  def next: String = scala.io.StdIn.readLine()
  def lines: Iterator[String] = scala.io.Source.stdin.getLines()

}
