import sbt.Keys.version
import scala.collection.Seq

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion := "2.13.18"
ThisBuild / javacOptions := Seq("-source", "21", "-target", "21")

ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds",
  "-language:existentials",
  "-Wconf:cat=other-match-analysis:error",  // fail on non-exhaustive pattern matching
  "-Wconf:msg=implicit numeric widening:s", // silence number widening
  "-Wconf:msg=unused:s",                    // silence unused things
  "-Ymacro-annotations",
  "-Ywarn-value-discard",
  "-Ywarn-dead-code",
)

ThisBuild / libraryDependencies ++= Seq(
  /** some useful plugin things */
  compilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.4" cross CrossVersion.full),
  compilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
  /** basic category things */
  "org.typelevel"               %% "cats-core"               % "2.13.0",
  /** effects */
  "org.typelevel"               %% "cats-effect"             % "3.7.0",
  "co.fs2"                      %% "fs2-io"                  % "3.13.0",
  /** json serialization */
  "io.circe"                    %% "circe-parser"            % "0.14.15",
  "io.circe"                    %% "circe-generic-extras"    % "0.14.4",
  /** enum support */
  "com.beachape"                %% "enumeratum"              % "1.9.7",
  "com.beachape"                %% "enumeratum-circe"        % "1.9.7",
  "com.beachape"                %% "enumeratum-cats"         % "1.9.7",
  "com.beachape"                %% "enumeratum-scalacheck"   % "1.9.7",
  /** testing */
  "org.scalatest"               %% "scalatest"               % "3.2.20",
  "org.scalacheck"              %% "scalacheck"              % "1.19.0",
  "org.scalatestplus"           %% "scalacheck-1-19"         % "3.2.20.0",
  "org.mockito"                 %% "mockito-scala-scalatest" % "2.2.1",
  "org.typelevel"               %% "literally"               % "1.2.0",
  /** colored & informative output */
  "com.lihaoyi"                 %% "pprint"                  % "0.9.6",
  "org.springframework.security" % "spring-security-crypto"  % "7.0.5",
  "org.junit.jupiter"            % "junit-jupiter"           % "6.0.3"
)

lazy val algo = project.in(file("algo"))
  .settings(
    organization := "com.djnzx",
    name := "algorithms-scala",
    version := "0.0.1",
  )
