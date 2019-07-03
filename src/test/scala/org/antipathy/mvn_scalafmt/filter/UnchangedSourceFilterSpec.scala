package org.antipathy.mvn_scalafmt.filter

import java.io.File

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class UnchangedSourceFilterSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "UnformattedSourceFilter"

  it should "identify source files that have not changed" in {

    val unchangedResult = FormatResult(
      sourceFile = new File("a"),
      originalSource = "a",
      formattedSource = "a"
    )
    val changedResult = unchangedResult.copy(formattedSource = "\ta")

    val input = Seq(unchangedResult, changedResult)

    val result = input.filter(new UnchangedSourceFilter(new SystemStreamLog).filter)

    result.length should be(1)

    result.head.originalSource should be(changedResult.originalSource)
    result.head.formattedSource should be(changedResult.formattedSource)
  }

}
