package monadsl.example.domain

import com.softwaremill.macwire.wire
import monadsl.example.domain.services.{TicketFactory, TicketFactoryImpl, TicketRepository, TicketRepositoryImpl, TicketService, TicketServiceImpl}
import monadsl.example.infrastructure.InfrastructureLayer

trait DomainLayer extends InfrastructureLayer {
  lazy val ticketFactory: TicketFactory = wire[TicketFactoryImpl]
  lazy val ticketRepository: TicketRepository = wire[TicketRepositoryImpl]
  lazy val ticketService: TicketService = wire[TicketServiceImpl]
}
