ThisBuild / scalaVersion := "3.10.0"

lazy val root = project
  .in(file("."))
  .aggregate(docs)

lazy val docs = project
  .in(file("site-docs"))
  .enablePlugins(DocusaurusPlugin, MdocPlugin)
  .settings(
    moduleName := "site-docs",
    mdocExtraArguments += "--no-link-hygiene",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.13.0",
      "org.typelevel" %% "cats-parse" % "1.1.0"
    ),
    watchSources += baseDirectory.in(ThisBuild).value / "docs"
  )

ThisBuild / scalacOptions := Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-Werror" // Fail the compilation if there are any warnings.
)
