package org.antipathy.mvn_scalafmt.builder

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import org.apache.maven.plugin.logging.SystemStreamLog
import java.io.File

class LocalConfigBuilderSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "LocalConfigBuilder"

  it should "Ensure a local config is correct" in {

    val builder = LocalConfigBuilder(new SystemStreamLog)
    val path    = ".scalafmt.conf"

    val resultPath = builder.build(path)

    resultPath.toString should be(path)
  }

  it should "Retrieve a remote config and store it locally" in {

    val builder = LocalConfigBuilder(new SystemStreamLog)
    val path    = "https://raw.githubusercontent.com/SimonJPegg/mvn_scalafmt/master/.scalafmt.conf"
    val expectedContent = scala.io.Source
      .fromURL("https://raw.githubusercontent.com/SimonJPegg/mvn_scalafmt/master/.scalafmt.conf")
      .mkString

    builder.build(path)

    val result = scala.io.Source.fromFile(new File(".scalafmt.conf")).getLines().mkString

    result.trim should be(expectedContent.trim)
  }

}
