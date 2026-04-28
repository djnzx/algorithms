package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.data.NonEmptyList
import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax
import djnz.hackerrank.fp.a6adhoc.p11.InputSyntax.Rule
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson

class P11RuntimeSpec extends P11zSpecSupport {

  import InputSyntax.{Rule => _, _}
  import P11ElementaryWatson._

  private def indexed(clauses: Vector[Clause])(predicate: Predicate): Vector[Clause] =
    Predicate.Key.make(predicate) match {
      case Some(key) => clauses.filter(clause => Predicate.Key.make(clause.head).contains(key))
      case None      => clauses
    }

  test("substitution applies recursively") {
    val substitution = Substitution(
      Map(
        "x".id -> StName("a".id),
        "y".id -> StRelation("p".id, NonEmptyList.of(StVariable("x".id), StName("b".id)))
      )
    )

    substitution.applyST(StVariable("y".id)) shouldBe
      StRelation("p".id, NonEmptyList.of(StName("a".id), StName("b".id)))
  }

  test("substitution normalized resolves binding chains") {
    val substitution = Substitution(
      Map(
        "who".id -> StVariable("x".id),
        "x".id   -> StName("a".id)
      )
    )

    substitution.normalized shouldBe
      Substitution(
        Map(
          "who".id -> StName("a".id),
          "x".id   -> StName("a".id)
        )
      )
  }

  test("unification binds variables") {
    val result =
      Unification.unify(
        StVariable("x".id),
        StName("a".id),
        Substitution.empty
      )

    result shouldBe Substitution(Map("x".id -> StName("a".id))).asRight
  }

  test("unification works for relation terms") {
    val left = StRelation("p".id, NonEmptyList.of(StVariable("x".id), StName("b".id)))
    val right = StRelation("p".id, NonEmptyList.of(StName("a".id), StVariable("y".id)))

    val result = Unification.unify(left, right, Substitution.empty)

    result shouldBe
      Substitution(
        Map(
          "x".id -> StName("a".id),
          "y".id -> StName("b".id)
        )
      ).asRight
  }

  test("predicate compilation accepts relation terms only") {
    P11ElementaryWatson.Predicate(StName("a".id)) shouldBe
      Predicate(StName("a".id))

    P11ElementaryWatson.Predicate(StRelation("p".id, NonEmptyList.of(StName("a".id)))) shouldBe
      Predicate(StRelation("p".id, NonEmptyList.of(StName("a".id))))
  }

  test("constraint solver executes equality") {
    val result =
      ConstraintSolver.solve(
        Eq(StVariable("x".id), StName("a".id)),
        Substitution.empty
      )

    result shouldBe ConstraintSolver.Resolution(Substitution(Map("x".id -> StName("a".id))), Nil).some
  }

  test("constraint solver executes disequality") {
    ConstraintSolver.solve(
      Neq(StName("a".id), StName("b".id)),
      Substitution.empty
    ) shouldBe ConstraintSolver.Resolution(Substitution.empty, Nil).some

    ConstraintSolver.solve(
      Neq(StName("a".id), StName("a".id)),
      Substitution.empty
    ) shouldBe none[ConstraintSolver.Resolution]
  }

  test("constraint solver defers disequality with unbound variables") {
    ConstraintSolver.solve(
      Neq(StVariable("x".id), StName("a".id)),
      Substitution.empty
    ) shouldBe ConstraintSolver.Resolution(
      Substitution.empty,
      List(Neq(StVariable("x".id), StName("a".id)))
    ).some

    ConstraintSolver.validatePendingNeq(
      List(Neq(StVariable("x".id), StName("a".id))),
      Substitution(Map("x".id -> StName("b".id)))
    ) shouldBe List.empty[Neq].some

    ConstraintSolver.validatePendingNeq(
      List(Neq(StVariable("x".id), StName("a".id))),
      Substitution(Map("x".id -> StName("a".id)))
    ) shouldBe none[List[Neq]]
  }

  test("variables extracts query variables from goals") {
    val goals = List(
      GoalPredicate(Predicate(StRelation("p".id, NonEmptyList.of(StVariable("x".id), StName("a".id))))),
      GoalConstraint(Neq(StVariable("y".id), StRelation("q".id, NonEmptyList.of(StVariable("x".id)))))
    )

    Variables.goalsToNames(goals) shouldBe Set("x".id, "y".id)
  }

  test("freshener renames rule variables consistently") {
    val rule = P11ElementaryWatson.Clause.Rule(
      Predicate(StRelation("p".id, NonEmptyList.of(StVariable("x".id)))),
      List(
        GoalPredicate(Predicate(StRelation("q".id, NonEmptyList.of(StVariable("x".id), StVariable("y".id))))),
        GoalConstraint(Neq(StVariable("y".id), StVariable("x".id)))
      )
    )

    val (freshRule, nextState) = Freshener.freshClause(rule, Freshener.N.initial)

    freshRule shouldBe P11ElementaryWatson.Clause.Rule(
      Predicate(StRelation("p".id, NonEmptyList.of(StVariable("x__0".id)))),
      List(
        GoalPredicate(Predicate(StRelation("q".id, NonEmptyList.of(StVariable("x__0".id), StVariable("y__1".id))))),
        GoalConstraint(Neq(StVariable("y__1".id), StVariable("x__0".id)))
      )
    )
    nextState shouldBe Freshener.N(2)
  }

  test("solver step reduces a fact goal") {
    val clauses = Vector(
      Clause.Fact(Predicate(StRelation("p".id, NonEmptyList.of(StName("a".id)))))
    )

    val result = Solver.solveStep(
      indexed(clauses),
      Solver.SolverState(
        Solver.SearchState(
          List(GoalPredicate(Predicate(StRelation("p".id, NonEmptyList.of(StVariable("x".id)))))),
          Substitution.empty
        ),
        Freshener.N.initial
      )
    )

    result shouldBe
      Vector(
        Solver.SolverState(
          Solver.SearchState(
            Nil,
            Substitution(Map("x".id -> StName("a".id)))
          ),
          Freshener.N.initial
        )
      )
  }

  test("solver solves a fact query") {
    val clauses = Vector(
      Clause.Fact(Predicate(StRelation("p".id, NonEmptyList.of(StName("a".id)))))
    )

    val result = Solver.solve(
      indexed(clauses),
      List(GoalPredicate(Predicate(StRelation("p".id, NonEmptyList.of(StVariable("x".id))))))
    )

    result shouldBe LazyList(Substitution(Map("x".id -> StName("a".id))))
  }

  test("solver handles equality and disequality goals") {
    val result = Solver.solve(
      _ => Vector.empty,
      List(
        GoalConstraint(Eq(StVariable("x".id), StName("a".id))),
        GoalConstraint(Neq(StVariable("x".id), StName("b".id)))
      )
    )

    result shouldBe LazyList(Substitution(Map("x".id -> StName("a".id))))
  }

  test("knowledge query returns only original query variables") {
    val knowledge = Knowledge.initial
      .addClause(Clause.Fact(Predicate(StRelation("q".id, NonEmptyList.of(StName("a".id))))))
      .addClause(
        P11ElementaryWatson.Clause.Rule(
          Predicate(StRelation("p".id, NonEmptyList.of(StVariable("x".id)))),
          List(GoalPredicate(Predicate(StRelation("q".id, NonEmptyList.of(StVariable("x".id))))))
        )
      )

    val result = knowledge.query(
      QueryCompiler.queryToGoals(
        Query(
          List(
            StRelation("p".id, NonEmptyList.of(StVariable("who".id)))
          )
        )
      )
    )

    result shouldBe Vector(
      Vector(
        StVariable("who".id) -> StName("a".id)
      )
    ).some
  }

  test("knowledge accepts and resolves parsed sample-style fact with uppercase names") {
    val knowledge = Knowledge.initial
      .addClause(QueryCompiler.ruleToClause(
        Rule.Fact(
          StRelation(
            "spouse-of".id,
            NonEmptyList.of(StName("ColTravis".id), StName("Martha".id))
          )
        )
      ))

    val result = knowledge.query(
      QueryCompiler.queryToGoals(
        Query(
          List(
            StRelation(
              "spouse-of".id,
              NonEmptyList.of(StName("ColTravis".id), StName("Martha".id))
            )
          )
        )
      )
    )

    result shouldBe Vector(Vector.empty[(StVariable, SimpleTerm)]).some
  }

  test("knowledge accepts and resolves bare-name sample-style fact") {
    val knowledge = Knowledge.initial
      .addClause(QueryCompiler.ruleToClause(
        Rule.Fact(StName("ColTravis".id))
      ))

    val result = knowledge.query(
      QueryCompiler.queryToGoals(
        Query(
          List(
            StName("ColTravis".id)
          )
        )
      )
    )

    result shouldBe Vector(Vector.empty[(StVariable, SimpleTerm)]).some
  }
}
