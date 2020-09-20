package monadsl.example.domain.services

import monadsl.example.domain.values.{InvalidTicketStatusException, TicketStatuses}
import monadsl.example.infrastructure.model.{V1, V2, Version}
import monadsl.example.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketService[V <: Version] {
  def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit]
}

class TicketServiceImplV1(ticketDao: TicketDao[V1],
                          ticketRepository: TicketRepository[V1])
                         (implicit executionContext: ExecutionContext) extends TicketService[V1] {
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

class TicketServiceImplV2(ticketDao: TicketDao[V2],
                          ticketRepository: TicketRepository[V2])
                         (implicit executionContext: ExecutionContext) extends TicketService[V2] {
  override def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit] = ???
}
