package org.antipathy.mvn_scalafmt.validation

import java.nio.file.Paths

import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

class ConfigFileValidatorSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  behavior of "ConfigFileValidator"

  it should "Return an error when the config file path is empty" in {
    an[IllegalArgumentException] should be thrownBy {
      new ConfigFileValidator(new SystemStreamLog).validate("")
    }
  }

  it should "Return an error when the config file path is null" in {
    an[IllegalArgumentException] should be thrownBy {
      new ConfigFileValidator(new SystemStreamLog).validate(null)
    }
  }

  it should "Create a valid config sequence when passed a config location" in {
    new ConfigFileValidator(new SystemStreamLog).validate(".scalafmt.conf") should be(
      Paths.get(".scalafmt.conf")
    )
  }

  it should "Raise an exception when the config path is invalid and a config is required" in {
    an[IllegalArgumentException] should be thrownBy {
      new ConfigFileValidator(new SystemStreamLog).validate("--config")
    }
  }

}
