package util.mock

import akka.actor.ActorSystem
import org.scalamock.scalatest.MockFactory
import tracker.domain.services.{TicketFactory, TicketRepository, TicketService}
import tracker.infrastructure.model.{V1, V2, Version}
import tracker.infrastructure.persistence.TicketDao
import util.helpers.IdGeneratorService

import scala.concurrent.ExecutionContext

trait MockWiringFactory extends MockFactory {

  trait MockWiring {
    implicit lazy val executionContext: ExecutionContext = system.dispatcher

    lazy val mockIdGeneratorService: IdGeneratorService = mock[IdGeneratorService]

    lazy val mockTicketDaoV1: TicketDao[V1] = mock[TicketDao[V1]]
    lazy val mockTicketDaoV2: TicketDao[V2] = mock[TicketDao[V2]]

    lazy val mockTicketFactoryV1: TicketFactory[V1] = mock[TicketFactory[V1]]
    lazy val mockTicketFactoryV2: TicketFactory[V2] = mock[TicketFactory[V2]]
    lazy val mockTicketRepositoryV1: TicketRepository[V1] = mock[TicketRepository[V1]]
    lazy val mockTicketRepositoryV2: TicketRepository[V2] = mock[TicketRepository[V2]]
    lazy val mockTicketServiceV1: TicketService[V1] = mock[TicketService[V1]]
    lazy val mockTicketServiceV2: TicketService[V2] = mock[TicketService[V2]]
  }

  private lazy val system: ActorSystem = ActorSystem()
}
