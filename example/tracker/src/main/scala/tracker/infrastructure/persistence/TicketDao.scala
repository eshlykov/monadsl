package tracker.infrastructure.persistence

import slick.jdbc.JdbcBackend
import tracker.infrastructure.model.{TicketModel, TicketRow, Version}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

trait TicketDao[V <: Version] {
  def find(id: String): Future[Option[TicketRow]]

  def create(id: String,
             name: String,
             descriptionOpt: Option[String],
             status: String): Future[Unit]

  def updateStatus(id: String, status: String, commentOpt: Option[String]): Future[Unit]
}

class TicketDaoImpl[V <: Version : ClassTag, BackEnd <: JdbcBackend](model: TicketModel,
                                                                     db: BackEnd#Database)
                                                                    (implicit executionContext: ExecutionContext) extends TicketDao[V] {

  import model._
  import model.api._

  override def find(id: String): Future[Option[TicketRow]] =
    db.run {
      findTicket(id)
        .result
        .headOption
    }

  override def create(id: String, name: String, descriptionOpt: Option[String], status: String): Future[Unit] =
    db.run {
      tickets += TicketRow(id, name, descriptionOpt, status, comment = None)
    }.map(_ => ())

  override def updateStatus(id: String, status: String, commentOpt: Option[String]): Future[Unit] =
    db.run {
      findTicket(id)
        .map(ticket => (ticket.status, ticket.comment))
        .update((status, commentOpt))
    }.map(_ => ())

  private def findTicket(id: String) = tickets.filter(_.id === id.bind)
}
