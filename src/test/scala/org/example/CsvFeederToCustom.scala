package org.example

import io.gatling.http.Predef._
import io.gatling.core.Predef._

class CsvFeederToCustom extends Simulation{
  var httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))
  val idNumbers = (1 to 10).iterator

  val customFeeder= Iterator.continually(Map("gameId"->idNumbers.next()))

  def getSpecificVideoGame()={
    repeat(10){
      feed(customFeeder)
        .exec(http("Get Specific Video Game").get("videogames/${gameId}")
          .check(status.is(200))).pause(1)
    }
  }
  val scn=scenario("Csv Feeder Test").exec(getSpecificVideoGame())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
