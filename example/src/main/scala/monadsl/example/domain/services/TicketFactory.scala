package monadsl.example.domain.services

import com.softwaremill.id.pretty.PrettyIdGenerator
import monadsl.example.domain.values.TicketStatuses
import monadsl.example.infrastructure.model.{V1, Version}
import monadsl.example.infrastructure.persistence.TicketDao

import scala.concurrent.{ExecutionContext, Future}

trait TicketFactory[V <: Version] {
  def create(name: String, descriptionOpt: Option[String]): Future[String]
}

class TicketFactoryImpl[V <: Version](ticketDao: TicketDao[V])
                                     (implicit executionContext: ExecutionContext) extends TicketFactory[V] {
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
