package djnz.hackerrank.fp.a6adhoc.p11

/** https://www.hackerrank.com/challenges/elementary-watson/problem = 200 */
// pure Horn clause logic with explicit disequality constraints

object P11ElementaryWatson {

  import InputSyntax._
  import cats.implicits._

  ////////////////////  KNOWLEDGE STRUCTURE  //////////////////////////////////////////////////////

  case class Predicate(value: SimpleTerm)

  sealed trait Constraint
  case class Eq(left: SimpleTerm, right: SimpleTerm) extends Constraint
  case class Neq(left: SimpleTerm, right: SimpleTerm) extends Constraint

  sealed trait Goal
  case class GoalPredicate(value: Predicate) extends Goal
  case class GoalConstraint(value: Constraint) extends Goal

  sealed trait Clause {
    def head: Predicate
  }
  object Clause {
    case class Fact(head: Predicate) extends Clause
    case class Rule(head: Predicate, body: List[Goal]) extends Clause
  }

  object Predicate {
    case class Key(name: Id, arity: Int)
    object Key {
      def make(p: Predicate): Option[Key] = p.value match {
        case StName(name)            => Key(name, 0).some
        case StRelation(name, terms) => Key(name, terms.length).some
        case StVariable(_)           => None
      }
    }
  }

  case class Knowledge(
    clauses: Vector[Clause],
    byHead: Map[Predicate.Key, Vector[Clause]]
  ) {

    private def clausesFor(predicate: Predicate) =
      Predicate.Key.make(predicate) match {
        case Some(key) => byHead.getOrElse(key, Vector.empty)
        case None      => clauses // return all clauses if no id can be obtained
      }

    def addClause(clause: Clause): Knowledge =
      Predicate.Key.make(clause.head) match {
        case None      => this.copy(clauses = clauses :+ clause)
        case Some(key) =>
          val byHeadNew = byHead.updatedWith(key) {
            case None          => Vector(clause).some
            case Some(clauses) => (clauses :+ clause).some
          }
          Knowledge(clauses :+ clause, byHeadNew)
      }

    def query(goals: List[Goal]) = {
      val projected = Solver.solve(clausesFor, goals)
        .map(ss => Variables.projectGoals(goals, ss))
        .toVector // strict

      Option.unless(projected.isEmpty)(projected)
    }
  }
  object Knowledge {
    def initial: Knowledge = Knowledge(Vector.empty, Map.empty)
  }

  ////////////////////  Model.Query => List[Goal]  ////////////////////////////////////////////////

  object QueryCompiler {

    private def mkGoal(ct: ComplexTerm): Goal = ct match {
      case st: SimpleTerm               => GoalPredicate(Predicate(st))
      case EqualityAssertion(lt, rt)    => GoalConstraint(Eq(lt, rt))
      case NonEqualityAssertion(lt, rt) => GoalConstraint(Neq(lt, rt))
    }

    def ruleToClause(rule: InputSyntax.Rule): Clause = rule match {
      case Rule.Fact(st)                  => Clause.Fact(Predicate(st))
      case Rule.Complex(prem, conclusion) => Clause.Rule(Predicate(conclusion), prem.map(mkGoal))
    }

    def queryToGoals(query: InputSyntax.Query): List[Goal] =
      query.terms.map(mkGoal)

  }

  sealed trait RunError

  case class Substitution(bindings: Map[Id, SimpleTerm]) {

    // resolve variable to terminal term in the bindings
    def resolve(term: SimpleTerm): SimpleTerm = term match {
      case StVariable(name) => bindings.get(name) match {
          case None       => term
          case Some(term) => resolve(term)
        }
      case term             => term
    }

    def applyST(term: SimpleTerm): SimpleTerm = resolve(term) match {
      case StRelation(name, terms) => StRelation(name, terms.map(applyST))
      case other                   => other
    }

    def applyP =
      (predicate: Predicate) => Predicate(applyST(predicate.value))

    def applyNeq =
      (c: Neq) => Neq(applyST(c.left), applyST(c.right))

    def applyC: Constraint => Constraint = {
      case Eq(l, r) => Eq(applyST(l), applyST(r))
      case c: Neq   => applyNeq(c)
    }

    def applyG: Goal => Goal = {
      case GoalPredicate(p)  => GoalPredicate(applyP(p))
      case GoalConstraint(c) => GoalConstraint(applyC(c))
    }

    def occurs(name: Id, term: SimpleTerm): Boolean = resolve(term) match {
      case StVariable(other)    => other == name
      case StRelation(_, terms) => terms.exists(st => occurs(name, st))
      case _: StName            => false
    }

    def bind(name: Id, term: SimpleTerm) = applyST(term) match {
      case StVariable(`name`)         => this.asRight
      case norm if occurs(name, norm) => Substitution.Error(name, norm).asLeft
      case norm                       => Substitution(bindings.updated(name, norm)).asRight
    }

    def normalized: Substitution = Substitution(bindings.fmap(applyST))
  }
  object Substitution {
    case class Error(name: Id, term: SimpleTerm) extends RunError {
      override def toString: String = s"occurs check failed for #${name.value} in $term"
    }

    def empty: Substitution = Substitution(Map.empty)
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////

  object Variables {

    private def fromTerm(term: SimpleTerm): Set[Id] = term match {
      case StName(_)            => Set.empty
      case StVariable(name)     => Set(name)
      case StRelation(_, terms) => terms.toList.flatMap(fromTerm).toSet
    }

    private def fromConstraint(constraint: Constraint) = constraint match {
      case Eq(l, r)  => fromTerm(l) ++ fromTerm(r)
      case Neq(l, r) => fromTerm(l) ++ fromTerm(r)
    }

    private def fromGoal(goal: Goal) = goal match {
      case GoalPredicate(p)      => fromTerm(p.value)
      case GoalConstraint(value) => fromConstraint(value)
    }

    def goalsToNames(goals: List[Goal]) =
      goals.flatMap(fromGoal).toSet

    // api
    def projectGoals(
      goals: List[Goal],
      substitution: Substitution
    ) =
      Variables.goalsToNames(goals)
        .toVector
        .sortBy(_.value)
        .map(name => StVariable(name) -> substitution.applyST(StVariable(name)))
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////

  object Unification {

    case class UnificationError(left: SimpleTerm, right: SimpleTerm) extends RunError {
      override def toString: String = s"cannot unify $left with $right"
    }

    def unify(l: SimpleTerm, r: SimpleTerm, subst: Substitution): Either[RunError, Substitution] = {
      val normL = subst.applyST(l)
      val normR = subst.applyST(r)

      (normL, normR) match {
        case (StVariable(name), term)                                                           => subst.bind(name, term)
        case (term, StVariable(name))                                                           => subst.bind(name, term)
        case (StName(l), StName(r)) if l == r                                                   => subst.asRight
        case (StRelation(ln, lts), StRelation(rn, rts)) if ln == rn && lts.length == rts.length =>
          (lts.toList zip rts.toList)
            .foldLeft(subst.asRight[RunError]) { case (acc, (lt, rt)) => acc.flatMap(s => unify(lt, rt, s)) }
        case _                                                                                  => UnificationError(normL, normR).asLeft
      }
    }

    def unifyPredicate(left: Predicate, right: Predicate, subst: Substitution) =
      unify(left.value, right.value, subst)
  }

  object ConstraintSolver {

    sealed trait NonEqualityResolution
    case object Satisfied extends NonEqualityResolution
    case object Violated extends NonEqualityResolution
    case class Deferred(value: Neq) extends NonEqualityResolution

    private def isGround(term: SimpleTerm): Boolean = term match {
      case StName(_)            => true
      case StVariable(_)        => false
      case StRelation(_, terms) => terms.forall(isGround)
    }

    private def evalNeq(neq: Neq, subst: Substitution): NonEqualityResolution =
      subst.applyNeq(neq) match {
        case Neq(normL, normR) if normL == normR                     => Violated
        case Neq(normL, normR) if isGround(normL) && isGround(normR) => Satisfied
        case n                                                       => Deferred(n)
      }

    def validatePendingNeq(
      neqs: List[Neq],
      subst: Substitution
    ): Option[List[Neq]] =
      neqs.foldLeft(List.empty[Neq].some) {
        case (None, _)            => None
        case (Some(pending), neq) =>
          evalNeq(neq, subst) match {
            case Violated       => None
            case Satisfied      => pending.some
            case Deferred(next) => (pending :+ next).some
          }
      }

    case class Resolution(
      subst: Substitution,
      deferredNeqs: List[Neq]
    )

    def solve(
      constraint: Constraint,
      subst: Substitution
    ) = constraint match {
      case Eq(l, r) =>
        Unification.unify(l, r, subst)
          .toOption // discard unification errors
          .map(subst => Resolution(subst, Nil))
      case neq: Neq =>
        evalNeq(neq, subst) match {
          case Violated       => None
          case Satisfied      => Resolution(subst, Nil).some
          case Deferred(next) => Resolution(subst, List(next)).some
        }
    }
  }

  object Freshener {

    case class N(value: Int) {
      def next = N(value + 1)
    }
    object N {
      def initial: N = N(0)
    }

    import cats.data.State
    private type St[A] = State[(Map[Id, Id], N), A]

    private def freshTerm(term: SimpleTerm): St[SimpleTerm] = term match {
      case n: StName => State.pure(n)

      case StVariable(name) =>
        State { case s @ (renaming, next) =>
          renaming.get(name) match {
            case Some(name2) => s -> StVariable(name2)
            case None        =>
              val id2 = Id(s"${name.value}__${next.value}")
              val s2 = (renaming.updated(name, id2), next.next)
              s2 -> StVariable(id2)
          }
        }

      case r @ StRelation(_, terms) =>
        terms
          .traverse(freshTerm)
          .map(ts => r.copy(terms = ts))
    }

    private def freshConstraint(c: Constraint): St[Constraint] = c match {
      case Eq(l, r)  => (freshTerm(l), freshTerm(r)).mapN(Eq(_, _))
      case Neq(l, r) => (freshTerm(l), freshTerm(r)).mapN(Neq(_, _))
    }

    private def freshGoal(goal: Goal): St[Goal] = goal match {
      case GoalPredicate(p)  => freshTerm(p.value).map(v => GoalPredicate(Predicate(v)))
      case GoalConstraint(c) => freshConstraint(c).map(GoalConstraint(_))
    }

    def freshClause(clause: Clause, n: N): (Clause, N) = {
      val action: St[Clause] = clause match {
        case Clause.Fact(p)       =>
          freshTerm(p.value)
            .map(st => Predicate(st))
            .map(p => Clause.Fact(p))
        case Clause.Rule(p, body) =>
          freshTerm(p.value)
            .map(st => Predicate(st))
            .flatMap(p => body.traverse(freshGoal).map(b => Clause.Rule(p, b)))
      }

      val ((_, next2), clause2) = action.run((Map.empty, n)).value
      clause2 -> next2
    }

  }

  object Solver {

    case class SearchState(
      goals: List[Goal],
      substitution: Substitution,
      pendingNeqs: List[Neq] = Nil
    )
    object SearchState {
      def initial(goals: List[Goal]) =
        SearchState(goals, Substitution.empty)
    }

    private def buildNextSearchState(
      goals: List[Goal],
      subst: Substitution,
      extraNeqs: List[Neq]
    ): Option[SearchState] =
      ConstraintSolver
        .validatePendingNeq(extraNeqs, subst)
        .map(neqs => SearchState(goals.map(subst.applyG), subst, neqs))

    private def reducePredicateGoal(
      goal: Predicate,
      remaining: List[Goal],
      subst: Substitution,
      pending: List[Neq],
      clause: Clause
    ): Option[SearchState] =
      Unification.unifyPredicate(goal, clause.head, subst)
        .toOption // discard unification errors
        .flatMap { subst =>
          val nextGoals = clause match {
            case Clause.Fact(_)           => remaining
            case Clause.Rule(_, ruleBody) => ruleBody ++ remaining
          }
          buildNextSearchState(nextGoals, subst, pending)
        }

    case class SolverState(
      search: SearchState,
      freshening: Freshener.N
    )

    import ConstraintSolver.Resolution
    def solveStep(clausesFor: Predicate => Vector[Clause], state: SolverState) =
      state.search.goals match {
        case Nil => Vector.empty

        case GoalConstraint(cons) :: tail =>
          ConstraintSolver.solve(cons, state.search.substitution)
            .flatMap { case Resolution(subst, deferredNeqs) =>
              val neqs = state.search.pendingNeqs ++ deferredNeqs
              buildNextSearchState(tail, subst, neqs)
            }
            .map(st => SolverState(st, state.freshening))
            .toVector

        case GoalPredicate(pred) :: tail =>
          val normPred = state.search.substitution.applyP(pred)
          clausesFor(normPred)
            .foldLeft(Vector.empty[SolverState] -> state.freshening) {
              case ((states, n), clause) =>
                val (clause2, n2) = Freshener.freshClause(clause, n)
                val nextStates = reducePredicateGoal(normPred, tail, state.search.substitution, state.search.pendingNeqs, clause2)
                  .fold(states)(next => states :+ SolverState(next, n2))
                nextStates -> n2
            }._1
      }

    def solve(clausesFor: Predicate => Vector[Clause], initialGoals: List[Goal]) = {

      def solveState(state: SolverState): LazyList[Substitution] =
        state.search.goals match {
          case Nil => LazyList(state.search.substitution.normalized)
          case _   =>
            solveStep(clausesFor, state)
              .foldRight(LazyList.empty[Substitution]) {
                case (st, acc) => solveState(st) #::: acc
              }
        }

      val s0 = SolverState(
        SearchState.initial(initialGoals),
        Freshener.N.initial
      )
      solveState(s0)
    }

  }

}
