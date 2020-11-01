package org.antipathy.mvn_scalafmt.io

import java.io.File
import org.antipathy.mvn_scalafmt.builder.FilesSummaryBuilder
import org.antipathy.mvn_scalafmt.model.{FileSummaryRequest, FormatResult, Summary}
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log

/** Class for writing formatted source files
  */
class FormattedFilesWriter(log: Log) extends Writer[Seq[FormatResult], Summary] with FilesSummaryBuilder {

  /** Write the passed in input
    *
    * @param input The input to write
    */
  override def write(input: Seq[FormatResult]): Summary = {
    val unformattedFiles = input.filter(!_.isFormatted)
    unformattedFiles.foreach(writeFile)
    Summary(
      input.length,
      unformattedFiles.length,
      build(FileSummaryRequest(input, "Correctly formatted", "Reformatted"))
    )
  }

  /** Write each FormatResult to disk
    *
    * @param input The input to write
    */
  private def writeFile(input: FormatResult): Unit = {
    import org.antipathy.mvn_scalafmt.ScalaFormatter
    log.debug(s"Writing ${input.sourceFile.getName} to ${input.sourceFile.getCanonicalPath}")

    val newFilePath = input.sourceFile.getCanonicalPath
    input.sourceFile.delete()

    FileUtils.writeStringToFile(new File(newFilePath), input.formattedSource, ScalaFormatter.fileEncoding)
  }
}
