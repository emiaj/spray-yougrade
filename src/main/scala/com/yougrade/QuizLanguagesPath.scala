package com.yougrade

import spray.routing._
import akka.pattern._
import akka.util._
import scala.concurrent.duration._
import QuizLanguagesCommands._
import spray.http.MediaTypes._
import akka.actor._

trait QuizLanguagesPath extends Actor  {
  this: HttpService =>

  import QuizFormats._
  import spray.httpx.SprayJsonSupport._
  import scala.concurrent.ExecutionContext.Implicits.global

  def quizLanguagesActor:ActorRef

  implicit val timeout = Timeout(1.minutes)

  def quizLanguagesServiceRoutes = {
    path("languages" / "list"){
      get {
        jsonpWithParameter("callback"){
          respondWithMediaType(`application/json`){
            complete {
              (quizLanguagesActor ? AvailableLanguages).mapTo[List[Language]]
            }
          }
        }
      }
    }
  }
}