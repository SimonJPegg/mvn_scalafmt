package org.antipathy.mvn_scalafmt

import java.io.File
import java.util.{List => JList}

import org.antipathy.mvn_scalafmt.builder.{Builder, LocalConfigBuilder, SourceFileSequenceBuilder}
import org.antipathy.mvn_scalafmt.format.{Formatter, SourceFileFormatter}
import org.antipathy.mvn_scalafmt.io.{FormattedFilesWriter, Writer}
import org.antipathy.mvn_scalafmt.logging.MavenLogReporter
import org.antipathy.mvn_scalafmt.model.{FormatResult, Summary}
import org.apache.maven.plugin.logging.Log
import org.scalafmt.interfaces.Scalafmt

import scala.collection.JavaConverters._

/**
  * class to format scala source files using the Scalafmt library
  */
class ScalaFormatter(
    sourceBuilder: Builder[Seq[Any], Seq[File]],
    fileFormatter: Formatter[File, FormatResult],
    writer: Writer[Seq[FormatResult], Summary]
) extends Formatter[JList[Any], Summary] {

  /**
    * Format the files in the passed in source directories
    * @param sourceDirectories The source directories to format
    * @return A summary of what was done
    */
  override def format(sourceDirectories: JList[Any]): Summary = {
    val sources = sourceBuilder.build(sourceDirectories.asScala)
    val formattedSources = sources.map(fileFormatter.format)
    writer.write(formattedSources)
  }
}

object ScalaFormatter {

  def apply(configLocation: String, log: Log, respectVersion: Boolean, testOnly: Boolean): ScalaFormatter = {
    val config = LocalConfigBuilder(log).build(configLocation)
    val sourceBuilder = new SourceFileSequenceBuilder(log)
    val scalafmt = Scalafmt
      .create(this.getClass.getClassLoader)
      .withReporter(new MavenLogReporter(log))
      .withRespectVersion(respectVersion)
    val sourceFormatter = new SourceFileFormatter(config, scalafmt, log)
    val fileWriter = if (testOnly) {
      import org.antipathy.mvn_scalafmt.io.TestResultLogWriter
      new TestResultLogWriter(log)
    } else {
      new FormattedFilesWriter(log)
    }
    new ScalaFormatter(sourceBuilder, sourceFormatter, fileWriter)
  }
}
