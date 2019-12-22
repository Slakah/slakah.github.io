lazy val root = project
  .in(file("."))
  .aggregate(docs)

lazy val docs = project
  .in(file("site-docs"))
  .enablePlugins(DocusaurusPlugin, MdocPlugin)
  .settings(
    moduleName := "site-docs",
    watchSources += baseDirectory.in(ThisBuild).value / "docs"
  )
