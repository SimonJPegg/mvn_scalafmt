package org.antipathy.mvn_scalafmt.filter

/**
  * Base trait for filters
  * @tparam I The input type
  * @tparam O The output type
  */
trait Filter[I, O] {

  /**
    * Filter the passed in input
    * @param input the input to filter
    * @return The filtered output
    */
  def filter(input: I): O
}
