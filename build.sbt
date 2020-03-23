name := "scaes"

version := "0.1"

scalaVersion := "2.12.8"

val elastic4sVersion = "7.3.1"
val typesafeConfigVersion = "1.4.0"
val elasticsearchCore = "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion
val elasticsearchClient = "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion
val elasticsearchStreams = "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % elastic4sVersion
val typesafeConfig = "com.typesafe" % "config" % typesafeConfigVersion


libraryDependencies ++= List(
  elasticsearchCore,
  elasticsearchClient,
  elasticsearchStreams,
)

lazy val root = (project in file("."))
  .settings(
    mainClass in(Compile, run) := Some("edu.knol.Main"),
    run := {
      (run in `scaes-impl` in Compile).evaluated
    }
  ).aggregate(`scaes-api`, `scaes-impl`)

lazy val `scaes-api` = (project in file("scaes-api"))

lazy val `scaes-impl` = (project in file("scaes-impl"))
  .settings(
    libraryDependencies ++= List(
      elasticsearchCore,
      elasticsearchClient,
      elasticsearchStreams,
      typesafeConfig
    )
  ).dependsOn(`scaes-api`)