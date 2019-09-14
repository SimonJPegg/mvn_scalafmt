package org.antipathy.mvn_scalafmt.model

import java.io.File

/**
  * Class used to contain a format result
  *
  * @param sourceFile The source file
  * @param originalSource The string value of the unformatted file
  * @param formattedSource The new formatted source
  */
case class FormatResult(sourceFile: File,
                        originalSource: String,
                        formattedSource: String)
