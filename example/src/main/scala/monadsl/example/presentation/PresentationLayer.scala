package monadsl.example.presentation

import akka.http.scaladsl.server.{Route, RouteConcatenation}
import akka.http.scaladsl.server.Directives._
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.swagger.v3.core.util.Yaml
import io.swagger.v3.jaxrs2.Reader
import io.swagger.v3.oas.integration.SwaggerConfiguration
import monadsl.example.application.ApplicationLayer
import monadsl.example.presentation.http.route.SwaggerUiRoute

import scala.collection.JavaConverters._

trait PresentationLayer extends ApplicationLayer {
  lazy val route: Route = RouteConcatenation.concat(applicationRoutes: _*) ~ uiRoute

  private lazy val openApi = {
    val reader = new Reader(new SwaggerConfiguration)
    reader.read(applicationRoutes.map(_.getClass).toSet[Class[_]].asJava)
  }

  private lazy val uiRoute = {
    val _ = Yaml.mapper().getFactory.asInstanceOf[YAMLFactory]
    new SwaggerUiRoute(Yaml.pretty(openApi))
  }
}
