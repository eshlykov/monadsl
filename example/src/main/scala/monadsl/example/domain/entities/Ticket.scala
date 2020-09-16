package monadsl.example.domain.entities

import monadsl.example.domain.values.TicketStatuses
import monadsl.example.domain.values.TicketStatuses.TicketStatus
import monadsl.example.infrastructure.model.TicketRow
import play.api.libs.json.{Format, Json}

case class Ticket(id: String,
                  name: String,
                  descriptionOpt: Option[String],
                  status: TicketStatus,
                  commentOpt: Option[String])

object Ticket {
  implicit lazy val ticketFormat: Format[Ticket] = Json.format[Ticket]

  def apply(ticketRow: TicketRow): Ticket =
    Ticket(
      id = ticketRow.id,
      name = ticketRow.name,
      descriptionOpt = ticketRow.description,
      status = TicketStatuses.withName(ticketRow.status),
      commentOpt = ticketRow.comment,
    )

  private implicit lazy val ticketStatusFormat: Format[TicketStatus] = Json.formatEnum(TicketStatuses)
}
