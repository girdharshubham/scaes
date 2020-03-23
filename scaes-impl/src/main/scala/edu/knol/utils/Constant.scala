package edu.knol.utils

import com.sksamuel.elastic4s.{ElasticClient, ElasticProperties}
import com.sksamuel.elastic4s.http.JavaClient

object Constant {
  private val ElasticSearchContactPointKey: String = "elasticsearch.contact-point"
  private val ElasticSearchPortKey: String = "elasticsearch.port"
  private val ElasticSearchIndexNameKey: String = "elasticsearch.index"

  lazy val ElasticSearchContactPoint: String = utils.config.getString(ElasticSearchContactPointKey)
  lazy val ElasticSearchPort: String = utils.config.getString(ElasticSearchPortKey)
  lazy val ElasticSearchIndexName: String = utils.config.getString(ElasticSearchIndexNameKey)

  implicit lazy val client: ElasticClient =
    ElasticClient(JavaClient(ElasticProperties(s"http://${ElasticSearchContactPoint}:$ElasticSearchPort")))

}
