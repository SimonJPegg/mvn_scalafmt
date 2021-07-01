package org.antipathy.mvn_scalafmt.io

import org.antipathy.mvn_scalafmt.model.{FileSummary, FormatResult, Summary}

/** Class for writing formatted source files
  */
abstract class FormatResultsWriter extends Writer[Seq[FormatResult], Summary] {

  protected val showReformattedOnly: Boolean
  protected val formattedDetail: String
  protected val unformattedDetail: String
  protected def processUnformattedFile(input: FormatResult): Unit

  /** Write the passed in input
    *
    * @param input The input to write
    */
  final override def write(input: Seq[FormatResult]): Summary = {
    val unformattedFiles = input.filter(!_.isFormatted)
    unformattedFiles.foreach(processUnformattedFile)
    val results = if (showReformattedOnly) unformattedFiles else input
    Summary(input.length, unformattedFiles.length, build(results))
  }

  /** Build a summary of the format run from the passed in `FormatResult`s
    * @param input The input to build from
    * @return The built output
    */
  private def build(formatResults: Seq[FormatResult]): Seq[FileSummary] =
    formatResults.map { item =>
      val isFormatted = item.isFormatted
      val details     = if (isFormatted) formattedDetail else unformattedDetail
      FileSummary(item.sourceFile.getName, details)
    }

}
