package tracker.domain.services

import tracker.domain.values.TicketStatuses
import tracker.infrastructure.model.Version
import tracker.infrastructure.persistence.TicketDao
import util.helpers.IdGeneratorService

import scala.concurrent.{ExecutionContext, Future}

trait TicketFactory[V <: Version] {
  def create(name: String, descriptionOpt: Option[String]): Future[String]
}

class TicketFactoryImpl[V <: Version](ticketDao: TicketDao[V],
                                     idGeneratorService: IdGeneratorService)
                                     (implicit executionContext: ExecutionContext) extends TicketFactory[V] {
  override def create(name: String, descriptionOpt: Option[String]): Future[String] = {
    val id = idGeneratorService.nextId()
    ticketDao.create(
      id = id,
      name = name,
      descriptionOpt = descriptionOpt,
      status = TicketStatuses.specification.toString
    ).map(_ => id)
  }
}
