package monadsl.example.infrastructure

import akka.actor.ActorSystem
import com.softwaremill.macwire.wire
import com.typesafe.config.ConfigFactory
import monadsl.example.infrastructure.liquibase.{LiquibaseConfig, LiquibaseService, LiquibaseServiceImpl}
import monadsl.example.infrastructure.model.TicketModel
import monadsl.example.infrastructure.persistence.{TicketDao, TicketDaoImpl}
import slick.jdbc.{JdbcProfile, PostgresProfile}

import scala.concurrent.ExecutionContext

trait InfrastructureLayer {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  lazy val liquibaseService: LiquibaseService = wire[LiquibaseServiceImpl]

  lazy val ticketDao: TicketDao = wire[TicketDaoImpl[BackEnd]]

  private type BackEnd = PostgresProfile#Backend

  private trait DatabaseModel extends TicketModel

  private lazy val model: DatabaseModel = new DatabaseModel {
    override val databaseProfile: JdbcProfile = PostgresProfile
  }

  private lazy val database: BackEnd#Database = model.api.Database.forConfig(path = "tickets")

  private val config= ConfigFactory.load()
  private val liquibaseConfig = LiquibaseConfig(config)
}
