package monadsl.example.application

import akka.http.scaladsl.server.Route
import com.softwaremill.macwire.wire
import monadsl.example.application.http.route.TicketRoute
import monadsl.example.domain.DomainLayer

trait ApplicationLayer extends DomainLayer {
  lazy val applicationRoutes: Seq[Route] = Seq(ticketRoute)

  private lazy val ticketRoute: TicketRoute = wire[TicketRoute]
}
