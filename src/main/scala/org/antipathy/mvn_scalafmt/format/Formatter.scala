package org.antipathy.mvn_scalafmt.format

/**
  * Base trait for formatting
  * @tparam I Input type
  * @tparam O Output type
  */
trait Formatter[I, O] {

  /**
    * Format the passed in input
    * @param input The input to format
    * @return Formatted output
    */
  def format(input: I): O
}
