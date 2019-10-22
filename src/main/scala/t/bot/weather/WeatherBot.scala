package t.bot.weather

import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.future.Polling

import scala.concurrent.Future

class WeatherBot(token: String)
    extends AbstractBot(token) with Polling with Commands[Future] with WeatherDataFetcher {

  onMessage({ implicit msg =>
    if (msg.location.nonEmpty) {
      fetchForecast(msg.location.get.latitude, msg.location.get.longitude) match {
        case Right(forecast) =>
          replyMd(forecast.toString, disableWebPagePreview = Some(true)).void
        case Left(error) =>
          logger.warn(s"Error msg: $msg, error: $error")
          replyMd("Unable to fetch forecast...").void
      }
    } else Future.successful(Unit)
  })

}
