package monadsl.example.infrastructure

import akka.actor.ActorSystem
import com.softwaremill.macwire.wire
import com.typesafe.config.ConfigFactory
import monadsl.example.infrastructure.liquibase.{LiquibaseConfig, LiquibaseService, LiquibaseServiceImpl}
import monadsl.example.infrastructure.model.{TicketModel, V1, V2}
import monadsl.example.infrastructure.persistence.{TicketDao, TicketDaoImpl}
import slick.jdbc.{JdbcProfile, PostgresProfile}

import scala.concurrent.ExecutionContext

trait InfrastructureLayer {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  lazy val liquibaseService: LiquibaseService = wire[LiquibaseServiceImpl]

  lazy val ticketV1dao: TicketDao[V1] = wire[TicketDaoImpl[V1, BackEnd]]
  lazy val ticketV2dao: TicketDao[V2] = wire[TicketDaoImpl[V2, BackEnd]]

  private type BackEnd = PostgresProfile#Backend

  private trait DatabaseModel extends TicketModel

  private lazy val model: DatabaseModel = new DatabaseModel {
    override val databaseProfile: JdbcProfile = PostgresProfile
  }

  private lazy val database: BackEnd#Database = model.api.Database.forConfig(path = "tickets")

  private val config= ConfigFactory.load()
  private val liquibaseConfig = LiquibaseConfig(config)
}
