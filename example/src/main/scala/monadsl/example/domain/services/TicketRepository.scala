package monadsl.example.domain.services

import com.typesafe.scalalogging.LazyLogging
import monadsl.example.domain.entities.Ticket

import scala.concurrent.{ExecutionContext, Future}

trait TicketRepository {
  def get(ticketId: String): Future[Ticket]
}

class TicketRepositoryImpl(implicit executionContext: ExecutionContext) extends TicketRepository with LazyLogging {
  override def get(ticketId: String): Future[Ticket] = ???
}
