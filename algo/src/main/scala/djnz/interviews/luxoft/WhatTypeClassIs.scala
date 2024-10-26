package djnz.interviews.luxoft

import org.scalatest.funsuite.AnyFunSuite

class WhatTypeClassIs extends AnyFunSuite {

  /** 1. define behavior */
  trait Show[A] {
    def show(a: A): String
  }

  /** 2. create instances for types I want to attach my behavior */
  implicit val ss: Show[String] = new Show[String] {
    override def show(a: String): String = s"I'm a String: $a"
  }
  implicit val si: Show[Int] = new Show[Int] {
    override def show(a: Int): String = s"I'm an Int: $a"
  }

  test("using without syntax") {
    ss.show("AAA")
    si.show(123)
  }

  /** 3. define syntax */
  implicit class ShowSyntax[A: Show](a: A) {
    def show() = implicitly[Show[A]].show(a)
  }

  test("using with syntax") {
    "AAA".show()
    123.show()
  }

}
