package org.antipathy.scalafmtmvn

import org.scalafmt.Error.UnableToParseCliOptions
import org.scalafmt.cli.{ Cli, CliOptions }

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Gets calls scalafmt with the config file specified at configLocation
 */
object Formatter {

  /**
   * Gets calls scalafmt with the config file specified at configLocation
   * @param configLocation the location of a scalafmt.conf file
   */
  def format(configLocation: String, parameters: String): Unit = {
    val params : Seq[String] = parameters match {
      case null => Seq("--config", configLocation)
      case string: String => parameters.split(" ") ++ Seq("--config", configLocation)
    }
    Cli.getConfig(params.toArray, CliOptions.default) match {
      case Some(x) => Cli.run(x)
      case None =>
        throw new IllegalArgumentException(s"unable to parse config: $configLocation",
                                           UnableToParseCliOptions)
    }
  }
}
