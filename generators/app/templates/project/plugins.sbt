addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.8.0")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.9.0")

addSbtPlugin("org.scalameta"     % "sbt-scalafmt"  % "2.4.2")
addSbtPlugin("org.scoverage"     % "sbt-scoverage" % "1.6.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header"    % "5.6.0")
addSbtPlugin("ch.epfl.scala"     % "sbt-scalafix"  % "0.9.26")

addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-site"         % "1.4.1")
addSbtPlugin("io.kevinlee"      % "sbt-github-pages" % "0.5.0")

addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.2.20")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.0.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.0.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.5.1")
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.4.0")