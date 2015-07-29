name := """measuringux"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.webjars" % "chartjs" % "1.0.2",
  javaJdbc,
  cache,
  javaWs,
  "junit" % "junit" % "4.12" % "test"
)

includeFilter in (Assets, LessKeys.less) := "crisp-bootstrap.less"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
