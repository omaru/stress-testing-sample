package org.example

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class CheckResponseCode extends Simulation{

  var httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost",8080))

  val scn = scenario("Video game")
    .exec(http("1st call").get("videogames").check(status.is(200))).pause(5)
    .exec(http("2 call").get("videogames/1").check(status.in(200 to 400))).pause(1,2)
    .exec(http("get all video games 3rd call")
      .get("videogames").check(status.not(404),status.not(500))).pause(200.milliseconds)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
