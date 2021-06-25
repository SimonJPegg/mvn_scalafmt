package org.antipathy.mvn_scalafmt.io

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

import org.antipathy.mvn_scalafmt.model.{FileSummary, FormatResult, Summary}
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

class FormattedFilesWriterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  behavior of "FormattedFileWriter"

  it should "Write files" in {
    val originalContent = "originalContent"
    val changedContent  = "changedContent"
    val sourceFile      = new File(s"${System.getProperty("java.io.tmpdir")}${File.separator}TempFile.scala")

    FileUtils.writeStringToFile(sourceFile, originalContent, StandardCharsets.UTF_8)
    new String(Files.readAllBytes(sourceFile.toPath)) should be(originalContent)

    new FormattedFilesWriter(new SystemStreamLog, false)
      .write(Seq(FormatResult(sourceFile, originalContent, changedContent))) shouldBe Summary(
      1,
      1,
      Seq(FileSummary(sourceFile.getName, "Reformatted"))
    )

    new String(Files.readAllBytes(sourceFile.toPath)) should be(changedContent)

    new FormattedFilesWriter(new SystemStreamLog, false)
      .write(Seq(FormatResult(sourceFile, changedContent, changedContent))) shouldBe Summary(
      1,
      0,
      Seq(FileSummary(sourceFile.getName, "Correctly formatted"))
    )

    new String(Files.readAllBytes(sourceFile.toPath)) should be(changedContent)

    sourceFile.delete()
  }

  it should "Write files and print reformatted only" in {
    val originalContent = "originalContent"
    val changedContent  = "changedContent"
    val sourceFile      = new File(s"${System.getProperty("java.io.tmpdir")}${File.separator}TempFile.scala")

    FileUtils.writeStringToFile(sourceFile, originalContent, StandardCharsets.UTF_8)
    new String(Files.readAllBytes(sourceFile.toPath)) should be(originalContent)

    new FormattedFilesWriter(new SystemStreamLog, true)
      .write(Seq(FormatResult(sourceFile, originalContent, changedContent))) shouldBe Summary(
      1,
      1,
      Seq(FileSummary(sourceFile.getName, "Reformatted"))
    )

    new String(Files.readAllBytes(sourceFile.toPath)) should be(changedContent)

    new FormattedFilesWriter(new SystemStreamLog, true)
      .write(Seq(FormatResult(sourceFile, changedContent, changedContent))) shouldBe Summary(
      1,
      0,
      Seq.empty
    )

    new String(Files.readAllBytes(sourceFile.toPath)) should be(changedContent)

    sourceFile.delete()
  }

}
