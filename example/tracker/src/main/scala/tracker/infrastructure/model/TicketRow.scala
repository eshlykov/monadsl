package tracker.infrastructure.model

case class TicketRow(id: String,
                    name: String,
                    description: Option[String],
                    status: String,
                    comment: Option[String])
