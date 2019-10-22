package t.bot.weather

trait WeatherDataFetcher extends ResultsParser {

  import sttp.client._
  implicit val weatherFetcherBackend = sttp.client.HttpURLConnectionBackend()

  def fetchForecast(latitude: Double, longitude: Double): Either[String, FetchResult] = {

    val detailsUrl = uri"https://yandex.ru/pogoda/?lat=$latitude&lon=$longitude"

    for {
      response <- basicRequest.get(detailsUrl).send().body.right
    } yield {
      val parsedResponse = parseResult(response)
      val forecastResult = parsedResponse.hourlyForecast
        .map(i => s"${i.when}: ${i.temperature}")
        .mkString("\n")
      FetchResult(parsedResponse.locationName, s"Now: $forecastResult", detailsUrl.toString())
    }
  }
}

case class FetchResult(location: String, data: String, detailsUrl: String) {
  override def toString: String =
    s"Weather forecast for $location, ```$data```\n [See more details...]($detailsUrl)"
}

trait ResultsParser {
  import net.ruippeixotog.scalascraper.browser.JsoupBrowser
  import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
  import net.ruippeixotog.scalascraper.dsl.DSL._

  def parseResult(a: String): ParseResult = {
    val doc = JsoupBrowser().parseString(a)
    val forecastItems = doc >> elementList(".fact__hour")
    val locationName = (doc >> element(".header-title__title")).text
    ParseResult(
      locationName,
      forecastItems
        .map(i => (i >> element(".fact__hour-label"), i >> element(".fact__hour-temp")))
        .map(tuple => HourlyForecast(tuple._1.text, tuple._2.text))
    )
  }
}

case class ParseResult(locationName: String, hourlyForecast: List[HourlyForecast])
case class HourlyForecast(when: String, temperature: String)
