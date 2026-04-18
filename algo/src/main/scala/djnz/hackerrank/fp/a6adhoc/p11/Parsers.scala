package djnz.hackerrank.fp.a6adhoc.p11

object Parsers {

  trait ParsersBase[P[+_]] {
    import scala.util.matching.Regex
    def run[A](p: P[A])(input: String): Either[ParseError, A]
    def succeed[A](a: A): P[A]
    def fail[A](msg: String): P[A]
    implicit def string(s: String): P[String]
    def or[A](p1: P[A], p2: => P[A]): P[A]
    def flatMap[A, B](pa: P[A])(g: A => P[B]): P[B]
    implicit def regex(r: Regex): P[String]
    def label[A](msg: String)(p: P[A]): P[A]
    def attempt[A](p: P[A]): P[A]
    def slice[A](p: P[A]): P[String]
  }

  case class Location(input: String, offset: Int = 0) {
    lazy val line: Int = input.slice(0, offset + 1).count(_ == '\n') + 1
    lazy val col: Int = input.slice(0, offset + 1).lastIndexOf('\n') match {
      case -1        => offset + 1
      case lineStart => offset - lineStart
    }

    def toError(msg: String): ParseError = ParseError(List((this, msg)))
    def advanceBy(n: Int): Location = copy(offset = offset + n)
    def currentLine: String =
      if (input.length > 1) input.linesIterator.drop(line - 1).next
      else ""

    def columnCaret: String = (" " * (col - 1)) + "^"
  }

  case class ParseError(stack: List[(Location, String)]) {
    def push(loc: Location, msg: String): ParseError = copy(stack = (loc, msg) :: stack)
    def label[A](s: String): ParseError = ParseError(latestLoc.map(loc => (loc, s)).toList)
    def latestLoc: Option[Location] = latest map { case (l, _) => l }
    def latest: Option[(Location, String)] = stack.lastOption

    override def toString =
      if (stack.isEmpty) "no errors"
      else {
        val collapsed = collapseStack(stack)
        val context =
          collapsed.lastOption.map("\n\n" + _._1.currentLine).getOrElse("") +
            collapsed.lastOption.map("\n" + _._1.columnCaret).getOrElse("")

        collapsed.map { case (loc, msg) => loc.line.toString + "." + loc.col + " " + msg }.mkString("\n") +
          context
      }

    def collapseStack(s: List[(Location, String)]): List[(Location, String)] =
      s.groupBy(_._1)
        .view
        .mapValues(_.map(_._2).mkString("; "))
        .toList
        .sortBy(_._1.offset)

    def formatLoc(l: Location): String = s"${l.line}.${l.col}"
  }

  sealed trait Result[+A] {
    def extract: Either[ParseError, A] = this match {
      case Failure(e, _) => Left(e)
      case Success(a, _) => Right(a)
    }
    def extractLen: Either[ParseError, (A, Int)] = this match {
      case Failure(e, _) => Left(e)
      case Success(a, l) => Right((a, l))
    }
    def uncommit: Result[A] = this match {
      case Failure(e, true) => Failure(e, isCommitted = false)
      case _                => this
    }
    def addCommit(isCommitted: Boolean): Result[A] = this match {
      case Failure(e, c) => Failure(e, c || isCommitted)
      case _             => this
    }
    def mapError(f: ParseError => ParseError): Result[A] = this match {
      case Failure(e, c) => Failure(f(e), c)
      case _             => this
    }
    def advanceSuccess(n: Int): Result[A] = this match {
      case Success(a, m) => Success(a, n + m)
      case _             => this
    }
  }
  case class Success[+A](get: A, pos: Int) extends Result[A]
  case class Failure(get: ParseError, isCommitted: Boolean) extends Result[Nothing]

  case class ParseState(loc: Location) {
    def advanceBy(numChars: Int) = copy(loc = loc.copy(offset = loc.offset + numChars))
    def input = loc.input.substring(loc.offset)
    def slice(n: Int) = loc.input.substring(loc.offset, loc.offset + n)
  }

  type Parser[+A] = ParseState => Result[A]

  trait Parsers[P[+_]] extends ParsersBase[P] { self =>

    /** syntax */
    implicit def syntaxForParser[A](p: P[A]): ParserOps[A] = ParserOps[A](p)
    implicit def asStringParser[A](a: A)(implicit f: A => P[String]): ParserOps[String] = ParserOps(f(a))
    def char(c: Char): P[Char] = string(c.toString) map { _.charAt(0) }
    def many[A](p: P[A]): P[List[A]] = map2(p, many(p))(_ :: _) | succeed(Nil)
    def listOfN[A](n: Int, p: P[A]): P[List[A]] =
      if (n <= 0) succeed(Nil)
      else map2(p, listOfN(n - 1, p))(_ :: _)
    def product[A, B](pa: P[A], pb: => P[B]): P[(A, B)] = for {
      a <- pa
      b <- pb
    } yield (a, b)
    def map2[A, B, C](pa: P[A], pb: => P[B])(f: (A, B) => C): P[C] = for {
      a <- pa
      b <- pb
    } yield f(a, b)
    def map[A, B](pa: P[A])(f: A => B): P[B] = flatMap(pa)(f andThen succeed)
    def skipL[B](p: P[Any], p2: => P[B]): P[B] = map2(slice(p), p2)((_, b) => b)
    def skipR[A](p: P[A], p2: => P[Any]): P[A] = map2(p, slice(p2))((a, _) => a)
    def opt[A](p: P[A]): P[Option[A]] = p.map(Some(_)) | succeed(None)
    def nonOpt[A](p: P[Option[A]]): P[A] = p.flatMap {
      case Some(a) => succeed(a)
      case None    => fail("falling back to none")
    }
    def whitespace: P[String] = "\\s*".r
    val identifier: P[String] = "[a-z][a-zA-Z0-9_]*".r
    def digits: P[String] = "\\d+".r
    def number: P[Int] = digits map { _.toInt } label "integer w/o sign literal"
    def doubleString: P[String] = token("[-+]?([0-9]*\\.)?[0-9]+([eE][-+]?[0-9]+)?".r)
    def double: P[Double] = doubleString map { _.toDouble } label "double literal"
    def token[A](p: P[A]): P[A] = attempt(p) <* whitespace
    def surround[A](start: P[Any], stop: P[Any])(p: => P[A]) = start *> p <* stop
    def eof: P[String] = regex("\\z".r).label("unexpected trailing characters")
    def root[A](p: P[A]): P[A] = p <* eof

    /** syntax */
    case class ParserOps[A](p: P[A]) {
      def |[B >: A](p2: => P[B]): P[B] = self.or(p, p2)
      def map[B](f: A => B): P[B] = self.map(p)(f)
      def as[B](b: B): P[B] = self.succeed(b)
      def many: P[List[A]] = self.many(p)
      def slice: P[String] = self.slice(p)
      def product[B](pb: => P[B]): P[(A, B)] = self.product(p, pb)
      def **[B](pb: => P[B]): P[(A, B)] = self.product(p, pb)
      def flatMap[B](f: A => P[B]): P[B] = self.flatMap(p)(f)
      def label(msg: String): P[A] = self.label(msg)(p)
      def *>[B](p2: => P[B]): P[B] = self.skipL(p, p2)
      def <*(p2: => P[Any]): P[A] = self.skipR(p, p2)
      def opt: P[Option[A]] = self.opt(p)
      def nonOpt[AA](implicit ev: A <:< Option[AA]): P[AA] = self.nonOpt(p.map(ev))
      def surround(start: P[Any], stop: P[Any]): P[A] = self.surround(start, stop)(p)
    }
  }

  object ParserImpl extends Parsers[Parser] {
    import scala.util.matching.Regex

    def run[A](p: Parser[A])(s: String): Either[ParseError, A] = p(ParseState(Location(s))).extract

    def runLen[A](p: Parser[A])(s: String): Either[ParseError, (A, Int)] =
      p(ParseState(Location(s))).extractLen

    def succeed[A](a: A): Parser[A] = _ => Success(a, 0)

    def fail[A](msg: String): Parser[A] = s => Failure(s.loc.toError(msg), true)

    def firstNonMatchingIndex(s0: String, pat: String, s0_off: Int): Int = {
      var i = 0
      while (s0_off + i < s0.length && i < pat.length) {
        if (s0.charAt(s0_off + i) != pat.charAt(i)) return i
        i += 1
      }
      if (s0.length - s0_off >= pat.length) -1
      else s0.length - s0_off
    }

    implicit def string(w: String): Parser[String] = s =>
      firstNonMatchingIndex(s.loc.input, w, s.loc.offset) match {
        case -1 => Success(w, w.length) // they matched
        case i  => Failure(s.loc.advanceBy(i).toError(s"'$w'"), i != 0)
      }

    def or[A](p: Parser[A], p2: => Parser[A]): Parser[A] = s =>
      p(s) match {
        case Failure(_, false) => p2(s)
        case r                 => r
      }

    def flatMap[A, B](pa: Parser[A])(g: A => Parser[B]): Parser[B] = s =>
      pa(s) match {
        case Success(a, pos)   =>
          val pb: Parser[B] = g(a)
          val rb1 = pb(s.advanceBy(pos))
          val rb2 = rb1.addCommit(pos != 0)
          val rb3 = rb2.advanceSuccess(pos)
          rb3
        case f @ Failure(_, _) => f
      }

    def regex(r: Regex): Parser[String] = s =>
      r.findPrefixOf(s.input) match {
        case Some(m) => Success(m, m.length)
        case None    => Failure(s.loc.toError(s"regex $r"), isCommitted = false)
      }

    def scope[A](msg: String)(p: Parser[A]): Parser[A] = s => p(s).mapError(_.push(s.loc, msg))

    def label[A](msg: String)(p: Parser[A]): Parser[A] = s => p(s).mapError(_.label(msg))

    def attempt[A](p: Parser[A]): Parser[A] = s => p(s).uncommit

    def slice[A](p: Parser[A]): Parser[String] = s =>
      p(s) match {
        case Success(_, n)     => Success(s.slice(n), n)
        case f @ Failure(_, _) => f
      }
  }

}
