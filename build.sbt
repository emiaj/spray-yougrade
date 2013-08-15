import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

organization  := "com.yougrade"

version       := "0.1"

scalaVersion  := "2.10.1"

name := "rest-yougrade"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "spray nightly repo" at "http://nightlies.spray.io/",
  "Eligosource Snapshots" at "http://repo.eligotech.com/nexus/content/repositories/eligosource-snapshots"
)

libraryDependencies ++= Seq(
    "io.spray" % "spray-routing" % "1.2-M8",
    "io.spray" % "spray-can" % "1.2-M8",
    "io.spray" % "spray-httpx" % "1.2-M8",
    "io.spray" % "spray-http" % "1.2-M8",
    "io.spray" % "spray-io" % "1.2-M8",
    "io.spray" % "spray-util" % "1.2-M8",
    "io.spray" % "spray-testkit" % "1.2-M8",
    "io.spray" %% "spray-json" % "1.2.5",
    "org.scalaz" %% "scalaz-core" % "7.0.0",
    "com.typesafe.akka" %% "akka-actor" % "2.2.0-RC1",
    "com.typesafe.akka" %% "akka-slf4j" % "2.2.0-RC1",
    "com.typesafe.akka" %% "akka-testkit" % "2.2.0-RC1" % "test",
    "org.specs2" %% "specs2" % "2.0-RC2" % "test",
    "org.eligosource" %% "eventsourced-core" % "0.6-SNAPSHOT",
    "org.eligosource" %% "eventsourced-journal-leveldb" % "0.6-SNAPSHOT",
    "org.scalatest" %% "scalatest" % "2.0.M6-SNAP26"
)

seq(Revolver.settings: _*)