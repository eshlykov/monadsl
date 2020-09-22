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

object Tracker extends Dependencies {
  override val dependencies: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.github.swagger-akka-http" %% "swagger-scala-module" % swaggerScalaModuleVersion,
    "com.softwaremill.common" %% "id-generator" % idGeneratorVersion,
    "com.softwaremill.macwire" %% "macros" % macrosVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaActorVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
    "com.typesafe.play" %% "play-json" % playJsonVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickHikaricpVersion,
    "de.heikoseeberger" %% "akka-http-play-json" % akkaHttpPlayJsonVersion,
    "io.swagger.core.v3" % "swagger-core" % swaggerCoreVersion,
    "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerJaxrs2version,
    "javax.ws.rs" % "jsr311-api" % jsr311apiVersion,
    "org.liquibase" % "liquibase-core" % liquibaseCoreVersion,
    "org.postgresql" % "postgresql" % postgresqlVersion,
    "org.typelevel" %% "cats-core" % catsCoreVersion,
    "org.webjars" % "swagger-ui" % swaggerUiVersion,
    "org.webjars" % "webjars-locator" % webjarsLocatorVersion
  )
}
