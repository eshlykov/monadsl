package monadsl.example.application.protocol

import monadsl.example.domain.entities.Ticket
import monadsl.example.domain.values.TicketStatuses
import monadsl.example.domain.values.TicketStatuses.TicketStatus
import play.api.libs.json.{Format, Json}

case class TicketDto(id: String,
                     name: String,
                     description: String,
                     status: TicketStatus,
                     comment: String) {
}

object TicketDto {
  implicit lazy val ticketDtoFormat: Format[TicketDto] = Json.format[TicketDto]

  def apply(ticket: Ticket): TicketDto =
    TicketDto(
      id = ticket.id,
      name = ticket.name,
      description = ticket.description,
      status = ticket.status,
      comment = ticket.comment,
    )

  private implicit lazy val ticketStatusesFormat: Format[TicketStatus] = Json.formatEnum(TicketStatuses)
}
