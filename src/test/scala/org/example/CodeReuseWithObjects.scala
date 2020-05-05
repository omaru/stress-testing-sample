package org.example

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuseWithObjects extends Simulation{

  var httpConf = http.baseUrl("http://localhost:8081/app/").header("Accept", "application/json")
    .proxy(Proxy("localhost", 8080))

  def getAllVideogames()={
    repeat(3){
      exec(http("Get All Videogames").get("videogames")
        .check(status.is(200)))
    }
  }
  def getSpecificVideoGame()={
    repeat(5){
      exec(http("Get Specific Game").get("videogames/1")
        .check(status.in(200 to 210)))
    }
  }
  val scn = scenario("Code Reuse")
      .exec(getAllVideogames())
      .pause(5)
      .exec(getSpecificVideoGame())
      .pause(5)
      .exec(getAllVideogames())

    setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
