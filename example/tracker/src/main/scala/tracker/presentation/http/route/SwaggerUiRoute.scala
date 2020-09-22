package tracker.presentation.http.route

import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import org.webjars.WebJarAssetLocator
import tracker.presentation.http.model.CustomContentTypes

import scala.concurrent.Future
import scala.io.Source

class SwaggerUiRoute(docYaml: String) extends Route {
  override def apply(v1: RequestContext): Future[RouteResult] =
    pathPrefix("swagger") {
      pathEnd {
        getSwaggerUiIndexHtml
      } ~ path("doc.yaml") {
        getOpenApiDocumentYaml
      } ~ extractUnmatchedPath { path =>
        getResourceByLocation(path)
      }
    }.apply(v1)

  private lazy val indexHtml = Source.fromResource("index.html").getLines().mkString("\n")

  private lazy val indexHtmlResponse = HttpEntity(ContentTypes.`text/html(UTF-8)`, indexHtml)
  private lazy val docYamlResponse = HttpEntity(CustomContentTypes.`application/yaml`, docYaml)

  private val webJarAssetLocator = new WebJarAssetLocator

  private def getSwaggerUiIndexHtml: Route =
    get {
      complete(indexHtmlResponse)
    }

  private def getOpenApiDocumentYaml: Route =
    get {
      complete(docYamlResponse)
    }

  private def getResourceByLocation(path: Path): Route =
    getFromResource {
      webJarAssetLocator.getFullPath(path.toString)
    }
}
