package monadsl.example.domain.services

import com.softwaremill.id.pretty.PrettyIdGenerator
import monadsl.example.domain.values.TicketStatuses
import monadsl.example.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketFactory {
  def create(name: String, descriptionOpt: Option[String]): Future[String]
}

class TicketFactoryImpl(ticketDao: TicketDao)
                       (implicit executionContext: ExecutionContext) extends TicketFactory {
  override def create(name: String, descriptionOpt: Option[String]): Future[String] = {
    val id = idGenerator.nextId()
    ticketDao.create(
      id = id,
      name = name,
      descriptionOpt = descriptionOpt,
      status = TicketStatuses.specification.toString
    ).map(_ => id)
  }

  private val idGenerator: PrettyIdGenerator = PrettyIdGenerator.singleNode
}
