package org.antipathy.mvn_scalafmt.io

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import org.apache.maven.plugin.logging.SystemStreamLog
import java.net.MalformedURLException

class RemoteConfigReaderSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "RemoteConfigReader"

  it should "read a config from a remote location" in {

    val url =
      "https://raw.githubusercontent.com/SimonJPegg/mvn_scalafmt/35f3863c501b43beb59d84cb49fe124ee99c70a5/.scalafmt.conf"
    val reader         = new RemoteConfigReader(new SystemStreamLog)
    val expectedResult = "maxColumn = 120\n"

    reader.read(url).contents should be(expectedResult)
  }

  it should "raise an exception when unable to retrieve a config" in {
    val url    = "Skyrim belongs to the Nords"
    val reader = new RemoteConfigReader(new SystemStreamLog)

    an[MalformedURLException] should be thrownBy {
      reader.read(url)
    }
  }

}
