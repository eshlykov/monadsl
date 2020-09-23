package tracker.domain.services

import com.softwaremill.macwire.wire
import tracker.domain.entities.Ticket
import tracker.domain.values.{InvalidTicketStatusException, TicketStatuses}
import tracker.infrastructure.model.V1
import util.TestBase

class TicketServiceImplV1Test extends TestBase {
  "passCurrentStage" should "change status to the next one and update comment" in new Wiring {
    (mockTicketRepositoryV1.get _)
      .expects(id)
      .returnsAsync(ticket)

    (mockTicketDaoV1.updateStatus _)
      .expects(id, TicketStatuses.review.toString, Some(newComment))
      .returnsUnitAsync

    service.passCurrentStage(id, Some(newComment)).shouldSucceed
  }

  it should "do no change comment if new comment does not pass" in new Wiring {
    (mockTicketRepositoryV1.get _)
      .expects(id)
      .returnsAsync(ticket)

    (mockTicketDaoV1.updateStatus _)
      .expects(id, TicketStatuses.review.toString, Some(oldComment))
      .returnsUnitAsync

    service.passCurrentStage(id, None).shouldSucceed
  }

  it should "do not touch comment if ticket has no comment and new comment does not pass" in new Wiring {
    val ticketWithoutComment: Ticket = ticket.copy(commentOpt = None)

    (mockTicketRepositoryV1.get _)
      .expects(id)
      .returnsAsync(ticketWithoutComment)

    (mockTicketDaoV1.updateStatus _)
      .expects(id, TicketStatuses.review.toString, None)
      .returnsUnitAsync

    service.passCurrentStage(id, None).shouldSucceed
  }

  it should "fail with InvalidTicketStatusException if ticket is in Trashed stage" in new Wiring {
    val trashedTicket: Ticket = ticket.copy(status = TicketStatuses.trashed)

    (mockTicketRepositoryV1.get _)
      .expects(id)
      .returnsAsync(trashedTicket)

    service.passCurrentStage(id, Some(newComment)).shouldFailWith[InvalidTicketStatusException]
  }

  it should "fail with InvalidTicketStatusException if ticket is in Released stage" in new Wiring {
    val releasedTicket: Ticket = ticket.copy(status = TicketStatuses.released)

    (mockTicketRepositoryV1.get _)
      .expects(id)
      .returnsAsync(releasedTicket)

    service.passCurrentStage(id, Some(newComment)).shouldFailWith[InvalidTicketStatusException]
  }

  private trait Wiring extends MockWiring {
    lazy val service: TicketService[V1] = wire[TicketServiceImplV1]
  }

  private lazy val id = "id"
  private lazy val oldComment = "oldComment"
  private lazy val newComment = "newComment"

  private lazy val ticket = Ticket(
    id = id,
    name = "name",
    descriptionOpt = None,
    status = TicketStatuses.development,
    commentOpt = Some(oldComment)
  )
}
