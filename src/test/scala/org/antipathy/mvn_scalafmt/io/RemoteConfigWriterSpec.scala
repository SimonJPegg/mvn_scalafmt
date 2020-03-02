package org.antipathy.mvn_scalafmt.io

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import org.antipathy.mvn_scalafmt.model.RemoteConfig
import org.apache.commons.io.FileUtils
import org.apache.maven.plugin.logging.SystemStreamLog
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class RemoteConfigWriterSpec extends FlatSpec with GivenWhenThen with Matchers {

  behavior of "RemoteConfigWriter"

  it should "Write a config to a local path" in {

    val localPath = s"${System.getProperty("java.io.tmpdir")}${File.separator}.scalafmt.conf"
    val contents  = """version = "1.5.1"
                     |maxColumn = 120
                     |align = false
                     |rewrite.rules = [SortImports]
                     |danglingParentheses = true
                     |importSelectors = singleLine
                     |binPack.parentConstructors = true
                     |includeCurlyBraceInSelectChains = false""".stripMargin
    val writer    = new RemoteConfigWriter(new SystemStreamLog)
    val input     = RemoteConfig(contents, Paths.get(localPath))

    writer.write(input)

    new String(Files.readAllBytes(new File(localPath).toPath))
    Files.delete(input.location)
  }

  it should "Overwrite a config in a local path" in {

    val localPath = s"${System.getProperty("java.io.tmpdir")}${File.separator}.scalafmt2.conf"

    val contents    = """version = "1.5.1"
                     |maxColumn = 120
                     |align = false
                     |rewrite.rules = [SortImports]
                     |danglingParentheses = true
                     |importSelectors = singleLine
                     |binPack.parentConstructors = true
                     |includeCurlyBraceInSelectChains = false""".stripMargin
    val oldContents = "SomeOldConfig"

    val writer = new RemoteConfigWriter(new SystemStreamLog)
    val input  = RemoteConfig(contents, Paths.get(localPath))

    FileUtils.writeStringToFile(new File(localPath), oldContents, StandardCharsets.UTF_8)
    new String(Files.readAllBytes(new File(localPath).toPath)) should be(oldContents)

    writer.write(input)

    new String(Files.readAllBytes(new File(localPath).toPath)) should be(contents)
    Files.delete(input.location)
  }

}
