package org.antipathy.mvn_scalafmt.model

/**
  * Class representing a request to build a file summary
  * @param formatResults the formatResults
  * @param formattedValue The value to use if a file is formatted
  * @param unformattedValue The value to use if a file is unformatted
  */
case class FileSummaryRequest(
    formatResults: Seq[FormatResult],
    formattedValue: String,
    unformattedValue: String
)
