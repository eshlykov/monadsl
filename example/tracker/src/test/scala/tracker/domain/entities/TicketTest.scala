package tracker.domain.entities

import tracker.domain.values.{InvalidTicketStatusException, TicketStatuses}
import tracker.infrastructure.model.TicketRow
import util.TestBase

class TicketTest extends TestBase {
  "apply" should "transform database row into domain object" in {
    Ticket.apply(ticketRow) shouldBe ticket
  }

  it should "fail with InvalidTicketStatusException if database status is invalid" in {
    val corruptedTicketRow = ticketRow.copy(status = "status")

    Ticket.apply(corruptedTicketRow).shouldFailWith[InvalidTicketStatusException]
  }

  private lazy val ticketRow = TicketRow(
    id = "ticketId",
    name = "task",
    description = Some("description"),
    status = "Specification",
    comment = Some("comment"),
  )

  private lazy val ticket = Ticket(
    id = "ticketId",
    name = "task",
    descriptionOpt = Some("description"),
    status = TicketStatuses.specification,
    commentOpt = Some("comment"),
  )
}
