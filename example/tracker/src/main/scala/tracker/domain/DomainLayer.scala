package tracker.domain

import com.softwaremill.macwire.wire
import tracker.domain.services.{TicketFactory, TicketFactoryImpl, TicketRepository, TicketRepositoryImpl, TicketService, TicketServiceImplV1, TicketServiceImplV2}
import tracker.infrastructure.InfrastructureLayer
import tracker.infrastructure.model.{V1, V2}

trait DomainLayer extends InfrastructureLayer {
  lazy val ticketFactoryV1: TicketFactory[V1] = wire[TicketFactoryImpl[V1]]
  lazy val ticketFactoryV2: TicketFactory[V2] = wire[TicketFactoryImpl[V2]]
  lazy val ticketRepositoryV1: TicketRepository[V1] = wire[TicketRepositoryImpl[V1]]
  lazy val ticketRepositoryV2: TicketRepository[V2] = wire[TicketRepositoryImpl[V2]]
  lazy val ticketServiceV1: TicketService[V1] = wire[TicketServiceImplV1]
  lazy val ticketServiceV2: TicketService[V2] = wire[TicketServiceImplV2]
}
