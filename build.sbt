resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
resolvers += "Apache HttpClient" at "https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient"

lazy val commonSettings = Seq(
  scalaVersion := "2.10.6",
  version := "0.1",
  organization := "com.tinktest"
)
def itFilter(name: String): Boolean = name endsWith "Spec"

lazy val root = (project in file("."))
  .configs(Test)
  .settings(
    commonSettings,
    name := "TinkTest",
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      "org.apache.httpcomponents" % "httpclient" % "4.5.3",
      "org.scalactic" %% "scalactic" % "3.0.4",
      "org.scalatest" %% "scalatest" % "3.0.4" % "test"
    )
  )

testOptions in Test := Seq(Tests.Filter(itFilter))
logLevel := Level.Info

parallelExecution in Global := false