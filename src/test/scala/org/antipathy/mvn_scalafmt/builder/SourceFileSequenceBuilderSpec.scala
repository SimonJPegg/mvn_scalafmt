package org.antipathy.mvn_scalafmt.builder

import java.io.File

import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class SourceFileSequenceBuilderSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "SourceFileSequenceBuilder"

  it should "Create a sequence of valid source paths" in {
    val input = Seq("src/test/scala", "src/main/scala").map(new File(_))
    val result = new SourceFileSequenceBuilder(new SystemStreamLog).build(input).map(_.getName)

    result.contains("ScalaFormatterSpec.scala") should be(true)
    result.contains("SourceFileFormatterSpec.scala") should be(true)
    result.contains("SourceFileSequenceBuilderSpec.scala") should be(true)
    result.contains("ConfigFileValidatorSpec.scala") should be(true)
    result.contains("ScalaFormatter.scala") should be(true)
    result.contains("FormatResult.scala") should be(true)
    result.contains("Formatter.scala") should be(true)
    result.contains("SourceFileFormatter.scala") should be(true)
    result.contains("Builder.scala") should be(true)
    result.contains("SourceFileSequenceBuilder.scala") should be(true)
    result.contains("MavenLogReporter.scala") should be(true)
    result.contains("ConfigFileValidator.scala") should be(true)
    result.contains("Validator.scala") should be(true)
  }

  it should "Create an empty sequence when given invalid paths" in {
    new SourceFileSequenceBuilder(new SystemStreamLog)
      .build(Seq("src/main1/scala", "src/test1/scala").map(new File(_))) should be(Seq())
  }

  it should "Create an empty sequence when given null values" in {
    new SourceFileSequenceBuilder(new SystemStreamLog).build(null) should be(Seq())
  }
}
