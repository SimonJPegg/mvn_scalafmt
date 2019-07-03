package org.antipathy.mvn_scalafmt.io

/**
  * Base trait for writing
  * @tparam T The type to write
  */
trait Writer[T] {

  /**
    * Write the passed in input
    * @param input The input to write
    */
  def write(input: T): Unit
}
