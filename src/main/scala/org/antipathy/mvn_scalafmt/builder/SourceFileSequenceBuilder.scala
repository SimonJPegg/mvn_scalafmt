package org.antipathy.mvn_scalafmt.builder

import java.io.File
import java.nio.file.{Files, Paths}

import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.Log

import scala.collection.JavaConverters._

/**
  * Class for building a collection of source files in the maven project
  * @param log The maven logger
  */
class SourceFileSequenceBuilder(log: Log) extends Builder[Seq[Any], Seq[File]] {

  /**
    * Build a collection of all source files in the project
    *
    * @param paths The paths to build from
    * @return The source files in the project
    */
  override def build(paths: Seq[Any]): Seq[File] =
    if (paths == null) {
      log.error("Could not locate any scala sources to format")
      Seq.empty[File]
    } else {
      val files = paths.map(_.asInstanceOf[File].getCanonicalPath).flatMap { p =>
        if (Files.exists(Paths.get(p))) {
          Some(new File(p))
        } else {
          log.error(s"Could not locate Scala source at $p")
          None
        }
      }

      files.flatMap(file => FileUtils.listFiles(file, Array("scala", "sc", "sbt"), true).asScala)
    }
}
