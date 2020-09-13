package monadsl.example.infrastructure

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContext

trait InfrastructureLayer {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  lazy val configuration: Config = ConfigFactory.load()
}
