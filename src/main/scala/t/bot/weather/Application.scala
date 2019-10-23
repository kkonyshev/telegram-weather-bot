package t.bot.weather

object Application extends App {

  val api_token = com.typesafe.config.ConfigFactory.load().getString("bot.api_token")
  val bot = new WeatherBot(api_token)
  bot.run()

  RestService.run(Nil).unsafeToFuture()
}

trait Instrumented
    extends nl.grons.metrics4.scala.InstrumentedBuilder
    with nl.grons.metrics4.scala.DefaultInstrumented
