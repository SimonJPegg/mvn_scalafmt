package org.antipathy.mvn_scalafmt.logging

import java.io.{OutputStreamWriter, PrintWriter}
import java.nio.file.Path

import org.apache.maven.plugin.logging.Log
import org.scalafmt.dynamic.exceptions.ScalafmtException
import org.scalafmt.interfaces.ScalafmtReporter

/** Class for logging Scalafmt events via the maven log
  * @param log A maven log instance
  */
class MavenLogReporter(log: Log) extends ScalafmtReporter {

  /** log errors
    */
  override def error(path: Path, message: String): Unit =
    throw ScalafmtException(message, null)

  /** log errors with exceptions
    */
  override def error(file: Path, e: Throwable): Unit =
    throw e

  /** log excluded files
    */
  override def excluded(filename: Path): Unit = log.warn(s"ScalaFmt: file excluded: $filename")

  /** Log the version of scalafmt used
    */
  override def parsedConfig(config: Path, scalafmtVersion: String): Unit =
    log.info(s"parsed config (v$scalafmtVersion): $config")

  /** This method appears to be used to print download information,  sys.err should be fine here.
    */
  @deprecated override def downloadWriter(): PrintWriter = new PrintWriter(System.err)

  /** This method appears to be used to print download information,  sys.err should be fine here.
    */
  override def downloadOutputStreamWriter(): OutputStreamWriter = new OutputStreamWriter(System.err)
}
