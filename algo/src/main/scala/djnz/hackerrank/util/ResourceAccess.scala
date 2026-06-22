package djnz.hackerrank.util

import scala.util.Try
import scala.util.Using

trait ResourceAccess extends Fail { ra =>

  trait HasFileReader {
    def file(name: String): List[String]
  }

  def file(fileName: String) =
    Try(getClass.getClassLoader.getResource(fileName))
      .map(_.toURI)
      .flatMap(jf => Using(scala.io.Source.fromFile(jf))(_.getLines().toList))
      .fold(_ => failX(s"file $fileName should exist"), identity)

  def folder(prefix: String): HasFileReader = new HasFileReader {

    override def file(name: String) =
      ra.file(s"$prefix/$name")

  }

}
