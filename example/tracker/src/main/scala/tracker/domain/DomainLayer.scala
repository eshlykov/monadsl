package tracker.domain

import com.softwaremill.macwire.wire
import tracker.domain.services.{TicketFactory, TicketFactoryImpl, TicketRepository, TicketRepositoryImpl, TicketService, TicketServiceImplV1, TicketServiceImplV2}
import tracker.infrastructure.InfrastructureLayer
import tracker.infrastructure.model.{V1, V2}

trait DomainLayer extends InfrastructureLayer {
  lazy val ticketV1factory: TicketFactory[V1] = wire[TicketFactoryImpl[V1]]
  lazy val ticketV2factory: TicketFactory[V2] = wire[TicketFactoryImpl[V2]]
  lazy val ticketV1repository: TicketRepository[V1] = wire[TicketRepositoryImpl[V1]]
  lazy val ticketV2repository: TicketRepository[V2] = wire[TicketRepositoryImpl[V2]]
  lazy val ticketV1service: TicketService[V1] = wire[TicketServiceImplV1]
  lazy val ticketV2service: TicketService[V2] = wire[TicketServiceImplV2]
}
