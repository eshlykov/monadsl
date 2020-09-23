import DockerProject._
import scoverage.ScoverageKeys.coverageExcludedPackages

name := "monadsl"

version := "0.1"

scalaVersion := "2.12.12"

lazy val monadsl = (project in file("."))
  .settings(
    libraryDependencies ++= Monadsl.dependencies
  )

lazy val tracker = (project in file("example/tracker"))
  .settings(
    libraryDependencies ++= Tracker.dependencies,
    javaOptions += "-Dconfig.resource/application.conf",
    coverageExcludedPackages := ".*Layer;.*Tracker;util\\..*"
  )
  .enableDockerPlugin
  .dependsOn(monadsl)
