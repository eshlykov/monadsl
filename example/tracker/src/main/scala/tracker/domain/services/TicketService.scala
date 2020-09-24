package tracker.domain.services

import cats.instances.future._
import monadsl._
import tracker.domain.values.TicketStages.TicketStage
import tracker.domain.values.{InvalidTicketStageException, TicketStages}
import tracker.infrastructure.model.{V1, V2, Version}
import tracker.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketService[V <: Version] {
  def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit]

  def returnToPreviousStage(ticketId: String, stage: TicketStage, comment: String): Future[Unit]
}

class TicketServiceImplV1(ticketDao: TicketDao[V1],
                          ticketRepository: TicketRepository[V1])
                         (implicit executionContext: ExecutionContext) extends TicketService[V1] {
  override def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit] =
    for {
      ticket <- ticketRepository.get(ticketId)
      stage = ticket.stage
      _ = if (stage == TicketStages.released || stage == TicketStages.trashed) {
        throw new InvalidTicketStageException
      }
      nextStage = TicketStages(stage.id + 1)
      _ <- ticketDao.updateStage(
        id = ticketId,
        stage = nextStage.toString,
        commentOpt = commentOpt.orElse(ticket.commentOpt)
      )
    } yield ()

  override def returnToPreviousStage(ticketId: String, stage: TicketStage, comment: String): Future[Unit] =
    for {
      ticket <- ticketRepository.get(ticketId)
      oldStage = ticket.stage
      _ = if (stage.id >= oldStage.id || stage == TicketStages.trashed) {
        throw new InvalidTicketStageException
      }
      _ = if (oldStage == TicketStages.released) {
        throw new InvalidTicketStageException
      }
      _ <- ticketDao.updateStage(
        id = ticketId,
        stage = stage.toString,
        commentOpt = Some(comment)
      )
    } yield ()
}

class TicketServiceImplV2(ticketDao: TicketDao[V2],
                          ticketRepository: TicketRepository[V2])
                         (implicit executionContext: ExecutionContext) extends TicketService[V2] {
  override def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit] = {
    val ticket = ticketRepository.get(ticketId)
    val stage = ticket.map(_.stage)
    when((stage is TicketStages.released) or (stage is TicketStages.trashed)) {
      Future.failed(new InvalidTicketStageException)
    } otherwise {
      for {
        nextStage <- stage.map(stage => TicketStages(stage.id + 1))
        currentCommentOpt <- ticket.map(_.commentOpt)
        _ <- ticketDao.updateStage(
          id = ticketId,
          stage = nextStage.toString,
          commentOpt = commentOpt.orElse(currentCommentOpt)
        )
      } yield ()
    }
  }

  override def returnToPreviousStage(ticketId: String, stage: TicketStage, comment: String): Future[Unit] = {
    val ticket = ticketRepository.get(ticketId)
    val oldStage = ticket.map(_.stage)
    when((oldStage is TicketStages.released) or (oldStage is TicketStages.trashed) or (oldStage is TicketStages.specification) or (oldStage.map(_.id <= stage.id))) {
      Future.failed[Unit](new InvalidTicketStageException)
    } otherwise {
      ticketDao.updateStage(
        id = ticketId,
        stage = stage.toString,
        commentOpt = Some(comment)
      )
    }
  }
}
