package tracker.infrastructure.model

case class TicketRow(id: String,
                     name: String,
                     description: Option[String],
                     stage: String,
                     comment: Option[String])
