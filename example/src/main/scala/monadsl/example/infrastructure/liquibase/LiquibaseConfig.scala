package monadsl.example.infrastructure.liquibase

import com.typesafe.config.Config

case class LiquibaseConfig(url: String,
                           user: String,
                           password: String,
                           changeLogFile: String)

object LiquibaseConfig {
  def apply(config: Config): LiquibaseConfig = {
    val liquibaseConfig = config.getConfig("liquibase")
    LiquibaseConfig(
      url = liquibaseConfig.getString("url"),
      user = liquibaseConfig.getString("user"),
      password = liquibaseConfig.getString("password"),
      changeLogFile = liquibaseConfig.getString("changeLogFile"),
    )
  }
}
