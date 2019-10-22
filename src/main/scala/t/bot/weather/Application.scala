package t.bot.weather

object Application extends App {

  val api_token = com.typesafe.config.ConfigFactory.load().getString("bot.api_token")
  val bot = new WeatherBot(api_token)
  bot.run()

  while (true) {}
  sys.addShutdownHook(() => {
    println("shutting down")
    bot.shutdown()
  })
}
