package com.yougrade

import spray.can.server.SprayCanHttpServerApp
import akka.actor._

object Main extends App with SprayCanHttpServerApp {

  // create and start our service actor
  val service = system.actorOf(Props[Api], "yougrade-service")

  // create a new HttpServer using our handler tell it where to bind to
  newHttpServer(service) ! Bind(interface = "localhost", port = 8080)

}



