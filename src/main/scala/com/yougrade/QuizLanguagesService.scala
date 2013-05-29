package com.yougrade

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._

trait QuizLanguagesService extends HttpService {
  implicit val languageFormat = jsonFormat1(Language)
  val availableLanguages = List(Language("en")).toJson 
  val quizLanguagesRoutes =
    path("languages/list") {
      get {
        respondWithMediaType(`application/json`) { 
          complete {
            availableLanguages.toString
          }
        }
      }
    }
}
case class Language(name:String)