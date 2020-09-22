package tracker.presentation.http.model

import akka.http.scaladsl.model.{HttpCharsets, MediaType}

object CustomContentTypes {
  val `application/yaml`: MediaType.WithFixedCharset = MediaType.customWithFixedCharset(
    mainType = "application",
    subType = "yaml",
    charset = HttpCharsets.`UTF-8`,
  )
}
