package tracker.application.http.protocol

import io.swagger.v3.oas.annotations.media.Schema
import play.api.libs.json.{Format, Json}

case class TicketProjectionDto(@Schema(description = "Название", required = true) name: String,
                               @Schema(description = "Описание") description: Option[String])

object TicketProjectionDto {
  implicit lazy val ticketProjectionDtoFormat: Format[TicketProjectionDto] = Json.format[TicketProjectionDto]
}
