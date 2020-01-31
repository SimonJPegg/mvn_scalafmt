package org.antipathy.mvn_scalafmt.builder

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import java.io.File
import org.apache.maven.plugin.logging.SystemStreamLog

class ChangedFilesBuilderSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "ChangedFilesBuilder"

  it should "Identify files that have changed from master" in {
    val log        = new SystemStreamLog
    val sourceDirs = Seq("src/test/scala", "src/main/scala").map(new File(_))
    val sources    = new SourceFileSequenceBuilder(log).build(sourceDirs)
    val changedFiles = Seq(
      "/mvn_scalafmt/src/main/scala/org/antipathy/mvn_scalafmt/builder/ChangedFilesBuilder.scala",
      "/mvn_scalafmt/src/main/scala/org/antipathy/mvn_scalafmt/builder/SourceFileSequenceBuilder.scala",
      "/mvn_scalafmt/src/test/scala/org/antipathy/mvn_scalafmt/builder/ChangedFilesBuilderSpec.scala",
      "/mvn_scalafmt/src/test/scala/org/antipathy/mvn_scalafmt/builder/LocalConfigBuilderSpec.scala"
    )

    val changeFunction = () => changedFiles.map(new File(_))

    val result = new ChangedFilesBuilder(log, true, "master", changeFunction).build(sources)
    result should be(changedFiles.map(new File(_)))
  }
}
