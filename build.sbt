import sbt.Keys.version
import scala.collection.Seq

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion := "2.13.15"
ThisBuild / javacOptions := Seq("-source", "17", "-target", "17")

ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds",
  "-language:existentials",
  "-Wconf:cat=other-match-analysis:error",
  "-Wunused",
//  "-Xfatal-warnings",
  "-Ymacro-annotations",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-dead-code",
//    "-Ywarn-unused",
  "-Yrepl-class-based"
)

ThisBuild / libraryDependencies ++= Seq(
  /** some useful plugin things */
  compilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.3" cross CrossVersion.full),
  compilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
  /** basic category things */
  "org.typelevel"               %% "cats-core"               % "2.12.0",
  /** effects */
  "org.typelevel"               %% "cats-effect"             % "3.5.4",
  "co.fs2"                      %% "fs2-io"                  % "3.11.0",
  /** json serialization */
  "io.circe"                    %% "circe-parser"            % "0.14.10",
  "io.circe"                    %% "circe-generic-extras"    % "0.14.4",
  /** enum support */
  "com.beachape"                %% "enumeratum"              % "1.7.5",
  "com.beachape"                %% "enumeratum-circe"        % "1.7.5",
  "com.beachape"                %% "enumeratum-cats"         % "1.7.5",
  "com.beachape"                %% "enumeratum-scalacheck"   % "1.7.5",
  /** testing */
  "org.scalatest"               %% "scalatest"               % "3.2.19",
  "org.scalacheck"              %% "scalacheck"              % "1.18.1",
  "org.scalatestplus"           %% "scalacheck-1-18"         % "3.2.19.0",
  "org.mockito"                 %% "mockito-scala-scalatest" % "1.17.37",
  /** colored & informative output */
  "com.lihaoyi"                 %% "pprint"                  % "0.9.0",
  /** sometimes we do Java and don't want to waste time on missing bits */
  "org.projectlombok"            % "lombok"                  % "1.18.34" % Provided,
  "org.springframework.security" % "spring-security-crypto"  % "6.3.0",
  "org.junit.jupiter"            % "junit-jupiter"           % "5.10.3"
)

lazy val algo = project.in(file("algo"))
  .settings(
    organization := "com.djnzx",
    name := "algorithms",
    version := "0.0.1",
  )
