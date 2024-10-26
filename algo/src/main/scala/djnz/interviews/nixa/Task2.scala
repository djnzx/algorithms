package djnz.interviews.nixa

object Task2 {
  def numberOfTokens(expLim: Int, commands: Array[Array[Int]]): Int = {

    /** Domain representation */
    sealed trait Command
    case class Create(tk: Int, time: Int) extends Command
    case class Refresh(tk: Int, time: Int) extends Command
    object Command {
      def parse(cmd: Array[Int]): Command = cmd match {
        case Array(0, tk, tm) => Create(tk, tm)
        case Array(1, tk, tm) => Refresh(tk, tm)
        case _                => sys.error("wrong data")
      }
    }

    /** State representation */
    type MII = Map[Int, Int]
    case class State(map: MII, max: Int) {
      private def withMax(mx: Int): State = copy(max = mx)
      private def withMap(mp: MII): State = copy(map = mp)

      def update(token: Int, time: Int): State =
        withMap(map + (token -> (time + expLim))).withMax(time)

      def refresh(token: Int, time: Int): State =
        map.get(token) match {
          case None                => withMax(time)                      // if token isn't found -> just update the time only
          case Some(x) if time < x => update(token, time)                // if found and can be updated -> update map + time
          case Some(_)             => withMap(map - token).withMax(time) // else -> remove token and update the time
        }
    }

    /** fold initial value */
    val s0 = State(Map.empty, 0)

    /** fold function */
    def process(mm: State, cmd: Command): State = cmd match {
      case Create(tk, time)  => mm.update(tk, time)
      case Refresh(tk, time) => mm.refresh(tk, time)
    }

    /** The task */
    commands
      .map(Command.parse)
      .foldLeft(s0)(process) match {
      case State(map, last) => map.count { case (_, exp) => exp >= last }
    }

  }

}
