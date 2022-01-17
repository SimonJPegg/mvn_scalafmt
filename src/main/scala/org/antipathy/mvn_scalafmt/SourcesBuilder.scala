package org.antipathy.mvn_scalafmt

import java.io.File
import java.util.{List => JList}

import scala.jdk.CollectionConverters._

import org.apache.maven.project.MavenProject

/** Class for building a set of source dirs in the maven project */
class SourcesBuilder(project: MavenProject) {

  private val sources = Set.newBuilder[File]
  private val build   = project.getBuild()
  private val outpath = getCanonicalFile(build.getDirectory()).toPath()

  private[mvn_scalafmt] def resultSet(): Set[File] =
    try sources.result()
    finally sources.clear()

  def result(): JList[File] = resultSet().toList.asJava

  def addMain(dirs: JList[File]): Unit =
    if (!add(dirs)) {
      appendPrimary(build.getSourceDirectory())
      appendAlternative(project.getCompileSourceRoots())
    }

  def addTest(dirs: JList[File]): Unit =
    if (!add(dirs)) {
      appendPrimary(build.getTestSourceDirectory())
      appendAlternative(project.getTestCompileSourceRoots())
    }

  private def add(dirs: JList[File]): Boolean = {
    val ok = dirs != null && !dirs.isEmpty
    if (ok) sources ++= dirs.asScala.map(getCanonicalFile)
    ok
  }

  private def getCanonicalFile(dir: String): File =
    project.getBasedir().toPath.resolve(dir).toFile().getCanonicalFile()

  private def getCanonicalFile(dir: File): File =
    project.getBasedir().toPath.resolve(dir.toPath).toFile().getCanonicalFile()

  private def appendPrimary(dir: String): Unit =
    sources += getCanonicalFile(dir + "/../scala")

  private def appendAlternative(altDirs: JList[String]): Unit =
    sources ++= altDirs.asScala.map(getCanonicalFile).filter(!_.toPath().startsWith(outpath))

}
