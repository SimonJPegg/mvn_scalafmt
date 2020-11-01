package org.antipathy.mvn_scalafmt.io
import org.antipathy.mvn_scalafmt.builder.FilesSummaryBuilder
import org.antipathy.mvn_scalafmt.model.{FileSummaryRequest, FormatResult, Summary}
import org.apache.maven.plugin.logging.Log

/** Class for writing test results to the log
  *
  * @param log The maven logger
  */
class TestResultLogWriter(log: Log) extends Writer[Seq[FormatResult], Summary] with FilesSummaryBuilder {

  /** Write the test results to a log
    *
    * @param input The input to write
    */
  override def write(input: Seq[FormatResult]): Summary = {
    input.filter(!_.isFormatted).foreach { item =>
      log.error(s"unformatted file at: ${item.sourceFile.getCanonicalPath}")
    }
    Summary(
      input.length,
      input.count(!_.isFormatted),
      build(FileSummaryRequest(input, "Formatted", "Requires formatting"))
    )
  }
}
