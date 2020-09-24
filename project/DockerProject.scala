import com.typesafe.sbt.packager.Keys.{executableScriptName, stage}
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt.Keys.name
import sbt.Project
import sbtdocker.DockerKeys.{docker, dockerfile}
import sbtdocker.{DockerPlugin, Dockerfile}

object DockerProject {

  implicit class ProjectOps(val project: Project) extends AnyVal {
    def enableDockerPlugin: Project = project
      .enablePlugins(DockerPlugin, JavaAppPackaging)
      .settings {
        dockerfile in docker := new Dockerfile {
          from(baseImage)
          expose(ports = 8080)
          copy(appDir, targetDir)
          workDir(targetDir)
          entryPoint(args = s"bin/${executableScriptName.value}")

          private lazy val appDir = stage.value
          private lazy val targetDir = s"/opt/${name.value}"
        }
      }
  }

  private val baseImage = "openjdk:11-jre"
}
