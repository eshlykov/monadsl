package monadsl.example.domain.services

import cats.data.OptionT
import cats.instances.future._
import monadsl.example.domain.entities.Ticket
import monadsl.example.domain.values.TicketNotFoundException
import monadsl.example.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketRepository {
  def get(ticketId: String): Future[Ticket]
}

class TicketRepositoryImpl(ticketDao: TicketDao)
                          (implicit executionContext: ExecutionContext) extends TicketRepository {
  override def get(ticketId: String): Future[Ticket] =
    OptionT(ticketDao.find(ticketId))
      .map(Ticket(_))
      .getOrElse(throw TicketNotFoundException)
}
