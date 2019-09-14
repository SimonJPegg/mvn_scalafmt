package org.antipathy.mvn_scalafmt.logging

import java.io.PrintWriter
import java.nio.file.Path

import org.apache.maven.plugin.logging.Log
import org.scalafmt.interfaces.ScalafmtReporter
import org.scalafmt.dynamic.exceptions.ScalafmtException

/**
  * Class for logging Scalafmt events via the maven log
  * @param log A maven log instance
  */
class MavenLogReporter(log: Log) extends ScalafmtReporter {



  /**
    * log errors
    */
  override def error(path: Path, message: String): Unit = log.error(s"ScalaFmt: error: $path: $message")

  /**
    * log errors with exceptions
    */
  override def error(file: Path, e: Throwable): Unit = error(file, ScalafmtException(e.getMessage, e))

  /**
    * log excluded files
    */
  override def excluded(filename: Path): Unit = log.warn(s"ScalaFmt: file excluded: $filename")

  /**
    * Log the version of scalafmt used
    */
  override def parsedConfig(config: Path, scalafmtVersion: String): Unit =
    log.info(s"parsed config (v$scalafmtVersion): $config")

  /**
    * This method appears to be used to print download information,  sys.err should be fine here.
    */
  override def downloadWriter(): PrintWriter = new PrintWriter(System.err)

}
