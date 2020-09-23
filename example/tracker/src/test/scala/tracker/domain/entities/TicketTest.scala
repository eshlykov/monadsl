package tracker.domain.entities

import tracker.domain.values.{InvalidTicketStageException, TicketStages}
import tracker.infrastructure.model.TicketRow
import util.TestBase

class TicketTest extends TestBase {
  "apply" should "transform database row into domain object" in {
    Ticket.apply(ticketRow) shouldBe ticket
  }

  it should "fail with InvalidTicketStageException if database stage is invalid" in {
    val corruptedTicketRow = ticketRow.copy(stage = "stage")

    Ticket.apply(corruptedTicketRow).shouldFailWith[InvalidTicketStageException]
  }

  private lazy val ticketRow = TicketRow(
    id = "ticketId",
    name = "task",
    description = Some("description"),
    stage = "Specification",
    comment = Some("comment"),
  )

  private lazy val ticket = Ticket(
    id = "ticketId",
    name = "task",
    descriptionOpt = Some("description"),
    stage = TicketStages.specification,
    commentOpt = Some("comment"),
  )
}
