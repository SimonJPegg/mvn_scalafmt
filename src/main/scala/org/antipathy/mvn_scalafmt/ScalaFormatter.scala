package org.antipathy.mvn_scalafmt

import java.io.File
import java.util.{List => JList}

import org.antipathy.mvn_scalafmt.builder.{Builder, SourceFileSequenceBuilder, SummaryBuilder}
import org.antipathy.mvn_scalafmt.filter.{Filter, UnchangedSourceFilter}
import org.antipathy.mvn_scalafmt.format.{Formatter, SourceFileFormatter}
import org.antipathy.mvn_scalafmt.io.{FormattedFileWriter, Writer}
import org.antipathy.mvn_scalafmt.logging.MavenLogReporter
import org.antipathy.mvn_scalafmt.model.FormatResult
import org.antipathy.mvn_scalafmt.validation.ConfigFileValidator
import org.apache.maven.plugin.logging.Log
import org.scalafmt.interfaces.Scalafmt

import scala.collection.JavaConverters._

/**
  * class to format scala source files using the Scalafmt library
  */
class ScalaFormatter(
    sourceBuilder: Builder[Seq[Any], Seq[File]],
    fileFormatter: Formatter[File, FormatResult],
    unchangedSourceFilter: Filter[FormatResult, Boolean],
    writer: Writer[FormatResult],
    summaryBuilder: Builder[Seq[FormatResult], Int => String]
) extends Formatter[JList[Any], String] {

  /**
    * Format the files in the passed in source directories
    * @param sourceDirectories The source directories to format
    * @return A summary of what was done
    */
  override def format(sourceDirectories: JList[Any]): String = {
    val sources = sourceBuilder.build(sourceDirectories.asScala)
    val formattedSources = sources.map(fileFormatter.format)
    val sourcesToWrite = formattedSources.filter(unchangedSourceFilter.filter)
    sourcesToWrite.foreach(writer.write)
    summaryBuilder.build(sourcesToWrite)(sources.length)
  }
}

object ScalaFormatter {

  def apply(configLocation: String, log: Log, respectVersion: Boolean): ScalaFormatter = {
    val config = new ConfigFileValidator(log).validate(configLocation)
    val sourceBuilder = new SourceFileSequenceBuilder(log)
    val scalafmt = Scalafmt
      .create(this.getClass.getClassLoader)
      .withReporter(new MavenLogReporter(log))
      .withRespectVersion(respectVersion)
    val sourceFormatter = new SourceFileFormatter(config, scalafmt, log)
    val unchangedSourceFilter = new UnchangedSourceFilter(log)
    val fileWriter = new FormattedFileWriter(log)
    val summaryBuilder = new SummaryBuilder
    new ScalaFormatter(sourceBuilder, sourceFormatter, unchangedSourceFilter, fileWriter, summaryBuilder)
  }
}
