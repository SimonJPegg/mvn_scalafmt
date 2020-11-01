package org.antipathy.mvn_scalafmt.builder

import java.io.File
import java.nio.file.{Files, Paths}

import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log

import scala.jdk.CollectionConverters._

/** Class for building a collection of source files in the maven project
  * @param log The maven logger
  */
class SourceFileSequenceBuilder(log: Log) extends Builder[Seq[File], Seq[File]] {

  /** Build a collection of all source files in the project
    *
    * @param paths The paths to build from
    * @return The source files in the project
    */
  override def build(paths: Seq[File]): Seq[File] =
    if (paths == null) {
      log.warn("Could not locate any scala sources to format")
      Seq.empty[File]
    } else {
      val files = paths.map(_.getCanonicalPath).flatMap { p =>
        if (Files.exists(Paths.get(p)))
          Some(new File(p))
        else {
          log.warn(s"Could not locate Scala source at $p")
          None
        }
      }
      files.flatMap(file => FileUtils.listFiles(file, Array("scala", "sc", "sbt"), true).asScala)
    }
}
