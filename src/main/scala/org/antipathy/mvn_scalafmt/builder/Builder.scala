package org.antipathy.mvn_scalafmt.builder

// $COVERAGE-OFF$
/**
  * Base trait for building objects
  * @tparam I The input type
  * @tparam O The output type
  */
trait Builder[I, O] {

  /**
    * Build the required output from the specified input
    * @param input The input to build from
    * @return The built output
    */
  def build(input: I): O
}
// $COVERAGE-ON$