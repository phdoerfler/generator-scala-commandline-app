ThisBuild / scalaVersion     := "<%= scalaVersion %>"
ThisBuild / version          := "<%= version %>"
ThisBuild / organization     := "<%= organization %>"
ThisBuild / organizationName := "<%= organizationName %>"
ThisBuild / homepage := Some(url(s"<%= homepage %>"))


ThisBuild / scalacOptions := Seq("-unchecked", "-deprecation", "-language:_", "-encoding", "UTF-8")

Global / onChangedBuildSource := ReloadOnSourceChanges
onLoad in Global ~= (_ andThen ("bloopInstall" :: _))

libraryDependencies ++= Seq(
  "org.jline" % "jline" % "3.19.0",
  "com.github.pathikrit" %% "better-files" % "3.9.1",
  "com.github.scopt" %% "scopt" % "4.0.1"
)

enablePlugins(BuildInfoPlugin)
enablePlugins(JavaAppPackaging)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := organization.value