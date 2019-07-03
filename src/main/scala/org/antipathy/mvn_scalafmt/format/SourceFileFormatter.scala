package org.antipathy.mvn_scalafmt.format

import java.io.File
import java.nio.file.Path

import org.antipathy.mvn_scalafmt.model.FormatResult
import org.apache.maven.plugin.logging.Log
import org.scalafmt.interfaces.Scalafmt

/**
  * Class for formatting source files
  * @param config the Scalafmt config location
  * @param inner The inner formatter
  * @param log The maven logger
  */
class SourceFileFormatter(
    config: Path,
    inner: Scalafmt,
    log: Log
) extends Formatter[File, FormatResult] {

  /**
    * Format the passed in input
    * @param sourceFile The input to format
    * @return Formatted output
    */
  override def format(sourceFile: File): FormatResult = {
    log.debug(s"Parsing file: ${sourceFile.getCanonicalPath}")
    val unformattedSource = scala.io.Source.fromFile(sourceFile).getLines().mkString(System.lineSeparator())
    FormatResult(sourceFile, unformattedSource, inner.format(config, sourceFile.toPath, unformattedSource))
  }
}
