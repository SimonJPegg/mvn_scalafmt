package org.antipathy.mvn_scalafmt.builder

import java.io.File
import org.apache.maven.plugin.logging.Log
import scala.util.control.NonFatal
import scala.sys.process.ProcessLogger

/**
  * Class for building a list of files that have changed from a specified git branch
  * @param log The maven logger
  * @param diff Should only changed files be returned
  * @param branch the git branch to compare against
  * @param changeFunction Function to identify changed files
  */
class ChangedFilesBuilder(log: Log, diff: Boolean, branch: String, changeFunction: () => String)
    extends Builder[Seq[File], Seq[File]] {

  /**
    * Build a list of files that have changed in git from the specified input
    *
    * @param input The input to build from
    * @return A list of changed files.
    */
  override def build(input: Seq[File]): Seq[File] =
    if (diff) {
      log.info(s"Checking for files changed from $branch")
      try {
        val names: Seq[String] =
          Predef.augmentString(changeFunction()).linesIterator.toSeq
        val changedFiles = names.map(new File(_).getAbsolutePath)
        changedFiles.foreach { f =>
          log.info(s"Changed from $branch: $f")
        }
        changedFiles.map(new File(_)).filter { f =>
          val path = f.getAbsolutePath
          path.endsWith("scala") ||
          path.endsWith("sc") ||
          path.endsWith("sbt")

        }
      } catch {
        case NonFatal(e) =>
          log.error("Could not obtain list of changed files", e)
          throw e
      }
    } else {
      input
    }

}

object ChangedFilesBuilder {

  def apply(log: Log, diff: Boolean, branch: String, workingDirectory: File): ChangedFilesBuilder = {

    def command(branch: String): String = s"git diff --name-only --diff-filter=d $branch"
    val logger: ProcessLogger           = sys.process.ProcessLogger(_ => (), err => log.error(err))
    def processFunction: () => String = () => {
      sys.process.Process(command(branch), workingDirectory).!!(logger)
    }
    new ChangedFilesBuilder(log, diff, branch, processFunction)
  }
}
