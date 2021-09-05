package org.antipathy.mvn_scalafmt.io

import java.io.File
import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log

/** Class for writing formatted source files
  */
class FormattedFilesWriter(log: Log, val showReformattedOnly: Boolean) extends FormatResultsWriter {

  protected val formattedDetail: String   = "Correctly formatted"
  protected val unformattedDetail: String = "Reformatted"

  /** Write each FormatResult to disk
    *
    * @param input The input to write
    */
  protected def processUnformattedFile(input: FormatResult): Unit = {
    import org.antipathy.mvn_scalafmt.ScalaFormatter
    log.debug(s"Writing ${input.sourceFile.getName} to ${input.sourceFile.getCanonicalPath}")

    val newFilePath = input.sourceFile.getCanonicalPath
    input.sourceFile.delete()

    FileUtils.writeStringToFile(new File(newFilePath), input.formattedSource, ScalaFormatter.fileEncoding)
  }
}
