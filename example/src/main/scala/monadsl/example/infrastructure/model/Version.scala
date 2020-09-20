package monadsl.example.infrastructure.model

import scala.reflect.{ClassTag, classTag}

sealed trait Version

final class V1 extends Version

final class V2 extends Version
