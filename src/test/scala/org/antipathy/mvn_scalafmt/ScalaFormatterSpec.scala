package org.antipathy.mvn_scalafmt

import java.io.File

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import org.apache.maven.plugin.logging.SystemStreamLog

/**
  * Unit tests for ScalaFormatter
  */
class ScalaFormatterSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "ScalaFormatter"

  it should "Return an empty sequence when passed an empty param string" in {
    ScalaFormatter.parseParametersString("", new SystemStreamLog) should be(Seq())
  }

  it should "Return an empty sequence when passed a null param string" in {
    ScalaFormatter.parseParametersString(null, new SystemStreamLog) should be(Seq())
  }

  it should "Split supplied parameters into an array" in {
    val expectedResult = Seq("one", "two", "three")
    ScalaFormatter.parseParametersString(expectedResult.mkString(" "), new SystemStreamLog) should be(expectedResult)
  }

  it should "Return an empty sequence when passed an empty config string and no config is required" in {
    ScalaFormatter.parseConfigLocation("", configRequired = false, new SystemStreamLog) should be(Seq())
  }

  it should "Return an empty sequence when passed a null config string and no config is required" in {
    ScalaFormatter.parseConfigLocation(null, configRequired = false, new SystemStreamLog) should be(Seq())
  }

  it should "Create a valid config sequence when passed a config location" in {
    val expectedResult = Seq("--config", ".scalafmt.conf")
    ScalaFormatter.parseConfigLocation(expectedResult(1), configRequired = false, new SystemStreamLog) should be(
      expectedResult)
  }

  it should "Return an empty sequence when passed an empty config string and a config is required" in {
    an[IllegalArgumentException] should be thrownBy {
      ScalaFormatter.parseConfigLocation("", configRequired = true, new SystemStreamLog) should be(Seq())
    }
  }

  it should "Return an empty sequence when passed a null config string and a config is required" in {
    an[IllegalArgumentException] should be thrownBy {
      ScalaFormatter.parseConfigLocation(null, configRequired = true, new SystemStreamLog) should be(Seq())
    }
  }

  it should "Raise an exception when the config path is invalid and a config is required" in {
    val expectedResult = Seq("--config", "/some/invalid/path")
    an[IllegalArgumentException] should be thrownBy {
      ScalaFormatter.parseConfigLocation(expectedResult(1), configRequired = true, new SystemStreamLog) should be(
        expectedResult)
    }
  }

  it should "Create a sequence of valid source paths" in {
    val sources = Seq(new File("src/main/scala"), new File("src/test/scala"))
    val expectedResult = Seq("src/main/scala", "src/test/scala")
    canonical(ScalaFormatter.getSourcePaths(sources, new SystemStreamLog)) should be(canonical(expectedResult))
  }

  it should "Create an empty sequence when given invalid paths" in {
    ScalaFormatter.getSourcePaths(Seq(new File("src/main1/scala"), new File("src/test1/scala")), new SystemStreamLog) should be(
      Seq())
  }

  it should "Create an empty sequence when given null values" in {
    ScalaFormatter.getSourcePaths(null, new SystemStreamLog) should be(Seq())
  }

  private def canonical(paths: Seq[String]) =
    paths.map(new File(_).getCanonicalFile)

}
