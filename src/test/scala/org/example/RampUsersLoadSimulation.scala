package org.example
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RampUsersLoadSimulation extends Simulation{
  val httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))

  def getAllVideoGames() ={
    exec(
      http("Get All Videogames")
        .get("videogames")
        .check(status.is(200))
    )
  }

  def getSpecificGame()={
    exec(
      http("Get specific Game")
        .get("videogames/1")
        .check(status.is(200))
    )
  }
  val scn= scenario("Get basic load simulation")
    .exec(getAllVideoGames).pause(2)
    .exec(getSpecificGame).pause(2)
    .exec(getAllVideoGames)

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      constantUsersPerSec(10) during (10 seconds)

    )
  ).protocols(httpConf)

}
