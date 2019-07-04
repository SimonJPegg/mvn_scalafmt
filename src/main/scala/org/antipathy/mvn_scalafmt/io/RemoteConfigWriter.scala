package org.antipathy.mvn_scalafmt.io

import org.antipathy.mvn_scalafmt.model.RemoteConfig
import java.io.File
import java.nio.charset.StandardCharsets
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log
import java.nio.file.Files

/**
  * Class for writing a remote config to a local path
  * @param log The maven logger
  */
class RemoteConfigWriter(log: Log) extends Writer[RemoteConfig] {

  /**
    * Write the passed in remote config to a local file
    *
    * @param input The input to write
    */
  override def write(input: RemoteConfig): Unit = {

    log.info(s"Writing remote config to ${input.location.toAbsolutePath}")

    if (Files.exists(input.location)) {
      Files.delete(input.location)
    }

    FileUtils.writeStringToFile(
      new File(input.location.toAbsolutePath.toString),
      input.contents,
      StandardCharsets.UTF_8
    )
  }
}
