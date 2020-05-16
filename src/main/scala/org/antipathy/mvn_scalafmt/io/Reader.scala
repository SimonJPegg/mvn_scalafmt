package org.antipathy.mvn_scalafmt.io

// $COVERAGE-OFF$
/**
  * Base trait for reading
  * @tparam I The input type
  * @tparam O The output type
  */
trait Reader[I, O] {

  /**
    * Read an object from the specified location
    * @param location The location to read from
    * @return The object at the location
    */
  def read(location: I): O
}
// $COVERAGE-ON$