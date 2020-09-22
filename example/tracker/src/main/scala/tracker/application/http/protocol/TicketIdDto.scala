package tracker.application.http.protocol

import io.swagger.v3.oas.annotations.media.Schema
import play.api.libs.json.{Format, Json}

case class TicketIdDto(@Schema(description = "Идентификатор задачи") id: String)

object TicketIdDto {
  implicit lazy val ticketdIdDtoFormat: Format[TicketIdDto] = Json.format[TicketIdDto]
}
