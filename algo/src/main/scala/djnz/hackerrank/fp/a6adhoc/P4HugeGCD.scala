package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/huge-gcd-fp/problem */
object P4HugeGCD {

  type BD = java.math.BigDecimal

  def mult(v: Seq[Int]): BD = v.foldLeft(new BD(1))((acc, el) => acc.multiply(new BD(el)))

  def gcd(a: BD, b: BD): BD = {
    val r = a.remainder(b)
    if (r.compareTo(new BD(0)) == 0) b else gcd(b, r)
  }

  // TODO: factorize and find GCD

  def main(args: Array[String]) {
    import scala.io.StdIn.readLine
    val _ = readLine
    val as = readLine.split(" ").map(_.toInt)
    val _ = readLine
    val bs = readLine.split(" ").map(_.toInt)
    val ba = mult(as)
    val bb = mult(bs)
    val r = gcd(ba, bb).remainder(new BD(1_000_000_007))
    println(r)
  }

}
