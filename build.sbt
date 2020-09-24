import DockerProject._
import scoverage.ScoverageKeys.{coverageEnabled, coverageExcludedPackages}

name := "monadsl"

version := "0.1"

scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .aggregate(monadsl, tracker)

lazy val monadsl = (project in file("monadsl"))
  .settings(
    libraryDependencies ++= Monadsl.dependencies,
    coverageEnabled in Test := true,
  )

lazy val tracker = (project in file("example/tracker"))
  .settings(
    libraryDependencies ++= Tracker.dependencies,
    javaOptions += "-Dconfig.resource/application.conf",
    coverageEnabled in Test := true,
    coverageExcludedPackages := ".*Layer;.*Tracker;util\\..*",
  )
  .enableDockerPlugin
  .dependsOn(monadsl)
