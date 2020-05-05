package org.example

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RuntimeParameters extends Simulation{
  val httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))
  def getAllVideoGames()={
    exec(http("Get All Videogames").get("videogames").check(status.is(200)))
  }
  val scn = scenario("Get All VideoGames").forever(){
    exec(getAllVideoGames())
  }

  setUp(
    scn.inject(
      nothingFor(1 second),
      rampUsers(1) during (1 second)
    )
  )
}
