import Versions._
import sbt.librarymanagement.ModuleID
import sbt.librarymanagement.syntax._

trait Dependencies {
  def dependencies: Seq[ModuleID]
}

object Monadsl extends Dependencies {
  override val dependencies: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core" % catsCoreVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  )
}

object Example extends Dependencies {
  override val dependencies: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaActorVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.typesafe.play" %% "play-json" % playJsonVersion,
    "de.heikoseeberger" %% "akka-http-play-json" % akkaHttpPlayJsonVersion,
    "io.swagger.core.v3" % "swagger-core" % swaggerCoreVersion,
    "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerJaxrs2Version,
    "javax.ws.rs" % "jsr311-api" % jsr311apiVersion,
    "org.webjars" % "webjars-locator" % webjarsLocatorVersion,
    "org.webjars" % "swagger-ui" % swaggerUiVersion,
    "com.softwaremill.macwire" %% "macros" % macrosVersion,
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.softwaremill.common" %% "id-generator" % idGeneratorVersion,
    "org.typelevel" %% "cats-core" % catsCoreVersion
  )
}
