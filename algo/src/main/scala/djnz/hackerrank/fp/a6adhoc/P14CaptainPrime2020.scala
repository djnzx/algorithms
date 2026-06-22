package djnz.hackerrank.fp.a6adhoc

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/** https://www.hackerrank.com/challenges/captain-prime/problem */
object P14CaptainPrime2020 {

  import scala.io.StdIn.readLine

  val primes: LazyList[Int] = 2 #:: LazyList.from(3, 2).filter(isPrime)
  def isPrime(x: Int): Boolean = (x > 1) && primes.takeWhile(_ <= math.sqrt(x.toDouble)).forall(x % _ != 0)

  def pow(n: Int, p: Int): Int = (1 to p).foldLeft(1)((a, _) => a * n)

  def isPrime(ns: Seq[Int]): Boolean = ns.forall(isPrime)

  def toLeft(n: Int, acc: List[Int] = Nil): List[Int] =
    n / 10 match {
      case 0    => acc
      case next => toLeft(next, next :: acc)
    }

  def toRight(n: Int, p: Int, acc: List[Int] = Nil): List[Int] =
    p match {
      case 0 => acc
      case _ =>
        n % pow(10, p) match {
          case 0    => acc
          case next => toRight(next, p - 1, next :: acc)
        }
    }

  // 12345 => 1234, 123, 12, 1
  def allToLeft(s: String): List[Int] = toLeft(s.toInt)

  // 12345") shouldBe List(5, 45, 345, 2345
  def allToRight(s: String): List[Int] = toRight(s.toInt, s.length - 1)

  def solve(ns: String): String =
    isPrime(ns.toInt) match {
      case false                 => "DEAD"
      case _ if ns.contains('0') => "DEAD"
      case _                     => (allToLeft(ns).forall(isPrime), allToRight(ns).forall(isPrime)) match {
          case (true, true)  => "CENTRAL"
          case (true, false) => "RIGHT"
          case (false, true) => "LEFT"
          case _             => "DEAD"
        }
    }

  def main(p: Array[String]): Unit =
    (1 to readLine.toInt)
      .foreach { _ =>
        val x = solve(readLine)
        println(x)
      }

}

class P14CaptainPrime2020 extends AnyFunSuite with Matchers {
  import P14CaptainPrime2020._

  test("toLeft") {
    toLeft(12345) shouldBe List(1, 12, 123, 1234)
  }

  test("allToLeft") {
    allToLeft("12345") shouldBe List(1, 12, 123, 1234)
  }

  test("allToRight") {
    allToRight("12345") shouldBe List(5, 45, 345, 2345)
  }

}
