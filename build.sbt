name := "HttpTaskManager"


// Versions
version := "0.1"
scalaVersion := "2.12.7"
val circeVersion = "0.10.0"

// Dependencies
libraryDependencies ++= Seq(
  // Akka HTTP dependencies.
  "com.typesafe.akka" %% "akka-actor" % "2.5.17",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.17" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.5.17",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.17" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.1.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test,
  // Circie for JSON encoding and decoding.
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  // AKKA HTTP plugin.
  "de.heikoseeberger" %% "akka-http-circe" % "1.22.0",
  // Scalatest
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalactic" %% "scalactic" % "3.0.5"
)
