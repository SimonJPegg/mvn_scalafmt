package org.antipathy.scalafmtmvn

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

/**
  * Unit tests for Formatter
  */
class FormatterSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "Formatter"

  it should "raise an exception when unable to parse config file" in {
    an[IllegalArgumentException] should be thrownBy {
      Formatter.format("/some/invalid/path", "")
    }
  }
}
