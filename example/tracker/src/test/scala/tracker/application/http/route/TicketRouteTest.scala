package tracker.application.http.route

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.server.Route
import com.softwaremill.macwire.wire
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import play.api.libs.json.{JsObject, JsValue, Json}
import tracker.application.http.protocol.{TicketDto, TicketIdDto}
import tracker.domain.entities.Ticket
import tracker.domain.values.TicketStages
import tracker.infrastructure.model.V1
import util.RouteTestBase

class TicketRouteTest extends RouteTestBase {

  "POST /tickets/new" should "create new ticket" in new Wiring {
    val body: JsValue = Json.obj("name" -> name, "description" -> description)

    (mockTicketFactoryV1.create _)
      .expects(name, Some(description))
      .returnsAsync(id)

    Post(Uri("/tickets/new"), body) ~> route ~> check {
      status shouldBe OK
      responseAs[JsValue].as[TicketIdDto] shouldBe TicketIdDto(id)
    }
  }

  "GET /tickets/{id}" should "return ticket" in new Wiring {
    (mockTicketRepositoryV1.get _)
      .expects(id)
      .returnsAsync(ticket)

    Get(Uri(s"/tickets/$id")) ~> route ~> check {
      status shouldBe OK
      responseAs[JsValue].as[TicketDto] shouldBe ticketDto
    }
  }

  "POST /ticket/{id}/pass" should "update ticket's stage" in new Wiring {
    val body: JsValue = Json.obj("comment" -> comment)

    (mockTicketServiceV1.passCurrentStage _)
      .expects(id, Some(comment))
      .returnsAsync(id)

    Post(Uri(s"/tickets/$id/pass"), body) ~> route ~> check {
      status shouldBe OK
      responseAs[JsValue] shouldBe JsObject.empty
    }
  }

  "POST /ticket/{id}/reject" should "update ticket's stage" in new Wiring {
    val body: JsValue = Json.obj("stage" -> TicketStages.specification.toString, "comment" -> comment)

    (mockTicketServiceV1.returnToPreviousStage _)
      .expects(id, TicketStages.specification, comment)
      .returnsUnitAsync

    Post(Uri(s"/tickets/$id/reject"), body) ~> route ~> check {
      status shouldBe OK
      responseAs[JsValue] shouldBe JsObject.empty
    }
  }

  "POST /ticket/{id}/trash" should "update ticket's stage" in new Wiring {
  }

  private trait Wiring extends MockWiring {
    lazy val route: Route = wire[TicketRoute[V1]]
  }

  private lazy val id = "id"
  private lazy val name = "name"
  private lazy val description = "description"
  private lazy val comment = "comment"

  private lazy val ticket = Ticket(id, name, Some(description), TicketStages.testing, Some(comment))
  private lazy val ticketDto = TicketDto(id, name, Some(description), TicketStages.testing, Some(comment))
}
