package org.antipathy.mvn_scalafmt.format

import java.io.File
import java.nio.file.Files

import org.antipathy.mvn_scalafmt.logging.MavenLogReporter
import org.antipathy.mvn_scalafmt.validation.ConfigFileValidator
import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalafmt.interfaces.Scalafmt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

class SourceFileFormatterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  behavior of "SourceFileFormatter"

  it should "format a source file" in {

    val log        = new SystemStreamLog
    val config     = new ConfigFileValidator(log).validate(".scalafmt.conf")
    val sourceFile = new File("src/main/scala/org/antipathy/mvn_scalafmt/model/FormatResult.scala")
    val reporter   = new MavenLogReporter(log)
    val scalafmt: Scalafmt =
      Scalafmt.create(this.getClass.getClassLoader).withRespectVersion(false).withReporter(reporter)

    val result = new SourceFileFormatter(config, scalafmt, log).format(sourceFile).formattedSource

    result.trim should be(new String(Files.readAllBytes(sourceFile.toPath)).trim)
  }
}
