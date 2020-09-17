package monadsl.example.application.http.protocol

import io.swagger.v3.oas.annotations.media.Schema
import play.api.libs.json.{Format, Json}

case class CommentDto(@Schema(description = "Комментарий") comment: Option[String])

object CommentDto {
  implicit lazy val commentDtoFormat: Format[CommentDto] = Json.format[CommentDto]
}
