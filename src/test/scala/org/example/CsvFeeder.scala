package org.example
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends  Simulation{
  var httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))
  val csvFeeder = csv("data/gameCsvFile.csv").circular
  def getSpecificVideoGame()={
    repeat(10){
      feed(csvFeeder)
        .exec(http("Get Specific Video Game").get("videogames/${gameId}")
         .check(jsonPath("$.name").is("${gameName}"))
          .check(status.is(200))).pause(1)
    }
  }
  val scn=scenario("Csv Feeder Test").exec(getSpecificVideoGame())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
