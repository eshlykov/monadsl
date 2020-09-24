package tracker.domain.entities

import play.api.libs.json.{Format, Json}
import tracker.domain.values.TicketStages.TicketStage
import tracker.domain.values.{InvalidTicketStageException, TicketStages}
import tracker.infrastructure.model.TicketRow

import scala.util.Try

case class Ticket(id: String,
                  name: String,
                  descriptionOpt: Option[String],
                  stage: TicketStage,
                  commentOpt: Option[String])

object Ticket {
  implicit lazy val ticketFormat: Format[Ticket] = Json.format[Ticket]

  def apply(ticketRow: TicketRow): Ticket =
    Ticket(
      id = ticketRow.id,
      name = ticketRow.name,
      descriptionOpt = ticketRow.description,
      stage = toTicketStage(ticketRow.stage),
      commentOpt = ticketRow.comment,
    )

  private implicit lazy val ticketStageFormat: Format[TicketStage] = Json.formatEnum(TicketStages)

  private def toTicketStage(stage: String): TicketStage =
    Try(TicketStages.withName(stage))
      .getOrElse(throw new InvalidTicketStageException)
}
