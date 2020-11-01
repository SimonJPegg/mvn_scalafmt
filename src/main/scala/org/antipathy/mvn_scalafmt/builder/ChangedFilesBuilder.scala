package org.antipathy.mvn_scalafmt.builder

import java.io.File
import java.nio.file.Paths

import org.apache.maven.plugin.logging.Log

import scala.sys.process.{Process, ProcessLogger}
import scala.util.{Failure, Success, Try}

/** Class for building a list of files that have changed from a specified git branch
  * @param log The maven logger
  * @param diff Should only changed files be returned
  * @param branch the git branch to compare against
  * @param changeFunction Function to identify changed files
  */
class ChangedFilesBuilder(log: Log, diff: Boolean, branch: String, changeFunction: () => Seq[File])
    extends Builder[Seq[File], Seq[File]] {

  private def isSupportedFile(f: File): Boolean =
    Seq(".scala", ".sc", ".sbt")
      .exists(f.getName.endsWith)

  /** Build a list of files that have changed in git from the specified input
    *
    * @param input The input to build from
    * @return A list of changed files.
    */
  override def build(input: Seq[File]): Seq[File] =
    if (diff) {
      log.info(s"Checking for files changed from $branch")
      Try {
        val changedFiles = changeFunction()
        log.info(changedFiles.mkString(s"Changed from $branch:\n", "\n", ""))
        changedFiles.filter(isSupportedFile)
      } match {
        case Success(value) => value
        case Failure(e) =>
          log.error("Could not obtain list of changed files", e)
          throw e
      }
    } else
      input

}

// $COVERAGE-OFF$
object ChangedFilesBuilder {

  def apply(log: Log, diff: Boolean, branch: String, workingDirectory: File): ChangedFilesBuilder = {
    val logger: ProcessLogger = ProcessLogger(_ => (), err => log.error(err))

    def run(cmd: String) = Process(cmd, workingDirectory).!!(logger).trim

    val prefix = ": "
    val actualBranch =
      if (!branch.startsWith(prefix)) branch
      else run(branch.substring(prefix.length))

    def processFunction(): Seq[File] = {
      val diffOutput    = run(s"git diff --name-only --diff-filter=d $actualBranch")
      val gitRootOutput = run("git rev-parse --show-toplevel")
      val gitRootPath   = Paths.get(gitRootOutput)
      diffOutput.linesIterator
        .map(gitRootPath.resolve)
        .map(_.toFile)
        .toSeq
    }

    new ChangedFilesBuilder(log, diff, actualBranch, processFunction _)
  }
}
// $COVERAGE-ON$
