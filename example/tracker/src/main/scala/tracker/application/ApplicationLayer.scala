package tracker.application

import akka.http.scaladsl.server.Route
import com.softwaremill.macwire.wire
import tracker.application.http.route.{TicketRoute, TicketRouteImplV1, TicketRouteImplV2}
import tracker.domain.DomainLayer
import tracker.infrastructure.model.{V1, V2}

trait ApplicationLayer extends DomainLayer {
  lazy val applicationRoutes: Seq[Route] = Seq(ticketRouteV1, ticketRouteV2)

  private lazy val ticketRouteV1: TicketRoute[V1] = wire[TicketRouteImplV1]
  private lazy val ticketRouteV2: TicketRoute[V2] = wire[TicketRouteImplV2]
}
