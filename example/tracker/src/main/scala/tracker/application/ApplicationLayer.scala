package tracker.application

import akka.http.scaladsl.server.Route
import com.softwaremill.macwire.wire
import tracker.application.http.route.{TicketRoute, TicketRouteV1, TicketRouteV2}
import tracker.domain.DomainLayer
import tracker.infrastructure.model.{V1, V2}

trait ApplicationLayer extends DomainLayer {
  lazy val applicationRoutes: Seq[Route] = Seq(ticketRouteV1, ticketRouteV2)

  private lazy val ticketRouteV1: TicketRouteV1 = {
    val route = wire[TicketRoute[V1]]
    wire[TicketRouteV1]
  }

  private lazy val ticketRouteV2: TicketRouteV2 = {
    val route = wire[TicketRoute[V2]]
    wire[TicketRouteV2]
  }
}
