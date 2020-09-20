package monadsl.example.infrastructure.model

import slick.lifted.ProvenShape

import scala.reflect.{ClassTag, classTag}

trait TicketModel extends Model {

  import api._

  class TicketTable[V <: Version : ClassTag](tag: Tag) extends Table[TicketRow](tag, tableName) {
    override def * : ProvenShape[TicketRow] = (id, name, description, status, comment).mapTo[TicketRow]

    def id: Rep[String] = column[String]("id", O.PrimaryKey)

    def name: Rep[String] = column[String]("name")

    def description: Rep[Option[String]] = column[Option[String]]("description")

    def status: Rep[String] = column[String]("status")

    def comment: Rep[Option[String]] = column[Option[String]]("comment")
  }

  def tickets[V <: Version : ClassTag]: TableQuery[TicketTable[V]] = TableQuery[TicketTable[V]]

  private def tableName[V <: Version : ClassTag] = s"tickets_${classTag[V].runtimeClass.getSimpleName.toLowerCase}"
}
