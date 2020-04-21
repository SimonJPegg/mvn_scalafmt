package org.antipathy.mvn_scalafmt

import java.io.File
import java.util.{List => JList}

import org.antipathy.mvn_scalafmt.builder.{Builder, LocalConfigBuilder, SourceFileSequenceBuilder}
import org.antipathy.mvn_scalafmt.format.{Formatter, SourceFileFormatter}
import org.antipathy.mvn_scalafmt.io.{FormattedFilesWriter, TestResultLogWriter, Writer}
import org.antipathy.mvn_scalafmt.logging.MavenLogReporter
import org.antipathy.mvn_scalafmt.model.{FormatResult, Summary}
import org.apache.maven.plugin.logging.Log
import org.scalafmt.interfaces.Scalafmt
import org.antipathy.mvn_scalafmt.builder.ChangedFilesBuilder

import scala.collection.JavaConverters._

/**
  * class to format scala source files using the Scalafmt library
  */
class ScalaFormatter(
  sourceBuilder: Builder[Seq[File], Seq[File]],
  changedFilesBuilder: Builder[Seq[File], Seq[File]],
  fileFormatter: Formatter[File, FormatResult],
  writer: Writer[Seq[FormatResult], Summary]
) extends Formatter[JList[File], Summary] {

  /**
    * Format the files in the passed in source directories
    * @param sourceDirectories The source directories to format
    * @return A summary of what was done
    */
  override def format(sourceDirectories: JList[File]): Summary = {
    val sources          = sourceBuilder.build(sourceDirectories.asScala.toSeq)
    val sourcesToFormat  = changedFilesBuilder.build(sources)
    val formattedSources = sourcesToFormat.map(fileFormatter.format)
    writer.write(formattedSources)
  }
}

object ScalaFormatter {

  /**
    *  Create a new ScalaFormatter instance
    * @param configLocation The location of the scalafmt.conf
    * @param log The maven logger
    * @param respectVersion should we respect the version in the scalafmt.conf
    * @param testOnly should files be reformatted
    * @param onlyChangedFiles Should only changed files be formatted
    * @param branch The branch to compare against for changed files
    * @param workingDirectory The project working directory
    * @param mavenRepositoryUrls The maven repositories to be used to dynamically load scalafmt, empty if maven central
    *                            should be used.
    * @return a new ScalaFormatter instance
    */
  def apply(
             configLocation: String,
             log: Log,
             respectVersion: Boolean,
             testOnly: Boolean,
             onlyChangedFiles: Boolean,
             branch: String,
             workingDirectory: File,
             mavenRepositoryUrls: JList[String]
  ): ScalaFormatter = {
    val config              = LocalConfigBuilder(log).build(configLocation)
    val sourceBuilder       = new SourceFileSequenceBuilder(log)
    val changedFilesBuilder = ChangedFilesBuilder(log, onlyChangedFiles, branch, workingDirectory)

    val scalafmt = Scalafmt
      .create(this.getClass.getClassLoader)
      .withReporter(new MavenLogReporter(log))
      .withRespectVersion(respectVersion)
      .withMavenRepositories(mavenRepositoryUrls.asScala.toSeq: _*)

    val sourceFormatter = new SourceFileFormatter(config, scalafmt, log)

    val fileWriter = if (testOnly) {
      new TestResultLogWriter(log)
    } else {
      new FormattedFilesWriter(log)
    }
    new ScalaFormatter(sourceBuilder, changedFilesBuilder, sourceFormatter, fileWriter)
  }
}
