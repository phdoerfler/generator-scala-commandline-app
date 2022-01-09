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
  ("com.github.pathikrit" %% "better-files" % "3.9.1").cross(CrossVersion.for3Use2_13),
  "com.github.scopt" %% "scopt" % "4.0.1"
)

enablePlugins(BuildInfoPlugin)
enablePlugins(JavaAppPackaging)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := organization.value

<% if (skipDoc) { %>
// disable scaladoc to speed up the build
mappings in (Compile, packageDoc) := Seq()
<% } %>

<% if (libs.includes("scalapy")) { %>
// JVM
libraryDependencies += "me.shadaj" %% "scalapy-core" % "<%= scalapyVersion %>"
// Scala Native
libraryDependencies += "me.shadaj" %%% "scalapy-core" % "<%= scalapyVersion %>"

fork := true

import scala.sys.process._
lazy val pythonLdFlags = {
  val withoutEmbed = "python3-config --ldflags".!!
  if (withoutEmbed.contains("-lpython")) {
    withoutEmbed.split(' ').map(_.trim).filter(_.nonEmpty).toSeq
  } else {
    val withEmbed = "python3-config --ldflags --embed".!!
    withEmbed.split(' ').map(_.trim).filter(_.nonEmpty).toSeq
  }
}

lazy val pythonLibsDir = {
  pythonLdFlags.find(_.startsWith("-L")).get.drop("-L".length)
}

javaOptions += s"-Djna.library.path=$pythonLibsDir"
<% } %>

<% if (libs.includes("scalanative")) { %>
// Set to false or remove if you want to show stubs as linking errors
nativeLinkStubs := true

enablePlugins(ScalaNativePlugin)
<% } %>
