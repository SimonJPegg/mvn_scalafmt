package org.antipathy.mvn_scalafmt.io

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.apache.maven.plugin.logging.Log
import org.mockito.Mockito
import java.io.File
import org.antipathy.mvn_scalafmt.model.{FileSummary, Summary, FormatResult}

class TestResultLogWriterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  behavior of "TestResultLogWriter"

  it should "write details of unformatted sources to a log" in {

    val log             = Mockito.mock(classOf[Log])
    val writer          = new TestResultLogWriter(log, false)
    val unformattedFile = Mockito.mock(classOf[File])
    val formattedFile   = Mockito.mock(classOf[File])
    val unformatted = FormatResult(
      sourceFile = unformattedFile,
      originalSource = "unformatted",
      formattedSource = "formatted"
    )
    val formatted = FormatResult(
      sourceFile = formattedFile,
      originalSource = "formatted",
      formattedSource = "formatted"
    )
    val input = Seq(unformatted, formatted)

    Mockito.when(unformattedFile.getName).thenReturn("unformatted.scala")
    Mockito.when(formattedFile.getName).thenReturn("formatted.scala")

    writer.write(input) shouldBe Summary(
      2,
      1,
      Seq(
        FileSummary(unformattedFile.getName, "Requires formatting"),
        FileSummary(formattedFile.getName, "Formatted")
      )
    )
  }

  it should "write details of unformatted sources to a log, reformatted only" in {

    val log             = Mockito.mock(classOf[Log])
    val writer          = new TestResultLogWriter(log, true)
    val unformattedFile = Mockito.mock(classOf[File])
    val formattedFile   = Mockito.mock(classOf[File])
    val unformatted = FormatResult(
      sourceFile = unformattedFile,
      originalSource = "unformatted",
      formattedSource = "formatted"
    )
    val formatted = FormatResult(
      sourceFile = formattedFile,
      originalSource = "formatted",
      formattedSource = "formatted"
    )
    val input = Seq(unformatted, formatted)

    Mockito.when(unformattedFile.getName).thenReturn("unformatted.scala")
    Mockito.when(formattedFile.getName).thenReturn("formatted.scala")

    writer.write(input) shouldBe Summary(
      2,
      1,
      Seq(FileSummary(unformattedFile.getName, "Requires formatting"))
    )
  }

}
