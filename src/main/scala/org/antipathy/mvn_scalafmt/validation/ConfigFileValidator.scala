package org.antipathy.mvn_scalafmt.validation

import java.nio.file.{Files, Path, Paths}

import org.apache.maven.plugin.logging.Log

/** Class for validating the ScalaFmt config
  * @param log The maven logger
  */
class ConfigFileValidator(
  log: Log
) extends Validator[String, Path] {

  /** Validate the passed in input
    *
    * @param location The input to validate
    * @throws IllegalArgumentException when the path is invalid
    * @return The validated output
    */
  @throws[IllegalArgumentException]
  override def validate(location: String): Path =
    location match {
      case "" | null => throw buildException(s"Config path is null or empty")
      case invalidPath if !Files.exists(Paths.get(invalidPath)) =>
        throw buildException(s"Config path is invalid: $location")
      case _ => Paths.get(location)
    }

  private def buildException(message: String): Exception = {
    val exception = new IllegalArgumentException(message)
    log.error(exception)
    exception
  }
}
