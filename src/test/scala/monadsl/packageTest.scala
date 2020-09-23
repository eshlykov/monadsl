package monadsl

import cats.Monad
import cats.instances.option._
import cats.instances.string._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class packageTest extends AnyFlatSpec with Matchers {
  "when" should "use default case when condition is true" in {
    when(trueOpt)(Option(ok)).otherwise(Option(fail)) shouldBe Some(ok)
  }

  it should "use otherwise case when condition is false" in {
    when(falseOpt)(Option(ok)).otherwise(Option(fail)) shouldBe Some(fail)
  }

  it should "return None if condition is None" in {
    when(none)(Option(ok)).otherwise(Option(fail)) shouldBe None
  }

  it should "return empty element for monoids when otherwise case is omitted" in {
    toF(when(falseOpt)(Option(ok))) shouldBe Some("")
  }

  it should "work with pure conditions" in {
    when(cond = true)(Option(ok)).otherwise(Option(fail)) shouldBe Some(ok)
    when(cond = false)(Option(ok)).otherwise(Option(fail)) shouldBe Some(fail)
  }

  "and" should "return second condition if first conditions is true" in {
    conditionToF(trueOpt.and(trueOpt)) shouldBe trueOpt
    conditionToF(trueOpt.and(falseOpt)) shouldBe falseOpt
    conditionToF(trueOpt.and(none)) shouldBe none
  }

  it should "return false if first condition is false" in {
    conditionToF(falseOpt.and(trueOpt)) shouldBe falseOpt
    conditionToF(falseOpt.and(falseOpt)) shouldBe falseOpt
    conditionToF(falseOpt.and(none)) shouldBe falseOpt
  }

  it should "return None if first condition is none" in {
    conditionToF(none.and(trueOpt)) shouldBe none
    conditionToF(none.and(falseOpt)) shouldBe none
    conditionToF(none.and(none)) shouldBe none
  }

  it should "work with pure conditions" in {
    conditionToF(true.and(that = true)) shouldBe trueOpt
    conditionToF(true.and(that = false)) shouldBe falseOpt
    conditionToF(false.and(that = true)) shouldBe falseOpt
    conditionToF(false.and(that = false)) shouldBe falseOpt
  }

  "or" should "return true if first condition is true" in {
    conditionToF(trueOpt.or(trueOpt)) shouldBe trueOpt
    conditionToF(trueOpt.or(falseOpt)) shouldBe trueOpt
    conditionToF(trueOpt.or(none)) shouldBe trueOpt
  }

  it should "return second condition if first conditions is false" in {
    conditionToF(falseOpt.or(trueOpt)) shouldBe trueOpt
    conditionToF(falseOpt.or(falseOpt)) shouldBe falseOpt
    conditionToF(falseOpt.or(none)) shouldBe none
  }

  it should "return None if first condition is none" in {
    conditionToF(none.or(trueOpt)) shouldBe none
    conditionToF(none.or(falseOpt)) shouldBe none
    conditionToF(none.or(none)) shouldBe none
  }

  it should "work with pure conditions" in {
    conditionToF(true.or(that = true)) shouldBe trueOpt
    conditionToF(true.or(that = false)) shouldBe trueOpt
    conditionToF(false.or(that = true)) shouldBe trueOpt
    conditionToF(false.or(that = false)) shouldBe falseOpt
  }

  "not" should "return opposite value" in {
    conditionToF(monadsl.not(trueOpt)) shouldBe falseOpt
    conditionToF(monadsl.not(falseOpt)) shouldBe trueOpt
  }

  it should "return None for None" in {
    conditionToF(monadsl.not(none)) shouldBe none
  }

  it should "work with pure conditions" in {
    conditionToF(monadsl.not(cond = true)) shouldBe falseOpt
    conditionToF(monadsl.not(cond = false)) shouldBe trueOpt
  }

  "is" should "return true if value is expected" in {
    Option(ok).is(ok) shouldBe trueOpt
  }

  it should "return false for unexpected values" in {
    Option(ok).is(fail) shouldBe falseOpt
  }

  it should "return None if there is no value to compare" in {
    Option.empty[String].is(ok) shouldBe none
  }

  private lazy val ok = "ok"
  private lazy val fail = "fail"

  private lazy val trueOpt = Option(true)
  private lazy val falseOpt = Option(false)
  private lazy val none = Option.empty[Boolean]

  private def conditionToF[F[_]](condition: Condition)(implicit F: Monad[F]): F[Boolean] =
    condition match {
      case cond: ConditionF[F] => cond.cond
      case cond: ConditionT[F] => F.pure(cond.cond)
    }

}
