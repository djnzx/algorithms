package djnz.interviews.chi

import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

object SevenToPowerOfN {

  def naive7(n: Int) = (1 to n).foldLeft(1)((a, _) => (a * 7) % 10)

  def smart7(n: Int) = n % 4 match {
    case 1 => 7
    case 2 => 9
    case 3 => 3
    case 0 => 1
    case _ => sys.error("impossibly by design")
  }

}

class SevenToPowerOfN extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import SevenToPowerOfN._

  val data = Table(
    ("input", "output"),
    1             -> 7, // 7^1 =     7
    2             -> 9, // 7^2 =    49
    3             -> 3, // 7^3 =   343
    4             -> 1, // 7^4 =  2401
    5             -> 7, // 7^5 = 16807
    1_000_000_000 -> 1  // ... = ....1
  )

  test("naive") {
    forAll(data) { case (in, out) =>
      naive7(in) shouldBe out
    }
  }

  test("smart") {
    forAll(data) { case (in, out) =>
      smart7(in) shouldBe out
    }
  }

  test("both are the same") {
    val genN: Gen[Int] = Gen.posNum[Int]
    implicit val arbN: Arbitrary[Int] = Arbitrary(genN)

    forAll { (n: Int) =>
      naive7(n) shouldBe smart7(n)
    }
  }

}
