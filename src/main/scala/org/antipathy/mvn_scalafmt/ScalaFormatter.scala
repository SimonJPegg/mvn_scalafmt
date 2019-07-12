package org.antipathy.mvn_scalafmt

import org.scalafmt.cli.{Cli, CliOptions}
import org.apache.maven.plugin.logging.Log
import org.scalafmt.util.AbsoluteFile
import scala.collection.JavaConverters._
import java.io.File
import java.nio.file.{Files, Paths}
import java.util.{List => JList}

/**
  * Object to format scala source files using the Scalafmt library
  */
object ScalaFormatter {

  /**
    * Format the specified sources using the specified config and  parameters
    *
    * @param configLocation the location of the scalafmt.conf file
    * @param configRequired should the source be formatted if no config exists
    * @param parameters any paramters to pass to scalafmt
    * @param sourceRoots the main src locations
    * @param testSourceRoots the test source locations
    * @param log a maven logger
    */
  def format(
      configLocation: String,
      configRequired: Boolean,
      parameters: String,
      sourceRoots: JList[File],
      testSourceRoots: JList[File],
      log: Log
  ): Unit = {

    val config = parseConfigLocation(configLocation, configRequired, log)
    val params = parseParametersString(parameters, log)

    val sources: Seq[String] = getSourcePaths(sourceRoots.asScala) ++
      getSourcePaths(testSourceRoots.asScala)

    if (sources.nonEmpty) {
      val cliOptions = getCLiOptions(sources, config, params)
      log.info(sources.toString())
      log.info(s"Formatting ${sources.mkString(",")}")
      Cli.run(cliOptions)
    } else {
      log.error("No source files found, skipping formatting!")
    }
  }

  /** Get the commandline options object for scalafmt
    *
    * @param sources a collection of source directories
    * @param config the config file location
    * @param params the parameters to pass to scalafmt
    * @return a CLi config object
    */
  private[mvn_scalafmt] def getCLiOptions(
      sources: Seq[String],
      config: Seq[String],
      params: Seq[String]
  ): CliOptions = {

    val options = CliOptions(customFiles = sources.flatMap(path => AbsoluteFile.fromPath(path)))
    Cli.getConfig((params ++ config).toArray, options) match {
      case Some(cliConfig) => cliConfig
      case None => options
    }
  }

  /** Filters the passed in Sequence for valid file paths
    *
    * @param paths the paths to filter
    * @return a sequence of valid paths
    */
  private[mvn_scalafmt] def getSourcePaths(paths: Seq[File]): Seq[String] =
    if (paths == null) {
      Seq[String]()
    } else {
      paths.map(_.toString).filter(p => Files.exists(Paths.get(p)))
    }

  /** Parses the config string and returns a sequence used for formatting
    *
    * @param location The location of the config file
    * @param configRequired should the source be formatted if no config exists
    * @param log a Maven logger
    * @throws IllegalArgumentException when the path is invalid
    * @return a Sequence containing the config location
    */
  @throws[IllegalArgumentException]
  private[mvn_scalafmt] def parseConfigLocation(location: String, configRequired: Boolean, log: Log): Seq[String] = {
    val configExists = if (location == null || location.trim().equals("")) {
      false
    } else {
      Files.exists(Paths.get(location))
    }

    (configExists, configRequired) match {
      case (true, _) =>
        log.info(s"Using config at path: $location")
        Seq("--config", location)
      case (_, true) =>
        val exception = new IllegalArgumentException(s"configRequired is set and config path is invalid: $location")
        log.error(exception)
        throw exception
      case (_, false) =>
        log.warn("No configuration file specified, using scalafmt defaults")
        Seq[String]()
    }
  }

  /** Parse the passed in parameter string into a sequence of strings
    *
    * @param params The parameter string to parse
    * @param log a Maven logger
    * @return a sequence of the passed in string
    */
  private[mvn_scalafmt] def parseParametersString(params: String, log: Log): Seq[String] =
    if (params == null || params.trim().equals("")) {
      log.info("No options specified")
      Seq()
    } else {
      log.info(s"Options: $params ")
      params.split(" ")
    }
}
