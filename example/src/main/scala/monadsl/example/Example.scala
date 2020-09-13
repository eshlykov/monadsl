package monadsl.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.softwaremill.macwire.wire
import monadsl.example.application.route.TicketRoute
import monadsl.example.domain.services.{TicketFactory, TicketFactoryImpl, TicketRepository, TicketRepositoryImpl, TicketService, TicketServiceImpl}

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Example extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val ticketFactory: TicketFactory = wire[TicketFactoryImpl]
  val ticketRepository: TicketRepository = wire[TicketRepositoryImpl]
  val ticketService: TicketService = wire[TicketServiceImpl]

  val ticketRoute: TicketRoute = wire[TicketRoute]

  val bindingFuture = Http()
    .newServerAt(interface = "localhost", port = 8080)
    .bind(ticketRoute.route)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
