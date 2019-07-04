package org.antipathy.mvn_scalafmt.filter

import java.io.File

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class UnchangedSourceFilterSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "UnformattedSourceFilter"

  it should "identify source files that have not changed" in {

    val fmtResult =
      scala.io.Source.fromFile("src/test/resources/FormatResult.scala").getLines().mkString(System.lineSeparator())
    val fmtResultChanged = scala.io.Source
      .fromFile("src/test/resources/FormatResultChanged.scala")
      .getLines()
      .mkString(System.lineSeparator())

    val unchangedResult = FormatResult(
      sourceFile = new File("a"),
      originalSource = fmtResult,
      formattedSource = fmtResult
    )

    val changedResult = FormatResult(
      sourceFile = new File("a"),
      originalSource = fmtResult,
      formattedSource = fmtResultChanged
    )

    val input = Seq(unchangedResult, changedResult)

    val result = input.filter(new UnchangedSourceFilter(new SystemStreamLog).filter)

    result.length should be(1)

    result.headOption match {
      case Some(head) =>
        head.originalSource should be(changedResult.originalSource)
        head.formattedSource should be(changedResult.formattedSource)
      case None => false should be(true)
    }

  }

}
