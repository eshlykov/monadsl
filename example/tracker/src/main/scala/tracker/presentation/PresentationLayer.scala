package tracker.presentation

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import io.swagger.v3.core.util.Yaml
import io.swagger.v3.jaxrs2.Reader
import io.swagger.v3.oas.integration.SwaggerConfiguration
import tracker.application.ApplicationLayer
import tracker.presentation.http.route.SwaggerUiRoute

import scala.collection.JavaConverters._

trait PresentationLayer extends ApplicationLayer {
  lazy val route: Route = RouteConcatenation.concat(applicationRoutes: _*) ~ uiRoute

  private lazy val openApi = {
    val reader = new Reader(new SwaggerConfiguration)
    reader.read(applicationRoutes.map(_.getClass).toSet[Class[_]].asJava)
  }

  private lazy val uiRoute = new SwaggerUiRoute(Yaml.pretty(openApi))
}
