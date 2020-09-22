package tracker.application

import akka.http.scaladsl.server.Route
import com.softwaremill.macwire.wire
import tracker.application.http.route.{TicketRoute, TicketRouteImplV1, TicketRouteImplV2}
import tracker.domain.DomainLayer
import tracker.infrastructure.model.{V1, V2}

trait ApplicationLayer extends DomainLayer {
  lazy val applicationRoutes: Seq[Route] = Seq(ticketV1route, ticketV2route)

  private lazy val ticketV1route: TicketRoute[V1] = wire[TicketRouteImplV1]
  private lazy val ticketV2route: TicketRoute[V2] = wire[TicketRouteImplV2]
}