package tracker.infrastructure

import akka.actor.ActorSystem
import com.softwaremill.macwire.wire
import com.typesafe.config.ConfigFactory
import slick.jdbc.{JdbcProfile, PostgresProfile}
import tracker.infrastructure.model.{TicketModel, V1, V2}
import tracker.infrastructure.persistence.{TicketDao, TicketDaoImpl}
import util.database.liquibase.{LiquibaseConfig, LiquibaseService, LiquibaseServiceImpl}
import util.helpers.{IdGeneratorHelper, IdGeneratorService}

import scala.concurrent.ExecutionContext

trait InfrastructureLayer {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  lazy val liquibaseService: LiquibaseService = wire[LiquibaseServiceImpl]
  lazy val idGeneratorHelper: IdGeneratorService = IdGeneratorHelper

  lazy val ticketDaoV1: TicketDao[V1] = wire[TicketDaoImpl[V1, BackEnd]]
  lazy val ticketDaoV2: TicketDao[V2] = wire[TicketDaoImpl[V2, BackEnd]]

  private type BackEnd = PostgresProfile#Backend

  private trait DatabaseModel extends TicketModel

  private lazy val model: DatabaseModel = new DatabaseModel {
    override val databaseProfile: JdbcProfile = PostgresProfile
  }

  private lazy val database: BackEnd#Database = model.api.Database.forConfig(path = "tracker-db")

  private val config= ConfigFactory.load()
  private val liquibaseConfig = LiquibaseConfig(config)
}
