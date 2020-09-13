package monadsl.example.application.http.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import monadsl.example.application.http.protocol.{CommentDto, TicketDto, TicketProjectionDto}
import monadsl.example.domain.services.{TicketFactory, TicketRepository, TicketService}
import play.api.libs.json.{JsObject, Writes}

import scala.concurrent.{ExecutionContext, Future}

class TicketRoute(ticketFactory: TicketFactory,
                  ticketService: TicketService,
                  ticketRepository: TicketRepository)
                 (implicit executionContext: ExecutionContext) extends Route {

  override def apply(v1: RequestContext): Future[RouteResult] = {
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
  }.apply(v1)

  private implicit lazy val unitWrites: Writes[Unit] = (_: Unit) => JsObject.empty

  private def createTicket(): Route =
    (post & entity(as[TicketProjectionDto])) { body =>
      complete {
        ticketFactory.create(
          name = body.name,
          description = body.description,
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
    (post & entity(as[CommentDto])) { body =>
      complete {
        ticketService.passCurrentStage(ticketId, body.comment)
      }
    }
}
