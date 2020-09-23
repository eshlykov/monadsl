package tracker.domain.values

object TicketStages extends Enumeration {
  type TicketStage = Value

  val trashed: TicketStage = Value("Trashed")
  val specification: TicketStage = Value("Specification")
  val architecture: TicketStage = Value("Architecture")
  val development: TicketStage = Value("Development")
  val review: TicketStage = Value("Review")
  val testing: TicketStage = Value("Testing")
  val ready: TicketStage = Value("Ready")
  val released: TicketStage = Value("Released")
}
