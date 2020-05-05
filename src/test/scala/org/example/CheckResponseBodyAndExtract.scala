package org.example
import io.gatling.http.Predef._
import io.gatling.core.Predef._

class CheckResponseBodyAndExtract extends Simulation {

  var httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))

  val scn = scenario("Video game")
    .exec(http("1st call").get("videogames")).pause(5)
    .exec(http("2 call").get("videogames/1").check(jsonPath("$.name").is("Resident Evil 4"))).pause(1, 2)
    .exec(http("get all video games 3rd call")
      .get("videogames").check(jsonPath("$[1].id").saveAs("gameId")))
    .exec{session=>println(session);session}
    .exec(http("get specific game")
      .get("videogames/${gameId}")
       .check(jsonPath("$.name").is("Gran Turismo 3"))
       .check(bodyString.saveAs("responseBody")))
      .exec{session=>println(session("responseBody").as[String]);session}

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
