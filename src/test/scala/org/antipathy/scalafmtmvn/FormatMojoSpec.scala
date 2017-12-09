package org.antipathy.scalafmtmvn

import org.apache.maven.plugin.MojoExecutionException
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

/**
  * Unit tests for FormatMojo
  */
class FormatMojoSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "FormatMojo"

  it should "raise an exception when no config file is specified" in {
    val mojo = new FormatMojo
    an[MojoExecutionException] should be thrownBy {
      mojo.execute()
    }
  }
}
