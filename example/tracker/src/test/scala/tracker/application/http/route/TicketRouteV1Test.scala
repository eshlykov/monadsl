package tracker.application.http.route

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import com.softwaremill.macwire.wire
import util.RouteTestBase

class TicketRouteV1Test extends RouteTestBase {

  "GET /api/v1/path" should "handle request" in new Wiring {
    (mockRoute.apply _)
      .expects(*)
      .returnsAsync(Complete(HttpResponse()))

    Get("/api/v1/path") ~> route ~> check {
      status shouldBe OK
    }
  }

  private trait Wiring extends MockWiring {
    lazy val route: Route = wire[TicketRouteV1]
  }
}
