package tracker.domain.values

object TicketStatuses extends Enumeration {
  type TicketStatus = Value

  val trashed: TicketStatus = Value("Trashed")
  val specification: TicketStatus = Value("Specification")
  val architecture: TicketStatus = Value("Architecture")
  val development: TicketStatus = Value("Development")
  val review: TicketStatus = Value("Review")
  val testing: TicketStatus = Value("Testing")
  val ready: TicketStatus = Value("Ready")
  val released: TicketStatus = Value("Released")
}
