package org.antipathy.mvn_scalafmt

import java.io.File

import org.antipathy.mvn_scalafmt.builder.Builder
import org.antipathy.mvn_scalafmt.format.Formatter
import org.antipathy.mvn_scalafmt.io.Writer
import org.antipathy.mvn_scalafmt.model.{FormatResult, Summary}
import org.mockito.Mockito
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.jdk.CollectionConverters._

class ScalaFormatterSpec extends AnyFlatSpec with GivenWhenThen with Matchers {

  behavior of "ScalaFormatter"

  it should "format scala files" in {
    import org.mockito.ArgumentMatchers
    val sourceBuilder       = Mockito.mock(classOf[Builder[Seq[File], Seq[File]]])
    val changedFilesBuilder = Mockito.mock(classOf[Builder[Seq[File], Seq[File]]])
    val fileFormatter       = Mockito.mock(classOf[Formatter[File, FormatResult]])
    val writer              = Mockito.mock(classOf[Writer[Seq[FormatResult], Summary]])
    val formatter           = new ScalaFormatter(sourceBuilder, changedFilesBuilder, fileFormatter, writer)

    val input           = Seq(Mockito.mock(classOf[File])).asJava
    val sources         = Mockito.mock(classOf[Seq[File]])
    val sourceToFormat  = Mockito.mock(classOf[File])
    val sourcesToFormat = Seq(sourceToFormat)
    val formatResult    = Mockito.mock(classOf[FormatResult])
    val summary         = Mockito.mock(classOf[Summary])

    Mockito.when(sourceBuilder.build(ArgumentMatchers.any(classOf[Seq[File]]))).thenReturn(sources)
    Mockito.when(changedFilesBuilder.build(sources)).thenReturn(sourcesToFormat)
    Mockito.when(fileFormatter.format(sourceToFormat)).thenReturn(formatResult)
    Mockito.when(writer.write(Seq(formatResult))).thenReturn(summary)
    val result = formatter.format(input)
    result should be(summary)
  }

}
