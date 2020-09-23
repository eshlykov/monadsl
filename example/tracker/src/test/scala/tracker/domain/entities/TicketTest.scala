package tracker.domain.entities

import akka.actor.FSM.Failure
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import tracker.domain.values.{InvalidTicketStatusException, TicketStatuses}
import tracker.infrastructure.model.TicketRow

import scala.util.Try


class TicketTest extends AnyFlatSpec with Matchers {
  "apply" should "transform database row into domain object" in {
    Ticket.apply(ticketRow) shouldBe ticket
  }

  it should "fail with InvalidTicketStatusException if database status is invalid" in {
    val corruptedTicketRow = ticketRow.copy(status = "status")

    Try(Ticket.apply(corruptedTicketRow)).failed.get shouldBe an[InvalidTicketStatusException]
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
