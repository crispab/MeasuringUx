name := """measuringux"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.webjars" % "chartjs" % "1.0.2",
  "org.webjars" % "html2canvas" % "0.4.1",
  "com.google.api-client" % "google-api-client" % "1.20.0",
  "com.google.http-client" % "google-http-client-jackson2" % "1.20.0",
  "com.google.gdata" % "core" % "1.47.1",
  "com.newrelic.agent.java" % "newrelic-agent" % "3.21.0",
  "com.newrelic.agent.java" % "newrelic-api" % "3.21.0",
  javaJdbc,
  cache,
  javaWs,
  "junit" % "junit" % "4.12" % "test"
)

includeFilter in (Assets, LessKeys.less) := "crisp-bootstrap.less"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
