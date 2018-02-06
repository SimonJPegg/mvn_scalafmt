package org.antipathy.scalafmtmvn

import org.scalafmt.Error.UnableToParseCliOptions
import org.scalafmt.cli.{Cli, CliOptions}

import org.scalafmt.util.AbsoluteFile

/**
  * Gets calls scalafmt with the config file specified at configLocation
  */
object Formatter {

  /**
    * Gets calls scalafmt with the config file specified at configLocation
    * @param configLocation the location of a scalafmt.conf file
    */
  def format(configLocation: String, parameters: String, sourceLocations: Array[String]): Unit = {
    val emptySeq = Seq[String]()

    val params: Seq[String] = if (parameters == null) emptySeq else parameters.split(" ")
    val config: Seq[String] = if (configLocation == null) emptySeq else Seq("--config", configLocation)

    val sourcePaths = sourceLocations.flatMap(path => AbsoluteFile.fromPath(path))

    val options = CliOptions(customFiles = sourcePaths)

    Cli.getConfig((params ++ config).toArray, options) match {
      case Some(x) => Cli.run(x)
      case None => Cli.run(options)
    }
  }
}
