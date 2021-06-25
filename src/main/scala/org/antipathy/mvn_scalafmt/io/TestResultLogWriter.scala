package org.antipathy.mvn_scalafmt.io

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.maven.plugin.logging.Log

/** Class for writing test results to the log
  *
  * @param log The maven logger
  */
class TestResultLogWriter(log: Log) extends FormatResultsWriter {

  protected val formattedDetail: String   = "Formatted"
  protected val unformattedDetail: String = "Requires formatting"

  /** Write the test results to a log
    *
    * @param input The input to write
    */
  protected def processUnformattedFile(item: FormatResult): Unit =
    log.error(s"unformatted file at: ${item.sourceFile.getCanonicalPath}")
}
