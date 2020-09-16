package monadsl.example.domain.entities

import monadsl.example.domain.values.TicketStatuses
import monadsl.example.domain.values.TicketStatuses.TicketStatus
import play.api.libs.json.{Format, Json}

case class Ticket(id: String,
                  name: String,
                  descriptionOpt: Option[String],
                  status: TicketStatus,
                  commentOpt: Option[String])

object Ticket {
  implicit lazy val ticketFormat: Format[Ticket] = Json.format[Ticket]

  private implicit lazy val ticketStatusFormat: Format[TicketStatus] = Json.formatEnum(TicketStatuses)
}
