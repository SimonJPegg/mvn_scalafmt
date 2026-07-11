package org.antipathy.mvn_scalafmt.logging

import java.io.File

import org.apache.maven.monitor.logging.DefaultLog
import org.codehaus.plexus.logging.console.ConsoleLogger
import org.scalafmt.dynamic.exceptions.{ScalafmtException => DynamicScalafmtException}
import org.scalafmt.interfaces.{ScalafmtException => InterfacesScalafmtException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers
import org.codehaus.plexus.logging.Logger

class MavenLogReporterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  val reporter = new MavenLogReporter(
    new DefaultLog(new ConsoleLogger(Logger.LEVEL_DEBUG, this.getClass.getSimpleName))
  )

  behavior of "MavenLogReporter"

  it should "throw an error if error is reported by Scalafmt" in {

    // error(Path, String) creates a dynamic ScalafmtException directly
    val ex1 = intercept[DynamicScalafmtException] {
      reporter.error(new File("").toPath, "Oops")
    }

    ex1.getMessage shouldEqual "Oops"

    // The 3-arg default method in ScalafmtReporter wraps in interfaces.ScalafmtException(message, cause)
    // then delegates to error(Path, Throwable), which rethrows it - so the thrown type is interfaces.ScalafmtException
    val ex2 = intercept[InterfacesScalafmtException] {
      reporter.error(new File("").toPath, "No way!", new RuntimeException("Oops"))
    }

    ex2.getMessage shouldEqual "No way!"

    // error(Path, Throwable) rethrows as-is
    val ex3 = intercept[RuntimeException] {
      reporter.error(new File("").toPath, new RuntimeException("Oops"))
    }

    ex3.getMessage shouldEqual "Oops"
  }

}
