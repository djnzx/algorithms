package djnz.hackerrank.fp.a1intro

import scala.annotation.tailrec

/** https://www.hackerrank.com/challenges/fp-reverse-a-list/problem */
object P8ReverseList {

  /** stdlib */
  def f2(arr: List[Int]): List[Int] = arr.reverse

  /** classic recursive, but O(n^2^) */
  def f1(arr: List[Int]): List[Int] = arr match {
    case Nil    => Nil
    case h :: t => f1(t) :+ h
  }

  /** tail recursive */
  def f(arr: List[Int]): List[Int] = {

    @tailrec
    def go(xs: List[Int], acc: List[Int]): List[Int] = xs match {
      case h :: t => go(t, h :: acc)
      case _      => acc
    }

    go(arr, Nil)
  }

}
