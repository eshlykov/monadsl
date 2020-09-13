package monadsl.example

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.LazyLogging
import monadsl.example.presentation.PresentationLayer

import scala.io.StdIn

object Example extends App with PresentationLayer with LazyLogging {
  val bindingFuture = Http()
    .newServerAt(interface = "localhost", port = 8080)
    .bind(route)

  logger.info(s"Server online at http://localhost:8080/. Press RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
