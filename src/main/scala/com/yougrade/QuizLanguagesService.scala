package com.yougrade

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._

trait QuizLanguagesService extends HttpService {
  implicit val languageFormat = jsonFormat2(Language)
  private val availableLanguages = List(Language("en","English"),Language("es","Español"))
  val quizLanguagesServiceRoutes =
    path("languages/list") {
      get {
        respondWithMediaType(`application/json`) { 
          complete {
            availableLanguages.toJson.prettyPrint
            
          }
        }
      }
    }
}

case class Language(name:String,title:String)