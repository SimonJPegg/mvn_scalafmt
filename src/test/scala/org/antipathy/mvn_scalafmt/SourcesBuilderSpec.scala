package org.antipathy.mvn_scalafmt

import java.io.File
import java.nio.file.{Path, Paths}
import java.util.{List => JList}

import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

import org.antipathy.mvn_scalafmt.SourcesBuilderSpec._
import org.apache.maven.model.{Build, Model}
import org.apache.maven.project.MavenProject
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SourcesBuilderSpec extends AnyFlatSpec with Matchers {

  behavior of "ProjectSourceCollectionBuilder"

  it should "add nonempty main" in {
    val builder = new SourcesBuilder(getMavenProject())
    builder.addMain(Seq("foo", "/bar", "foo"))
    builder.resultSet() shouldBe Set[File]("/xyz/foo", "/bar")
    builder.resultSet() shouldBe empty
  }

  it should "add empty main" in {
    val mavenBuild = new Build() {
      setSourceDirectory("/foo/src/main/java")
    }

    val builder = new SourcesBuilder(getMavenProject(mavenBuild))
    builder.addMain(null)
    builder.resultSet() shouldBe Set[File]("/foo/src/main/scala")
    builder.resultSet() shouldBe empty
  }

  it should "add nonempty test" in {
    val builder = new SourcesBuilder(getMavenProject())
    builder.addTest(Seq("foo", "/bar", "/bar"))
    builder.resultSet() shouldBe Set[File]("/xyz/foo", "/bar")
    builder.resultSet() shouldBe empty
  }

  it should "add empty test" in {
    val mavenBuild = new Build() {
      setTestSourceDirectory("/foo/src/test/scala")
    }

    val builder = new SourcesBuilder(getMavenProject(mavenBuild))
    builder.addTest(null)
    builder.resultSet() shouldBe Set[File]("/foo/src/test/scala")
    builder.resultSet() shouldBe empty
  }

  it should "add nonempty both" in {
    val builder = new SourcesBuilder(getMavenProject())
    builder.addMain(Seq("foo", "/bar"))
    builder.addTest(Seq("foo", "/qux"))
    builder.resultSet() shouldBe Set[File]("/xyz/foo", "/bar", "/qux")
    builder.resultSet() shouldBe empty
  }

  it should "add empty both" in {
    val mavenBuild = new Build() {
      setSourceDirectory("/foo/src/main/java")
      setTestSourceDirectory("/foo/src/test/java")
    }

    val builder = new SourcesBuilder(getMavenProject(mavenBuild))
    builder.addMain(null)
    builder.addTest(null)
    builder.resultSet() shouldBe Set[File]("/foo/src/main/scala", "/foo/src/test/scala")
    builder.resultSet() shouldBe empty
  }

  it should "add empty both, filtering" in {
    val mavenBuild = new Build() {
      setSourceDirectory("/foo/src/main/java")
      setTestSourceDirectory("/foo/src/test/java")
    }
    val project = getMavenProject(mavenBuild)
    project.addCompileSourceRoot("/foo/src/main/scala")
    project.addCompileSourceRoot("/out/src/main/scala")
    project.addCompileSourceRoot("/out2/src/main/scala")
    project.addTestCompileSourceRoot("/foo/src/test/scala")
    project.addTestCompileSourceRoot("/out/src/test/scala")
    project.addTestCompileSourceRoot("/out2/src/test/scala")

    val builder = new SourcesBuilder(project)
    builder.addMain(null)
    builder.addTest(null)
    builder.resultSet() shouldBe Set[File](
      "/foo/src/main/scala",
      "/out2/src/main/scala",
      "/foo/src/test/scala",
      "/out2/src/test/scala"
    )
    builder.resultSet() shouldBe empty
  }

}

private object SourcesBuilderSpec {

  def getMavenProject(mavenBuild: Build = new Build()) = {
    mavenBuild.setDirectory("/out")
    new MavenProject(new Model { setBuild(mavenBuild) }) {
      setFile(new File("/xyz/pom.xml")) // sets basedir
    }
  }

  implicit def implicitStringToPath(file: String): Path = Paths.get(file)

  implicit def implicitStringToFile(file: String): File = implicitStringToPath(file).toFile

  implicit def implicitStringsToFiles(files: Seq[String]): JList[File] =
    files.map(implicitStringToFile).asJava

}
