name := "telegram-weather-bot"

version := "0.1"

scalaVersion := "2.11.12"

val sttpVersion = "1.6.4"
val circleVersion = "0.11.1"

val coreDependencies = Seq(
  "com.typesafe" % "config" % "1.2.1"
)

val sttpDependencies = Seq(
  "com.softwaremill.sttp" %% "core" % sttpVersion,
  "com.softwaremill.sttp" %% "circe" % sttpVersion,
  "com.softwaremill.sttp" %% "okhttp-backend" % sttpVersion
  )

val sttpClientDependencies = Seq(
  "com.softwaremill.sttp.client" %% "core" % "2.0.0-M7"
)

val circeDependencies = Seq(
  "io.circe" %% "circe-core" % circleVersion,
  "io.circe" %% "circe-generic" % circleVersion,
  "io.circe" %% "circe-generic-extras" % circleVersion,
  "io.circe" %% "circe-parser" % circleVersion,
  "io.circe" %% "circe-literal" % circleVersion
  )

val telegramBotDependency = Seq(
  "com.bot4s" %% "telegram-core" % "4.4.0-RC1"
)

val webUtilsDependencies = Seq(
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0"
)

libraryDependencies ++= coreDependencies ++
                        sttpDependencies ++
                        sttpClientDependencies ++
                        circeDependencies ++
                        telegramBotDependency ++
                        webUtilsDependencies

// ##
enablePlugins(JavaAppPackaging)
// Disable javadoc packaging
mappings in (Compile, packageDoc) := Seq()

// docker packaging configuration
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)
mainClass in Compile := Some("t.bot.weather.Application")
dockerBaseImage      := "openjdk:8-jre-alpine"

// workaround for https://github.com/sbt/sbt-native-packager/issues/1202
daemonUserUid in Docker := None
daemonUser in Docker    := "daemon"
