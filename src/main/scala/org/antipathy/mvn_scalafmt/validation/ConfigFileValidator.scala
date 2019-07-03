package org.antipathy.mvn_scalafmt.validation

import java.nio.file.{Files, Path, Paths}

import org.apache.maven.plugin.logging.Log

/**
  * Class for validating the ScalaFmt config
  * @param log The maven logger
  */
class ConfigFileValidator(log: Log) extends Validator[String, Path] {

  /**
    * Validate the passed in input
    *
    * @param location The input to validate
    * @throws IllegalArgumentException when the path is invalid
    * @return The validated output
    */
  @throws[IllegalArgumentException]
  override def validate(location: String): Path =
    if (location == null || location.trim().equals("") || !Files.exists(Paths.get(location))) {
      val exception = new IllegalArgumentException(s"Config path is invalid: $location")
      log.error(exception)
      throw exception
    } else {
      Paths.get(location)
    }
}
