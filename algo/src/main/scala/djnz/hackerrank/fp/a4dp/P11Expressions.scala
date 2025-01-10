package djnz.hackerrank.fp.a4dp

/** https://www.hackerrank.com/challenges/expressions/problem */
object P11Expressions {

  def next() = scala.io.StdIn.readLine()

  val opsAll = LazyList('*', '+', '-')

  implicit class CharOps(c: Char) {
    def calc(a: BigInt, b: Long): BigInt = c match {
      case '+' => a + b
      case '-' => a - b
      case '*' => a * b
    }
  }
  case class A(xa: BigInt, ops: List[Char]) {
    def collect(op: Char, x: Int): A = A(op.calc(xa, x), op :: ops)
  }

  def fits(n: BigInt) = n % 101 == 0

  def doSolve(a: A, nums: List[Int]): LazyList[A] = nums match {
    case Nil if fits(a.xa) => LazyList(a)
    case Nil               => LazyList.empty
    case n :: ns           => opsAll.flatMap(op => doSolve(a.collect(op, n), ns))
  }

  def solve(numbers: List[Int]) = {
    val ops = doSolve(A(numbers.head, Nil), numbers.tail).head.ops.reverse
    represent(numbers, ops)
  }

  def represent(numbers: List[Int], ops: List[Char]) =
    numbers.tail.zip(ops)
      .foldLeft(new StringBuilder(numbers.head.toString)) {
        case (a, (n, op)) => a ++= s"$op$n"
      }.toString

  def main(args: Array[String]): Unit = {
    val _ = next()
    val ns = next().split(" ").map(_.toInt).toList
    println(solve(ns))
  }

}
