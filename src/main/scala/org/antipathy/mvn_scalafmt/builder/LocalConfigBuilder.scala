package org.antipathy.mvn_scalafmt.builder

import java.nio.file.Path

import org.antipathy.mvn_scalafmt.io.{Reader, RemoteConfigReader, RemoteConfigWriter, Writer}
import org.antipathy.mvn_scalafmt.model.RemoteConfig
import org.antipathy.mvn_scalafmt.validation.{ConfigFileValidator, Validator}
import org.apache.commons.validator.routines.UrlValidator
import org.apache.maven.plugin.logging.Log

/**
  * Class for building a local config from a remote location and validating the path is correct
  * @param urlValidator Class for validating if string is a valid url
  * @param configValidator Class for validating a local config's path
  * @param remoteConfigReader Class for reading a remote config
  * @param remoteConfigWriter Class for writing a remote config to a local path
  * @param log The maven logger
  */
class LocalConfigBuilder(
  urlValidator: UrlValidator,
  configValidator: Validator[String, Path],
  remoteConfigReader: Reader[String, RemoteConfig],
  remoteConfigWriter: Writer[RemoteConfig, Path],
  log: Log
) extends Builder[String, Path] {

  /**
    * Read an object from the specified location
    *
    * @param location The location to read from
    * @return The object at the location
    */
  override def build(location: String): Path =
    if (urlValidator.isValid(location)) {
      val remoteConfig = remoteConfigReader.read(location)
      val localConfig  = remoteConfigWriter.write(remoteConfig)
      configValidator.validate(localConfig.toString)
    } else {
      configValidator.validate(location)
    }
}

object LocalConfigBuilder {

  def apply(
    log: Log
  ): LocalConfigBuilder =
    new LocalConfigBuilder(
      new UrlValidator(Array("http", "https")),
      new ConfigFileValidator(log),
      new RemoteConfigReader(log),
      new RemoteConfigWriter(log),
      log
    )
}
