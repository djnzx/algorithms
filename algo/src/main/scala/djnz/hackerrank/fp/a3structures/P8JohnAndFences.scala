package djnz.hackerrank.fp.a3structures

/** https://www.hackerrank.com/challenges/john-and-fences/problem */
object P8JohnAndFences {

  def next() = scala.io.StdIn.readLine()

  def calcFence(fence: Vector[Int]) = {

    def pass1(i: Int, progress: List[Int], mx: Int): (List[Int], Int) = progress match {
      case _ if i == fence.length        => (progress, mx)
      case Nil                           => pass1(i + 1, i :: progress, mx)
      case h :: _ if fence(i) > fence(h) => pass1(i + 1, i :: progress, mx)
      case h :: Nil                      => pass1(i, Nil, mx max fence(h) * i)
      case h :: (t @ g :: _)             => pass1(i, t, mx max fence(h) * (i - 1 - g))
    }

    pass1(0, Nil, 0) match {
      case (stack, mx) =>
        stack.foldLeft(mx) { case (pmax, h) =>
          pmax max fence(h) * (fence.length - 1 - h)
        }
    }

  }

  def main(args: Array[String]): Unit = {
    val _ = next()
    val fence = next().split(" ").map(_.toInt).toVector
    val max = calcFence(fence)
    println(max)
  }

}
