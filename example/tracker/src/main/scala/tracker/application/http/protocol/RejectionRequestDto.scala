package tracker.application.http.protocol

import io.swagger.v3.oas.annotations.media.Schema
import play.api.libs.json.{Format, Json}
import tracker.domain.values.TicketStages
import tracker.domain.values.TicketStages.TicketStage

case class RejectionRequestDto(@Schema(description = "Целевая стадия задача", implementation = classOf[String]) stage: TicketStage,
                               @Schema(description = "Коммантарий") comment: String)

object RejectionRequestDto {
  implicit lazy val rejectionRequestDtoFormat: Format[RejectionRequestDto] = Json.format[RejectionRequestDto]

  private implicit lazy val ticketStagesFormat: Format[TicketStage] = Json.formatEnum(TicketStages)
}
