package org.antipathy.scalafmtmvn

import org.scalafmt.Error.UnableToParseCliOptions
import org.scalafmt.cli.{ Cli, CliOptions }

/**
 * Gets calls scalafmt with the config file specified at configLocation
 */
object Formatter {

  /**
   * Gets calls scalafmt with the config file specified at configLocation
   * @param configLocation the location of a scalafmt.conf file
   */
  def format(configLocation: String): Unit =
    Cli.getConfig(Array[String]("--config", configLocation), CliOptions.default) match {
      case Some(x) => Cli.run(x)
      case None =>
        throw new IllegalArgumentException(s"unable to pase config at $configLocation",
                                           UnableToParseCliOptions)
    }
}
