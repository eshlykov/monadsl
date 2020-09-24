package tracker.domain.services

import cats.data.OptionT
import cats.instances.future._
import tracker.domain.entities
import tracker.domain.entities.Ticket
import tracker.domain.values.TicketNotFoundException
import tracker.infrastructure.model.Version
import tracker.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketRepository[V <: Version] {
  def get(ticketId: String): Future[Ticket]
}

class TicketRepositoryImpl[V <: Version](ticketDao: TicketDao[V])
                                        (implicit executionContext: ExecutionContext) extends TicketRepository[V] {
  override def get(ticketId: String): Future[Ticket] =
    OptionT(ticketDao.find(ticketId))
      .map(entities.Ticket(_))
      .getOrElse(throw new TicketNotFoundException)
}
