package monadsl.example.application.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import monadsl.example.application.protocol.{TicketDto, TicketProjectionDto}
import monadsl.example.domain.services.{TicketFactory, TicketRepository, TicketService}
import play.api.libs.json.{JsObject, Writes}

import scala.concurrent.ExecutionContext

class TicketRoute(ticketFactory: TicketFactory,
                  ticketService: TicketService,
                  ticketRepository: TicketRepository)
                 (implicit executionContext: ExecutionContext) {

  val route: Route =
    pathPrefix("tickets") {
      path("new") {
        createTicket()
      } ~ pathPrefix(Segment) { ticketId =>
        pathEnd {
          getTicket(ticketId)
        } ~ path("pass") {
          passStage(ticketId)
        }
      }
    }

  private implicit lazy val unitWrites: Writes[Unit] = (_: Unit) => JsObject.empty

  private def createTicket(): Route =
    (post & entity(as[TicketProjectionDto])) { ticket =>
      complete {
        ticketFactory.create(
          name = ticket.name,
          description = ticket.description,
        )
      }
    }

  private def getTicket(ticketId: String): Route =
    get {
      complete {
        ticketRepository.get(ticketId)
          .map(TicketDto.apply)
      }
    }

  private def passStage(ticketId: String): Route =
    post {
      complete {
        ticketService.passCurrentStage(ticketId)
      }
    }
}
