package org.antipathy.mvn_scalafmt.model

import org.apache.maven.plugin.logging.Log

// $COVERAGE-OFF$

/** Class representing the result of a run of the plugin
  * @param totalFiles The total number of files checked
  * @param unformattedFiles The number of unformatted files in the project
  * @param fileDetails Details of each of the files
  */
case class Summary(
  totalFiles: Long,
  unformattedFiles: Long,
  fileDetails: Seq[FileSummary]
) {

  def print(log: Log) = {
    log.info(s"Scalafmt results: $unformattedFiles of $totalFiles were unformatted")
    log.info("Details:")
    fileDetails.foreach { fileSummary =>
      log.info("- " + fileSummary.toString)
    }
  }
}

// $COVERAGE-ON$
