package monadsl.example.application.http.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.{Operation, Parameter}
import javax.ws.rs.core.MediaType
import javax.ws.rs.{GET, POST, Path, Produces}
import monadsl.example.application.http.protocol.{CommentDto, TicketDto, TicketIdDto, TicketProjectionDto}
import monadsl.example.domain.services.{TicketFactory, TicketRepository, TicketService}
import monadsl.example.infrastructure.model.{V1, V2, Version}
import play.api.libs.json.{JsObject, Writes}

import scala.concurrent.{ExecutionContext, Future}

sealed abstract class TicketRoute[V <: Version](version: String,
                                                ticketFactory: TicketFactory[V],
                                                ticketService: TicketService[V],
                                                ticketRepository: TicketRepository[V])
                                               (implicit executionContext: ExecutionContext) extends Route {

  override def apply(v1: RequestContext): Future[RouteResult] = {
    pathPrefix("api" / version) {
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
    }
  }.apply(v1)

  @POST
  @Operation(summary = "Создать новую задачу")
  @RequestBody(
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(required = true, implementation = classOf[TicketProjectionDto]),
    )),
    required = true,
  )
  @Produces(Array(MediaType.APPLICATION_JSON))
  @ApiResponse(
    description = "Идентификатор задачи",
    responseCode = "200",
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(required = true, implementation = classOf[TicketIdDto]),
    )),
  )
  @Path("/tickets/new")
  protected def createTicket(): Route =
    (post & entity(as[TicketProjectionDto])) { body =>
      complete {
        ticketFactory.create(body.name, body.description).map(TicketIdDto(_))
      }
    }

  @GET
  @Operation(summary = "Получить информацию о задаче")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @ApiResponse(
    description = "Информация о задаче",
    responseCode = "200",
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(required = true, implementation = classOf[TicketDto]),
    )),
  )
  @Path("tickets/{ticketId}")
  protected def getTicket(@Parameter(name = "ticketId", in = ParameterIn.PATH, required = true) ticketId: String): Route =
    get {
      complete {
        ticketRepository.get(ticketId).map(TicketDto(_))
      }
    }

  @POST
  @Operation(summary = "Передвинуть задачу на следующую стадию")
  @RequestBody(
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(required = true, implementation = classOf[CommentDto]),
    )),
    required = true,
  )
  @Produces(Array(MediaType.APPLICATION_JSON))
  @ApiResponse(
    description = "Результат операции",
    responseCode = "200",
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(required = true, implementation = classOf[Unit]),
    )),
  )
  @Path("/tickets/{ticketId}/pass")
  protected def passStage(@Parameter(name = "ticketId", in = ParameterIn.PATH, required = true) ticketId: String): Route =
    (post & entity(as[CommentDto])) { body =>
      complete {
        ticketService.passCurrentStage(ticketId, body.comment)
      }
    }

  private implicit lazy val unitWrites: Writes[Unit] = (_: Unit) => JsObject.empty
}

@Tag(name = "API для работы с трекером задач (реализация без DSL)")
@Path("/api/v1")
class TicketRouteImplV1(ticketFactory: TicketFactory[V1],
                        ticketService: TicketService[V1],
                        ticketRepository: TicketRepository[V1])
                       (implicit executionContext: ExecutionContext)
  extends TicketRoute[V1](
    version = "v1",
    ticketFactory = ticketFactory,
    ticketService = ticketService,
    ticketRepository = ticketRepository
  )

@Tag(name = "API для работы с трекером задач (реализация через DSL)")
@Path("/api/v2")
class TicketRouteImplV2(ticketFactory: TicketFactory[V2],
                        ticketService: TicketService[V2],
                        ticketRepository: TicketRepository[V2])
                       (implicit executionContext: ExecutionContext)
  extends TicketRoute[V2](
    version = "v2",
    ticketFactory = ticketFactory,
    ticketService = ticketService,
    ticketRepository = ticketRepository
  )
