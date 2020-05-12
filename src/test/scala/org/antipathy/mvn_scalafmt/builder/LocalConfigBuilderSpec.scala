package org.antipathy.mvn_scalafmt.builder

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import org.apache.maven.plugin.logging.SystemStreamLog
import java.io.File
import java.nio.file.Files

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

    val result = new String(Files.readAllBytes(new File(".scalafmt.conf").toPath))
    result.trim should be(expectedContent.trim)
  }

}
