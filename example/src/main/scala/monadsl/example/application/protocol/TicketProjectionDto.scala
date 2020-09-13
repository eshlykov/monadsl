package monadsl.example.application.protocol

import play.api.libs.json.{Format, Json}

case class TicketProjectionDto(name: String, description: String)

object TicketProjectionDto {
  implicit lazy val ticketDtoFormat: Format[TicketProjectionDto] = Json.format[TicketProjectionDto]
}
