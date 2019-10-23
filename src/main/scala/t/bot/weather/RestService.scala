package t.bot.weather

import cats.effect._
import cats.implicits._
import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global

object RestService extends IOApp with Instrumented {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  val helloWorldService = HttpRoutes
    .of[IO] {
      case GET -> Root / "metrics" => getMetrics()
    }
    .orNotFound

  def getMetrics() = {

    val isHealthy = registry.runHealthChecks().values().asScala.toList.forall(_.isHealthy)
    val hitsCount = metricRegistry.counter("WeatherBot.hits").getCount
    Ok(
      Json.obj("isHealthy" -> Json.fromBoolean(isHealthy), "hitsCount" -> Json.fromLong(hitsCount))
    )

  }

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(com.typesafe.config.ConfigFactory.load().getInt("http.port"), "localhost")
      .withHttpApp(helloWorldService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

}
