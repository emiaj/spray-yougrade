package com.yougrade

import spray.routing._

import akka.actor._

object QuizLanguagesProvider{
  def availableLanguages = List(Language("en", "English"))
}

object QuizLanguagesCommands{
  case class AvailableLanguages()
}

class QuizLanguagesActor extends Actor {
  import QuizLanguagesCommands._
  def receive = {
    case AvailableLanguages => {
      sender ! QuizLanguagesProvider.availableLanguages
    }
  }
}

trait QuizLanguagesPath {
  this: HttpService =>

  import akka.pattern._
  import akka.util._
  import scala.concurrent.duration._
  import QuizLanguagesCommands._
  import QuizFormats._
  import spray.http.MediaTypes._
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