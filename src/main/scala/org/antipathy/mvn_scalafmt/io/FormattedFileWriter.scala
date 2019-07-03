package org.antipathy.mvn_scalafmt.io

import java.io.File
import java.nio.charset.StandardCharsets

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log

/**
  * Class for writing formatted source files
  */
class FormattedFileWriter(log: Log) extends Writer[FormatResult] {

  /**
    * Write the passed in input
    *
    * @param input The input to write
    */
  override def write(input: FormatResult): Unit = {
    log.debug(s"Writing ${input.sourceFile.getName} to ${input.sourceFile.getCanonicalPath}")

    val newFilePath = input.sourceFile.getCanonicalPath
    input.sourceFile.delete()

    FileUtils.writeStringToFile(new File(newFilePath), input.formattedSource, StandardCharsets.UTF_8)
  }
}
