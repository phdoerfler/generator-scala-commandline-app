ThisBuild / scalaVersion     := "<%= scalaVersion %>"
ThisBuild / version          := "<%= version %>"
ThisBuild / organization     := "<%= organization %>"
ThisBuild / organizationName := "<%= organizationName %>"
ThisBuild / homepage := Some(url(s"<%= homepage %>"))

Global / onChangedBuildSource := ReloadOnSourceChanges
onLoad in Global ~= (_ andThen ("bloopInstall" :: _))

<% if (skipDoc) { %>
// disable scaladoc to speed up the build
Compile / packageDoc / mappings := Seq()
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

<% } %>

// If you are using Scala.js 0.6.x, you need the following import:
//import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val root =
  // select supported platforms
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Full)
    .in(file("."))
    .settings(shared)
    .enablePlugins(BuildInfoPlugin)
    .enablePlugins(JavaAppPackaging)
    .jsSettings(/* ... */) // defined in sbt-scalajs-crossproject
    .jvmSettings(
      libraryDependencies ++= Seq(
        <% if (libs.includes("scalapy")) { %>
        "me.shadaj" %% "scalapy-core" % "<%= scalapyVersion %>",
        <% } %>
        "org.jline" % "jline" % "3.19.0",
        ("com.github.pathikrit" %% "better-files" % "3.9.1").cross(CrossVersion.for3Use2_13)
      ),
      scalacOptions := Seq("-unchecked", "-deprecation", "-language:_", "-encoding", "UTF-8")
    )
    // configure Scala-Native settings
    .nativeSettings(
      // Set to false or remove if you want to show stubs as linking errors
      nativeLinkStubs := true,
      <% if (libs.includes("scalapy")) { %>
      libraryDependencies += "me.shadaj" %%% "scalapy-core" % "<%= scalapyVersion %>",
      <% } %>
    ) // defined in sbt-scala-native


lazy val shared = Seq(
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
  buildInfoPackage := organization.value,

  libraryDependencies ++= Seq(
    ("com.github.pathikrit" %% "better-files" % "3.9.1").cross(CrossVersion.for3Use2_13),
    "com.github.scopt" %%% "scopt" % "4.0.1")
)