package djnz.hackerrank.fp.a1intro

/** https://www.hackerrank.com/challenges/fp-list-replication/problem */
object P4ListReplication {

  def f2(num: Int, arr: List[Int]): List[Int] =
    arr.flatMap(x => List.fill(num)(x))

  def f(num: Int, arr: List[Int]): List[Int] =
    arr.flatMap(x => (1 to num).map(_ => x))

}
