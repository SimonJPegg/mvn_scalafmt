package org.antipathy.mvn_scalafmt.builder

import org.antipathy.mvn_scalafmt.model.{FileSummary, FileSummaryRequest}

/** Trait for building file summaries
  */
trait FilesSummaryBuilder extends Builder[FileSummaryRequest, Seq[FileSummary]] {

  /** Build a summary of the format run from the passed in `FormatResult`s
    * @param input The input to build from
    * @return The built output
    */
  override def build(input: FileSummaryRequest): Seq[FileSummary] =
    input.formatResults.map { item =>
      val details =
        if (item.isFormatted)
          input.formattedValue
        else
          input.unformattedValue
      FileSummary(item.sourceFile.getName, details)
    }
}
