package util.database.model

import slick.jdbc.JdbcProfile

trait Model {
  val databaseProfile: JdbcProfile

  final lazy val api: databaseProfile.API = databaseProfile.api
}
