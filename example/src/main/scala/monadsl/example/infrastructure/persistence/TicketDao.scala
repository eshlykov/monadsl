package monadsl.example.infrastructure.persistence

import monadsl.example.infrastructure.model.{TicketModel, TicketRow}
import slick.jdbc.JdbcBackend

import scala.concurrent.{ExecutionContext, Future}

trait TicketDao {
  def find(id: String): Future[Option[TicketRow]]

  def create(id: String,
             name: String,
             descriptionOpt: Option[String],
             status: String): Future[Unit]

  def updateStatus(id: String, status: String, commentOpt: Option[String]): Future[Unit]
}

class TicketDaoImpl[BackEnd <: JdbcBackend](model: TicketModel, db: BackEnd#Database)
                                           (implicit executionContext: ExecutionContext) extends TicketDao {

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
