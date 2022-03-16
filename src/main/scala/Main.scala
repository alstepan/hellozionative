import zio.{ZEnv, Ref, UIO, URIO, ZIO, clock, App}
import zio.clock.{Clock, sleep}
import zio.console.{Console, putStr, putStrLn}
import zio.duration.durationInt
import zio.internal.Executor

import java.util.concurrent.TimeUnit
import scala.language.postfixOps
import zio.Task
import zio.ExitCode

object Main extends App {
   val currentTime: URIO[Clock, Long] = clock.currentTime(TimeUnit.SECONDS)
  /**
   * Эффект который все что делает, это спит заданное кол-во времени, в данном случае 1 секунду
   */
  val sleep1Second = ZIO.sleep(1.seconds)

  /**
   * Эффект который все что делает, это спит заданное кол-во времени, в данном случае 1 секунду
   */
  val sleep3Seconds = ZIO.sleep(3.seconds)

  /**
   * Создать эффект который печатает в консоль GetExchangeRatesLocation1 спустя 3 секунды
   */
     lazy val getExchangeRatesLocation1 = sleep3Seconds *> putStrLn("GetExchangeRatesLocation1")

  /**
   * Создать эффект который печатает в консоль GetExchangeRatesLocation2 спустя 1 секунду
   */
    lazy val getExchangeRatesLocation2 = sleep1Second *> putStrLn("GetExchangeRatesLocation2")

  def printEffectRunningTime[R, E, A](zio: ZIO[R, E, A]) = for{
      start <- currentTime
      z <- zio
      end <- currentTime
      _ <- putStrLn(s"Running time: ${end - start}")
  } yield z


  val exchangeRates: Map[String, Double] = Map(
    "usd" -> 76.02,
    "eur" -> 91.27
  )


  /**
   * Написать эффект котрый получит курсы из обеих локаций паралельно
   */
  lazy val getFrom2LocationsInParallel = for{
    f1 <- getExchangeRatesLocation1.fork
    f2 <- getExchangeRatesLocation2.fork
    r1 <- f1.join
    r2 <- f2.join
  } yield (r1, r2) 


  override def run(args: List[String]): URIO[ZEnv,ExitCode] = {
    val program = printEffectRunningTime(getFrom2LocationsInParallel)

    program.exitCode
  }
}
