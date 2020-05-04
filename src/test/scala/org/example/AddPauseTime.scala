package org.example
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AddPauseTime extends Simulation {

  var httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost",8080))

  val scn = scenario("Video game")
    .exec(http("1st call").get("videogames")).pause(5)
    .exec(http("2 call").get("videogames/1")).pause(1,2)
    .exec(http("get all video games 3rd call").get("videogames")).pause(200.milliseconds)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}