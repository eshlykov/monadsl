package tracker.application.http.route

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import com.softwaremill.macwire.wire
import util.RouteTestBase

class TicketRouteV2Test extends RouteTestBase {

  "GET /api/v2/path" should "handle request" in new Wiring {
    (mockRoute.apply _)
      .expects(*)
      .returnsAsync(Complete(HttpResponse()))

    Get("/api/v2/path") ~> route ~> check {
      status shouldBe OK
    }
  }

  private trait Wiring extends MockWiring {
    lazy val route: Route = wire[TicketRouteV2]
  }

}
