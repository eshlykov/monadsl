package monadsl.example.application.http.protocol

import play.api.libs.json.{Format, Json}

case class CommentDto(comment: Option[String])

object CommentDto {
  implicit lazy val commentDtoFormat: Format[CommentDto] = Json.format[CommentDto]
}
