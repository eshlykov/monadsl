package tracker.application.http.protocol

import tracker.domain.entities.Ticket
import tracker.domain.values.TicketStages
import util.TestBase

class TicketDtoTest extends TestBase {
  "apply" should "transform ticket to data transfer object" in {
    TicketDto.apply(ticket) shouldBe ticketDto
  }

  private lazy val id = "id"
  private lazy val name = "name"
  private lazy val description = "description"
  private lazy val stage = TicketStages.released
  private lazy val comment = "comment"

  private lazy val ticket = Ticket(id, name, Some(description), stage, Some(comment))
  private lazy val ticketDto = TicketDto(id, name, Some(description), stage, Some(comment))
}
