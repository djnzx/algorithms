package djnz.tools

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

trait ASuite extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks
