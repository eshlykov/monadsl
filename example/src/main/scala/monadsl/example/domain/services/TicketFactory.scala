package monadsl.example.domain.services

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

trait TicketFactory {
  def create(name: String, description: String): Future[Unit]
}

class TicketFactoryImpl(implicit executionContext: ExecutionContext) extends TicketFactory with LazyLogging {
  override def create(name: String, description: String): Future[Unit] = ???
}
