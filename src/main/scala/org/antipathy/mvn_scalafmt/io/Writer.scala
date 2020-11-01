package org.antipathy.mvn_scalafmt.io

// $COVERAGE-OFF$
/** Base trait for writing
  * @tparam I The type to write
  * @tparam O The result of the write operation
  */
trait Writer[I, O] {

  /** Write the passed in input
    * @param input The input to write
    */
  def write(input: I): O
}
// $COVERAGE-ON$
