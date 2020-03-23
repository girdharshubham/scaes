package edu.knol

import java.time.Instant

import edu.knol.command.TemperatureCommand
import edu.knol.model.Temperature

import scala.util.{Failure, Random, Success, Try}


object Main extends App {

  import utils.Constant.client

  val temperatureInstance: Temperature =
    Temperature(deviceId = "boiler-room", timestamp = Instant.now().toString, temperature = Random.nextDouble())

  import com.sksamuel.elastic4s.ElasticDsl._

  val command = new TemperatureCommand

  Try {
    command.createEsIndex.await
    command.insert(temperatureInstance).await
    command.searchES(temperatureInstance.deviceId).await
  } match {
    case Success(value) => println(value.result.hits.hits.toList.map(resHit => resHit.sourceAsString))
    case Failure(exception) => println(exception)
  }
}
