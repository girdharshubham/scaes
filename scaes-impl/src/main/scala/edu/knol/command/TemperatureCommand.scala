package edu.knol.command

import com.sksamuel.elastic4s.requests.common.RefreshPolicy
import com.sksamuel.elastic4s.requests.indexes.{CreateIndexResponse, IndexResponse}
import com.sksamuel.elastic4s.requests.searches.SearchResponse
import com.sksamuel.elastic4s.{ElasticClient, Response}
import edu.knol.model.Temperature

import scala.concurrent.Future


class TemperatureCommand(implicit val client: ElasticClient) {

  import edu.knol.utils.Constant.ElasticSearchIndexName
  import com.sksamuel.elastic4s.ElasticDsl._

  private def indexName: String = ElasticSearchIndexName

  private def deviceId: String = "deviceId"
  private def timestamp: String = "timestamp"
  private def temperature: String = "temperature"

  def createEsIndex: Future[Response[CreateIndexResponse]] = client
    .execute(
      createIndex(indexName).mapping(
        properties(fields = List(
          keywordField(deviceId),
          dateField(timestamp),
          doubleField(temperature)
        ))
      )
    )

  def insert(temperatureInstance: Temperature): Future[Response[IndexResponse]] = client
    .execute(
      indexInto(indexName).fields(deviceId -> temperatureInstance.deviceId,
        timestamp -> temperatureInstance.timestamp,
        temperature -> temperatureInstance.temperature
      ).refresh(RefreshPolicy.IMMEDIATE)
    )

  def searchES(searchDevice: String): Future[Response[SearchResponse]] = client
    .execute(
      search(indexName) query searchDevice
    )

  def deleteES(searchDevice: String) = client
    .execute(
      delete(searchDevice) from indexName
    )
}
