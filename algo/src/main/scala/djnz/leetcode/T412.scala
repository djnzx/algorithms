package djnz.leetcode

// https://leetcode.com/problems/fizz-buzz/description/
object T412 {

  def solve(x: Int): String = x match {
    case x if x % 3 == 0 && x % 5 == 0 => "FizzBuzz"
    case x if x % 3 == 0               => "Fizz"
    case x if x % 5 == 0               => "Buzz"
    case _                             => x.toString
  }

  def fizzBuzz(n: Int): List[String] =
    (1 to n)
      .map(solve)
      .toList

}
