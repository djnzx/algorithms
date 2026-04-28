package djnz.hackerrank.fp.a6adhoc.p11

import InputSyntax.Id
import cats.implicits.catsSyntaxEitherId
import org.typelevel.literally.Literally
import scala.language.experimental.macros

object IdLit extends Literally[Id] {

  def validate(c: IdLit.Context)(s: String): Either[String, c.Expr[Id]] = {
    import c.universe._

    val exprId: Expr[Id] = c.Expr[Id](q"_root_.djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.Id($s)")

    exprId.asRight
  }

  def make(c: Context)(args: c.Expr[Any]*): c.Expr[Id] = this.apply(c)(args: _*)

  implicit class id(val sc: StringContext) extends AnyVal {
    def id(args: Any*): Id = macro IdLit.make
  }

}

