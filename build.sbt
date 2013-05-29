organization  := "com.yougrade"

version       := "0.1"

scalaVersion  := "2.10.1"

name := "rest-yougrade"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "spray nightly repo" at "http://nightlies.spray.io/"
)

libraryDependencies ++= Seq(
  "io.spray"            %   "spray-can"     % "1.1-M7",
  "io.spray"            %   "spray-routing" % "1.1-M7",
  "io.spray"            %   "spray-testkit" % "1.1-M7",
  "io.spray" %% "spray-json" % "1.2.4",  
  "com.typesafe.akka"   %%  "akka-actor"    % "2.1.0",
  "org.specs2"          %%  "specs2"        % "1.13" % "test"
)

seq(Revolver.settings: _*)