package monadsl.example.application.http.protocol

import monadsl.example.domain.entities.Ticket
import monadsl.example.domain.values.TicketStatuses
import monadsl.example.domain.values.TicketStatuses.TicketStatus
import play.api.libs.json.{Format, Json}

case class TicketDto(id: String,
                     name: String,
                     description: Option[String],
                     status: TicketStatus,
                     comment: Option[String])

object TicketDto {
  implicit lazy val ticketDtoFormat: Format[TicketDto] = Json.format[TicketDto]

  def apply(ticket: Ticket): TicketDto =
    TicketDto(
      id = ticket.id,
      name = ticket.name,
      description = ticket.descriptionOpt,
      status = ticket.status,
      comment = ticket.commentOpt,
    )

  private implicit lazy val ticketStatusesFormat: Format[TicketStatus] = Json.formatEnum(TicketStatuses)
}
