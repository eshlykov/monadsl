import DockerProject._

name := "monadsl"

version := "0.1"

scalaVersion := "2.13.3"

lazy val monadsl = (project in file("."))
  .settings {
    libraryDependencies ++= Monadsl.dependencies
  }

lazy val example = (project in file("example"))
  .settings {
    libraryDependencies ++= Example.dependencies
  }
  .enableDockerPlugin
