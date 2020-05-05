package org.example

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.http.Predef._
import io.gatling.core.Predef._

import scala.util.Random


class CustomFeeder extends Simulation{
  val httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))
  val idNumbers =(11 to 20).iterator
  val rnd = new Random()
  def randomString(length:Int) ={
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }
  def getRandomDate(startDate: LocalDate,random: Random):String ={
    startDate.minusDays(random.nextInt(30)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  }
  val customFeeder = Iterator.continually(Map(
    "gameId"-> idNumbers.next,
    "name"->("Game-"+randomString(3)),
    "releaseDate"-> getRandomDate(LocalDate.now,rnd),
    "reviewScore"->rnd.nextInt(100),
    "category"->("Category-"+randomString(3)),
    "rating"->("Rating-"+randomString(5))
  ))
  def postNewGame()={
    repeat(5){
      feed(customFeeder).exec(http("Post new Game").post("videogames")
      .body(ElFileBody("bodies/NewGameTemplate.json")).asJson
        .check(status.is(200)))
    }
  }
  val scn = scenario("Post New Game").exec(postNewGame())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
