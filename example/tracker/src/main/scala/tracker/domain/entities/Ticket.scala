package tracker.domain.entities

import play.api.libs.json.{Format, Json}
import tracker.domain.values.{InvalidTicketStatusException, TicketStatuses}
import tracker.domain.values.TicketStatuses.TicketStatus
import tracker.infrastructure.model.TicketRow

import scala.util.{Failure, Try}


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
      status = toTicketStatus(ticketRow.status),
      commentOpt = ticketRow.comment,
    )

  private implicit lazy val ticketStatusFormat: Format[TicketStatus] = Json.formatEnum(TicketStatuses)

  private def toTicketStatus(status: String): TicketStatus =
    Try(TicketStatuses.withName(status))
      .getOrElse(throw new InvalidTicketStatusException)
}
