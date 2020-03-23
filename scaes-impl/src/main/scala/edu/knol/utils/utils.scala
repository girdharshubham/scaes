package edu.knol.utils

import com.typesafe.config.{Config, ConfigFactory}

package object utils {
  val config: Config = ConfigFactory.load()
}
