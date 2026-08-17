package djnz.leetcode

// https://leetcode.com/problems/monotone-increasing-digits/description/
object T738 {

  def monotoneIncreasingDigits(n: Int): Int =
    n.toString
      .map(_.asDigit)
      .foldRight(List.empty[Int]) {
        case (d, Nil)                         => List(d)
        case (d, suffix @ d1 :: _) if d <= d1 => d :: suffix
        case (d, suffix)                      => (d - 1) :: suffix.map(_ => 9)
      }
      .mkString
      .toInt

}
