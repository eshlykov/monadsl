package tracker.infrastructure.liquibase

import java.sql.DriverManager

import liquibase.database.Database
import liquibase.database.core.PostgresDatabase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.{Contexts, Liquibase}

trait LiquibaseService {
  def initDatabase(): Unit
}

class LiquibaseServiceImpl(liquibaseConfig: LiquibaseConfig) extends LiquibaseService {

  def initDatabase(): Unit = liquibase.update(new Contexts())

  private lazy val liquibase: Liquibase = new Liquibase(
    liquibaseConfig.changeLogFile,
    new ClassLoaderResourceAccessor(),
    database
  )

  private lazy val database: Database = {
    val database = new PostgresDatabase()
    database.setConnection(new JdbcConnection(connection))
    database
  }

  private lazy val connection = DriverManager.getConnection(
    liquibaseConfig.url,
    liquibaseConfig.user,
    liquibaseConfig.password
  )
}
