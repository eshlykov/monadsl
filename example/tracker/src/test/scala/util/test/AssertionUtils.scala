package util.test

import org.scalatest.Assertion
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.util.Try

trait AssertionUtils extends ScalaFutures with Matchers {

  implicit class FutureOps[T](val future: Future[T]) {
    def shouldReturn(result: T): Assertion = whenReady(future)(_ shouldBe result)

    def shouldFailWith[E: ClassTag]: Assertion = whenReady(future.failed)(_ shouldBe an[E])
  }

  implicit class FutureUnitOps(val future: Future[Unit]) {
    def shouldSucceed: Assertion = future.shouldReturn(())
  }

  implicit class FutureOptionOps[T](val future: Future[Option[T]]) {
    def shouldReturnNothing: Assertion = future.shouldReturn(None)

    def shouldReturnSome(result: T): Assertion = future.shouldReturn(Some(result))
  }

  implicit class TOps[T](result: => T) {
    def shouldFailWith[E: ClassTag]: Assertion = Try(result).failed.get shouldBe an[E]
  }

}
