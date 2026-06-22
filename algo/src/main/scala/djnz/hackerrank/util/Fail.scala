package djnz.hackerrank.util

trait Fail {

  def ux(msg: String): IllegalStateException = new IllegalStateException(msg)
  def failX(msg: String): Nothing = throw ux(msg)

}
