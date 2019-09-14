package org.antipathy.mvn_scalafmt.validation

/**
  * Base trait for validation
  *
  * @tparam I The input type
  * @tparam O The output type
  */
trait Validator[I, O] {

  /**
    * Validate the passed in input
    * @param i The input to validate
    * @return The validated output
    */
  def validate(i: I): O
}
