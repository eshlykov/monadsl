package monadsl.example.application.http.protocol

import play.api.libs.json.{Format, Json}

case class TicketProjectionDto(name: String, description: Option[String])

object TicketProjectionDto {
  implicit lazy val ticketProjectionDtoFormat: Format[TicketProjectionDto] = Json.format[TicketProjectionDto]
}
