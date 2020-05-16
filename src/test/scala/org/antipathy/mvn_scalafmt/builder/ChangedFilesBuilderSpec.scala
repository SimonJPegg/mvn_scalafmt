package org.antipathy.mvn_scalafmt.builder

import java.io.File
import java.nio.file.Paths

import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

class ChangedFilesBuilderSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

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
    ).map(x => getAbsolutePathFrom(x))

    val changeFunction = () => changedFiles.map(new File(_))

    val result = new ChangedFilesBuilder(log, true, "master", changeFunction).build(sources)
    result should be(changedFiles.map(new File(_)))
  }

  it should "return all files if diff is false" in {
    val log        = new SystemStreamLog
    val sourceDirs = Seq("src/test/scala", "src/main/scala").map(new File(_))
    val sources    = new SourceFileSequenceBuilder(log).build(sourceDirs)

    val changeFunction = () => sources

    val result = new ChangedFilesBuilder(log, false, "master", changeFunction).build(sources)
    result should be(sources)
  }

  it should "re-throw exceptions it encounters" in {
    import java.io.FileNotFoundException
    val log        = new SystemStreamLog
    val sourceDirs = Seq("src/test/scala", "src/main/scala").map(new File(_))
    val sources    = new SourceFileSequenceBuilder(log).build(sourceDirs)
    val changedFiles = Seq(
      "/mvn_scalafmt/src/main/scala/org/antipathy/mvn_scalafmt/builder/ChangedFilesBuilder.scala",
      "/mvn_scalafmt/src/main/scala/org/antipathy/mvn_scalafmt/builder/SourceFileSequenceBuilder.scala",
      "/mvn_scalafmt/src/test/scala/org/antipathy/mvn_scalafmt/builder/ChangedFilesBuilderSpec.scala",
      "/mvn_scalafmt/src/test/scala/org/antipathy/mvn_scalafmt/builder/LocalConfigBuilderSpec.scala"
    ).map(x => getAbsolutePathFrom(x))

    val changeFunction = () => throw new FileNotFoundException("Ooops")

    an[FileNotFoundException] should be thrownBy {
      new ChangedFilesBuilder(log, true, "master", changeFunction).build(sources)
    }
  }

  def getAbsolutePathFrom(path: String): String =
    Paths.get(path).normalize.toAbsolutePath.toString
}
