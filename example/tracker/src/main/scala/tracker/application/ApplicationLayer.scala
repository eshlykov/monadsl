package tracker.application

import akka.http.scaladsl.server.Route
import com.softwaremill.macwire.wire
import tracker.application.http.route.{TicketRouteImplV1, TicketRouteImplV2}
import tracker.domain.DomainLayer

trait ApplicationLayer extends DomainLayer {
  lazy val applicationRoutes: Seq[Route] = Seq(ticketRouteV1, ticketRouteV2)

  private lazy val ticketRouteV1: TicketRouteImplV1 = wire[TicketRouteImplV1]
  private lazy val ticketRouteV2: TicketRouteImplV2 = wire[TicketRouteImplV2]
}
