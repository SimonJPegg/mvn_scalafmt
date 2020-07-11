package org.antipathy.mvn_scalafmt.model

// $COVERAGE-OFF$

/**
  * Class representing the result of a run of the plugin
  * @param totalFiles The total number of files checked
  * @param unformattedFiles The number of unformatted files in the project
  * @param fileDetails Details of each of the files
  */
case class Summary(
  totalFiles: Long,
  unformattedFiles: Long,
  fileDetails: Seq[FileSummary]
) {

  override def toString: String =
    s"""Scalafmt results: $unformattedFiles of $totalFiles were unformatted
       |Details:
       |${fileDetails.mkString(System.lineSeparator)}
     """.stripMargin
}

// $COVERAGE-ON$