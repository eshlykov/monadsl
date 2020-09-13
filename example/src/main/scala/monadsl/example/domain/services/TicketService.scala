package monadsl.example.domain.services

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

trait TicketService {
  def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit]
}

class TicketServiceImpl(implicit executionContext: ExecutionContext) extends TicketService with LazyLogging {
  override def passCurrentStage(ticketId: String, commentOpt: Option[String]): Future[Unit] = ???
}
