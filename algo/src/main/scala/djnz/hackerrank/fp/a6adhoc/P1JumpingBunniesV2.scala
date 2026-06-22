package djnz.hackerrank.fp.a6adhoc

/** https://www.hackerrank.com/challenges/jumping-bunnies/problem
  * https://www.mesacc.edu/~scotz47781/mat120/notes/radicals/simplify/images/examples/prime_factorization.html
  */
object P1JumpingBunniesV2 { me =>

  def pow(n: Int, p: Int): Long = (1 to p).foldLeft(1L)((a, _) => a * n)

  def isPrime(n: Int): Boolean =
    if (n > 0 && n <= 2) true
    else (2 to math.sqrt(n).toInt).forall(n % _ != 0)

  def allPrimesTo(max: Int): Seq[Int] = (2 to max).filter(isPrime)

  def inc[A](map: Map[A, Int], key: A): Map[A, Int] =
    map.updatedWith(key) { ov =>
      Some(ov match {
        case None    => 1
        case Some(v) => v + 1
      })
    }

  implicit class IncOpsMap[A](map: Map[A, Int]) {
    def inc(key: A): Map[A, Int] = me.inc(map, key)
  }

  def factorize(n: Int, primes: List[Int], acc: Map[Int, Int]): Map[Int, Int] =
    if (primes.contains(n)) inc(acc, n)
    else primes match {
      case Nil => acc
      case ph :: _ if n % ph == 0 => factorize(n / ph, primes, acc.inc(ph))
      case _ :: pt => factorize(n, pt, acc)
    }

  def maxValByKeys[K](map1: Map[K, Int], map2: Map[K, Int]): Map[K, Int] =
    map2.foldLeft(map1) { case (acc, (k2, v2)) =>
      acc.updatedWith(k2) {
        case None     => Some(v2)
        case Some(v1) => Some(math.max(v1, v2))
      }
    }

  def process(xs: List[Int]) = {
    val primes = allPrimesTo(xs.max).toList
    xs
      .map(factorize(_, primes, Map.empty[Int, Int]))
      .reduce(maxValByKeys[Int])
      .map { case (n, p) => pow(n, p) }
      .product
  }

  def body(line: => String): Unit = {
    val _ = line.toInt
    val list = line.splitToInt.toList
    val r = process(list)
    println(r)
  }

  /** main to run from the console */
  //  def main(p: Array[String]): Unit = body { scala.io.StdIn.readLine }
  /** main to run from file */
  def main(p: Array[String]): Unit = processFile("bunnies.txt", body)
  def processFile(name: String, process: (=> String) => Unit): Unit = {
    val file = new java.io.File(this.getClass.getClassLoader.getResource(name).getFile)
    scala.util.Using(
      scala.io.Source.fromFile(file)
    ) { src =>
      val it = src.getLines().map(_.trim)
      process(it.next())
    }.getOrElse(???)
  }

  implicit class StringToOps(s: String) {
    def splitToInt: Array[Int] = s.split(" ").map(_.toInt)
    def toVectorInt: Vector[Int] = splitToInt.toVector
    def toListInt: List[Int] = splitToInt.toList
    def toTuple2Int: (Int, Int) = { val a = splitToInt; (a(0), a(1)) }
  }

}
