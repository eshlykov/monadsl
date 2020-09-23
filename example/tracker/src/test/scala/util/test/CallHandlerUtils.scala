package util.test

import org.scalamock.handlers.CallHandler
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

trait CallHandlerUtils extends Matchers {

  implicit class CallHandlerFutureOps[T](val callHandler: CallHandler[Future[T]]) {
    def returnsAsync(result: T): callHandler.Derived = callHandler.returns(Future.successful(result))

    def throwsAsync(exception: Throwable): callHandler.Derived = callHandler.returns(Future.failed(exception))
  }

  implicit class CallHandlerFutureUnitOps(val callHandler: CallHandler[Future[Unit]]) {
    def returnsUnitAsync: callHandler.Derived = callHandler.returns(Future.successful(()))
  }

  implicit class CallHandlerFutureOptionOps[T](val callHandler: CallHandler[Future[Option[T]]]) {
    def returnsSomeAsync(result: T): callHandler.Derived = callHandler.returns(Future.successful(Some(result)))

    def returnsNoneAsync: callHandler.Derived = callHandler.returns(Future.successful(None))
  }

}
