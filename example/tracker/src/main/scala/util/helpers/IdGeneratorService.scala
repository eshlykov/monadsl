package util.helpers

import com.softwaremill.id.pretty.PrettyIdGenerator

trait IdGeneratorService {
  def nextId(): String
}

object IdGeneratorHelper extends IdGeneratorService {
  override def nextId(): String = idGenerator.nextId()

  private val idGenerator: PrettyIdGenerator = PrettyIdGenerator.singleNode
}
