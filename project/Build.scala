import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ots"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
      //"siena" % "play-siena" % "v2.0.2"
      "com.github.julienrf" %% "play-jsmessages" % "1.0",
      "com.typesafe" %% "play-plugins-mailer" % "2.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers += "Apache" at "http://repo1.maven.org/maven2/",
      resolvers += "julienrf.github.com" at "http://julienrf.github.com/repo/"
    )

}
