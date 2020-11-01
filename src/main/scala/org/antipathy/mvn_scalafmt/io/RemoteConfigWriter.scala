package org.antipathy.mvn_scalafmt.io

import org.antipathy.mvn_scalafmt.model.RemoteConfig
import java.io.File
import java.nio.charset.StandardCharsets

import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log
import java.nio.file.{Files, Path}

/** Class for writing a remote config to a local path
  * @param log The maven logger
  */
class RemoteConfigWriter(log: Log) extends Writer[RemoteConfig, Path] {

  /** Write the passed in remote config to a local file
    *
    * @param input The input to write
    */
  override def write(input: RemoteConfig): Path = {

    log.info(s"Writing remote config to ${input.location.toAbsolutePath}")

    if (Files.exists(input.location))
      Files.delete(input.location)

    val newConfig = new File(input.location.toAbsolutePath.toString)
    FileUtils.writeStringToFile(
      newConfig,
      input.contents,
      StandardCharsets.UTF_8
    )
    newConfig.toPath
  }
}
