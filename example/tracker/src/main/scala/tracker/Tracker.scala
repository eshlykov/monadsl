package tracker

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.LazyLogging
import tracker.presentation.PresentationLayer

object Tracker extends App with PresentationLayer with LazyLogging {
  val _ = Http()
    .newServerAt(interface = "localhost", port = 8080)
    .bind(route)

  liquibaseService.initDatabase()

  logger.info(s"Server online at http://localhost:8080/")
}
