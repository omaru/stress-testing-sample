package org.example
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ImportVoucherSimulation extends Simulation{

  val httpConf = http.baseUrl("http://localhost:8081/app/")
    .header("accept","application/json").proxy(Proxy("localhost",8080))

  val scn = scenario("My First Test")
    .exec(http("Get all names").post("videogames")
      .body(StringBody(HelloWorld.voucherCodes("123",90).next())))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)


}
