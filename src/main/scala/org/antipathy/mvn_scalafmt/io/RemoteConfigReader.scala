package org.antipathy.mvn_scalafmt.io

import java.net.URL

import org.antipathy.mvn_scalafmt.model.RemoteConfig
import org.apache.maven.plugin.logging.Log
import scala.util.{Failure, Success, Try}

/**
  * Class for retrieving a config from a remote location
  *
  * @param log The maven logger
  */
class RemoteConfigReader(log: Log) extends Reader[String, RemoteConfig] {

  /**
    * Read an object from the specified location
    *
    * @param location The url to read from
    * @return A remote config
    */
  override def read(location: String): RemoteConfig =
    Try {
      log.info(s"Reading config from $location")
      RemoteConfig(
        contents = scala.io.Source.fromURL(new URL(location)).mkString
      )
    } match {
      case Success(value) => value
      case Failure(exception) =>
        log.error(s"error retrieving remote config: ${exception.getMessage}", exception)
        throw exception
    }
}
