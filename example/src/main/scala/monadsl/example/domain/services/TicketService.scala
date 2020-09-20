package monadsl.example.domain.services

import monadsl.example.domain.values.{InvalidTicketStatusException, TicketStatuses}
import monadsl.example.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketService {
  def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit]
}

class TicketServiceImpl(ticketDao: TicketDao,
                        ticketRepository: TicketRepository)
                       (implicit executionContext: ExecutionContext) extends TicketService {
  override def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit] =
    for {
      ticket <- ticketRepository.get(ticketId)
      status = ticket.status
      _ = if (status == TicketStatuses.released || status == TicketStatuses.trashed) {
        throw InvalidTicketStatusException
      }
      nextStatus = TicketStatuses(status.id + 1)
      _ <- ticketDao.updateStatus(
        id = ticket.id,
        status = nextStatus.toString,
        commentOpt = commentOpt.orElse(ticket.commentOpt)
      )
    } yield ()
}
