package org.antipathy.mvn_scalafmt.builder

import org.antipathy.mvn_scalafmt.builder.FilesSummaryBuilder.{FormattedValue, UnformattedValue}
import org.antipathy.mvn_scalafmt.model.{FileSummary, FormatResult}

/**
  * Trait for building file summaries
  */
trait FilesSummaryBuilder extends Builder[(Seq[FormatResult], FormattedValue, UnformattedValue), Seq[FileSummary]] {

  /**
    * Build a summary of the format run from the passed in `FormatResult`s
    * @param input The input to build from
    * @return The built output
    */
  override def build(input: (Seq[FormatResult], FormattedValue, UnformattedValue)): Seq[FileSummary] = input._1.map {
    item =>
      val details = if (item.isFormatted) {
        input._2
      } else {
        input._3
      }
      FileSummary(item.sourceFile.getName, details)
  }
}

/**
  * Companion class
  */
object FilesSummaryBuilder {
  type FormattedValue = String
  type UnformattedValue = String
}
