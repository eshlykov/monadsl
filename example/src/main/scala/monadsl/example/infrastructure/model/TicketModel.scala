package monadsl.example.infrastructure.model

import slick.lifted.ProvenShape

trait TicketModel extends Model {

  import api._

  class TicketTable(tag: Tag) extends Table[TicketRow](tag, "tickets") {
    override def * : ProvenShape[TicketRow] = (id, name, description, status, comment).mapTo[TicketRow]

    def id: Rep[String] = column[String]("ticket_id", O.PrimaryKey)

    def name: Rep[String] = column[String]("name")

    def description: Rep[Option[String]] = column[Option[String]]("description")

    def status: Rep[String] = column[String]("status")

    def comment: Rep[Option[String]] = column[Option[String]]("comment")
  }

  val tickets: TableQuery[TicketTable] = TableQuery[TicketTable]

}
