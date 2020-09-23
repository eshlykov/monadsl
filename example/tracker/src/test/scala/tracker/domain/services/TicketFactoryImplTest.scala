package tracker.domain.services

import com.softwaremill.macwire.wire
import tracker.infrastructure.model.V1
import util.TestBase

import scala.concurrent.Future

class TicketFactoryImplTest extends TestBase {
  "create" should "create new ticket using persistence layer" in new Wiring {
    (mockIdGeneratorService.nextId _)
      .expects()
      .returns(id)

    (mockTicketDaoV1.create _)
      .expects(id, name, Some(description), "Specification")
      .returns(Future.successful(()))

    factory.create(name, Some(description)).shouldReturn(id)
  }

  private trait Wiring extends MockWiring {
    lazy val factory: TicketFactory[V1] = wire[TicketFactoryImpl[V1]]
  }

  private lazy val id = "id"
  private lazy val name = "name"
  private lazy val description = "description"
}
