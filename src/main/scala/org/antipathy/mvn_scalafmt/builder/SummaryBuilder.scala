package org.antipathy.mvn_scalafmt.builder

import org.antipathy.mvn_scalafmt.model.FormatResult

class SummaryBuilder extends Builder[Seq[FormatResult], Int => String] {

  /**
    * Build the required output from the specified input
    *
    * @param input The input to build from
    * @return The built output
    */
  override def build(input: Seq[FormatResult]): Int => String = (projectFileCount: Int) => {

    val formattedFileCount = input.length
    val formattedFiles = input.map(_.sourceFile.getCanonicalPath).mkString(System.lineSeparator())
    s""" Scalafmt Result 
       |$formattedFileCount of $projectFileCount files formatted
       |${if (input.nonEmpty) {
         s"""formatted files:
         |$formattedFiles"""
       }}
     """.stripMargin
  }
}
