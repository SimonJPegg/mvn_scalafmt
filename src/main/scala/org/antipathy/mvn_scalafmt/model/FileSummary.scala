package org.antipathy.mvn_scalafmt.model

// $COVERAGE-OFF$
/** Class representing the result of a format on a file
  * @param name The name of the file
  * @param details a summary of the file
  */
case class FileSummary(name: String, details: String) {

  override def toString: String = s"$details: $name"
}
// $COVERAGE-ON$
