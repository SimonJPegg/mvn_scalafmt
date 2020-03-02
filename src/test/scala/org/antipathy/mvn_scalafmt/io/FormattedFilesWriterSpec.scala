package org.antipathy.mvn_scalafmt.io

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class FormattedFilesWriterSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "FormattedFileWriter"

  it should "Write files" in {
    val originalContent = "originalContent"
    val changedContent  = "changedContent"
    val sourceFile      = new File(s"${System.getProperty("java.io.tmpdir")}${File.separator}TempFile.scala")
    FileUtils.writeStringToFile(sourceFile, originalContent, StandardCharsets.UTF_8)

    val formatResult = FormatResult(sourceFile, originalContent, changedContent)
    val fileWriter   = new FormattedFilesWriter(new SystemStreamLog)

    new String(Files.readAllBytes(sourceFile.toPath)) should be(originalContent)

    fileWriter.write(Seq(formatResult))

    new String(Files.readAllBytes(sourceFile.toPath)) should be(changedContent)

    sourceFile.delete()
  }

}
