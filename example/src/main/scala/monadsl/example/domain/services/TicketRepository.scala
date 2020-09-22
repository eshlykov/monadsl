package monadsl.example.domain.services

import cats.data.OptionT
import cats.instances.future._
import monadsl.example.domain.entities.Ticket
import monadsl.example.domain.values.TicketNotFoundException
import monadsl.example.infrastructure.model.{V1, Version}
import monadsl.example.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketRepository[V <: Version] {
  def get(ticketId: String): Future[Ticket]
}

class TicketRepositoryImpl[V <: Version](ticketDao: TicketDao[V])
                          (implicit executionContext: ExecutionContext) extends TicketRepository[V] {
  override def get(ticketId: String): Future[Ticket] =
    OptionT(ticketDao.find(ticketId))
      .map(Ticket(_))
      .getOrElse(throw new TicketNotFoundException)
}
