package tracker.application.http.protocol

import io.swagger.v3.oas.annotations.media.Schema
import play.api.libs.json.{Format, Json}
import tracker.domain.entities.Ticket
import tracker.domain.values.TicketStages
import tracker.domain.values.TicketStages.TicketStage

case class TicketDto(@Schema(description = "Идентификатор", required = true) id: String,
                     @Schema(description = "Название", required = true) name: String,
                     @Schema(description = "Описание") description: Option[String],
                     @Schema(description = "Стадия", required = true, implementation = classOf[String]) stage: TicketStage,
                     @Schema(description = "Комментарий") comment: Option[String])

object TicketDto {
  implicit lazy val ticketDtoFormat: Format[TicketDto] = Json.format[TicketDto]

  def apply(ticket: Ticket): TicketDto =
    TicketDto(
      id = ticket.id,
      name = ticket.name,
      description = ticket.descriptionOpt,
      stage = ticket.stage,
      comment = ticket.commentOpt,
    )

  private implicit lazy val ticketStagesFormat: Format[TicketStage] = Json.formatEnum(TicketStages)
}
