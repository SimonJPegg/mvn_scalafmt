package org.antipathy.mvn_scalafmt.logging

import java.io.File

import org.apache.maven.monitor.logging.DefaultLog
import org.codehaus.plexus.logging.console.ConsoleLogger
import org.scalafmt.dynamic.exceptions.ScalafmtException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

class MavenLogReporterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  val reporter = new MavenLogReporter(new DefaultLog(new ConsoleLogger()))

  behavior of "MavenLogReporter"

  it should "throw an error if error is reported by Scalafmt" in {

    val ex1 = intercept[ScalafmtException] {
      reporter.error(new File("").toPath, "Oops")
    }

    ex1.getMessage shouldEqual "Oops"

    val ex2 = intercept[RuntimeException] {
      reporter.error(new File("").toPath, "No way!", new RuntimeException("Oops"))
    }

    ex2.getMessage shouldEqual "No way!"

    val ex3 = intercept[RuntimeException] {
      reporter.error(new File("").toPath, new RuntimeException("Oops"))
    }

    ex3.getMessage shouldEqual "Oops"
  }

}
