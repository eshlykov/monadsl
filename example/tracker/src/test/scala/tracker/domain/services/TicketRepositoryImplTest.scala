package tracker.domain.services

import com.softwaremill.macwire.wire
import tracker.domain.entities.Ticket
import tracker.domain.values.{TicketNotFoundException, TicketStatuses}
import tracker.infrastructure.model.{TicketRow, V1}
import util.TestBase

class TicketRepositoryImplTest extends TestBase {
  "get" should "return ticket from database if it exists" in new Wiring {
    (mockTicketDaoV1.find _)
      .expects(id)
      .returnsSomeAsync(ticketRow)

    repository.get(id).shouldReturn(ticket)
  }

  "it" should "fail with TicketNotFoundException if requested ticket does not exist" in new Wiring {
    (mockTicketDaoV1.find _)
      .expects(id)
      .returnsNoneAsync

    repository.get(id).shouldFailWith[TicketNotFoundException]
  }

  private trait Wiring extends MockWiring {
    lazy val repository: TicketRepository[V1] = wire[TicketRepositoryImpl[V1]]
  }

  private lazy val id = "id"
  private lazy val name = "name"
  private lazy val status = TicketStatuses.development

  private lazy val ticketRow = TicketRow(
    id = id,
    name = name,
    description = None,
    status = status.toString,
    comment = None,
  )
  private lazy val ticket = Ticket(
    id = id,
    name = name,
    descriptionOpt = None,
    status = status,
    commentOpt = None,
  )

}
