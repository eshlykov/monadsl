import Versions._

name := "monadsl"

version := "0.1"

scalaVersion := "2.13.3"

lazy val monadsl = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % catsCoreVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    )
  )

lazy val example = (project in file("example"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaActorVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "com.typesafe.play" %% "play-json" % playJsonVersion,
      "de.heikoseeberger" %% "akka-http-play-json" % akkaHttpPlayJsonVersion,
      "com.softwaremill.macwire" %% "macros" % macrosVersion % "provided",
    )
  )
