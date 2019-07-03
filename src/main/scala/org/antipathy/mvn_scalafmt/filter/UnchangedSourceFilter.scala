package org.antipathy.mvn_scalafmt.filter

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.maven.plugin.logging.Log

/**
  * Class for filtering source files that did not require formatting
  */
class UnchangedSourceFilter(log: Log) extends Filter[FormatResult, Boolean] {

  /**
    * Filter the passed in input
    *
    * @param input the input to filter
    * @return True if the formatted source does not match the unformatted source
    */
  override def filter(input: FormatResult): Boolean = {
    val result = !input.originalSource.trim.equals(input.formattedSource.trim)
    if (!result) {
      log.debug(s"${input.sourceFile.getName} has not changed")
    }
    result
  }
}
