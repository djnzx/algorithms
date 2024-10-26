package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-hello-world-n-times/problem */
object P3HelloWorldNTimes {

  def f(n: Int) =
    (1 to n).foreach(_ => println("Hello World"))

}
