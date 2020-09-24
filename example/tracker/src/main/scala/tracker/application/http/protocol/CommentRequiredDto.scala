package tracker.application.http.protocol

import io.swagger.v3.oas.annotations.media.Schema
import play.api.libs.json.{Format, Json}

case class CommentRequiredDto(@Schema(description = "Комментарий", required = true) comment: String)

object CommentRequiredDto {
  implicit lazy val commentRequiredDtoFormat: Format[CommentRequiredDto] = Json.format[CommentRequiredDto]
}
