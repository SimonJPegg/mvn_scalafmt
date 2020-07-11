package org.antipathy.mvn_scalafmt.builder

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers
import org.apache.maven.plugin.logging.SystemStreamLog
import java.io.File
import java.nio.file.Files

class LocalConfigBuilderSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

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
    val source = scala.io.Source
      .fromURL("https://raw.githubusercontent.com/SimonJPegg/mvn_scalafmt/master/.scalafmt.conf")
    val expectedContent = source.mkString

    builder.build(path)

    val result = new String(Files.readAllBytes(new File(".scalafmt.conf").toPath))
    result.trim should be(expectedContent.trim)
    source.close()
  }

}
