package com.yougrade

import akka.actor._
import akka.io.IO
import spray.can.Http

object Main extends App {

  implicit val system = ActorSystem("yougrade-system")

  // create and start our service actor
  val service = system.actorOf(Props[Api], "yougrade-service")

  // create a new HttpServer using our handler tell it where to bind to
  val host = "0.0.0.0"
  val port = Option(System.getenv("PORT")).getOrElse("8080").toInt


  IO(Http) ! Http.Bind(service, host, port = 8080)

}



