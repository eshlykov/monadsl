package util

import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}

import scala.concurrent.duration._

trait RouteTestBase extends TestBase with ScalatestRouteTest {
  implicit val routeTimeout: RouteTestTimeout = RouteTestTimeout(10.seconds)
}
