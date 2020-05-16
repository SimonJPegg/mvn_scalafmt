package org.antipathy.mvn_scalafmt.io

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.apache.maven.plugin.logging.Log
import org.mockito.Mockito
import org.antipathy.mvn_scalafmt.model.FormatResult
import java.io.File

class TestResultLogWriterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  behavior of "TestResultLogWriter"

  it should "write details of unformatted sources to a log" in {

    val log             = Mockito.mock(classOf[Log])
    val writer          = new TestResultLogWriter(log)
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

    val result = writer.write(input)

    result.totalFiles should be(input.length)
    result.unformattedFiles should be(1)
    result.fileDetails.length should be(input.length)
    result.fileDetails.filter(_.name == unformattedFile.getName).foreach { fd =>
      fd.name should be(unformattedFile.getName)
      fd.details should be("Requires formatting")
    }
    result.fileDetails.filter(_.name == formattedFile.getName).foreach { fd =>
      fd.name should be(formattedFile.getName)
      fd.details should be("Formatted")
    }
  }
}
