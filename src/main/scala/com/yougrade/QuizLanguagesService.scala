package com.yougrade

import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import QuizFormats._

trait QuizLanguagesService extends HttpService {
  private val availableLanguages = List(Language("en", "English"))
  val quizLanguagesServiceRoutes =
    path("languages/list") {
      get {
        jsonpWithParameter("callback") {
          respondWithMediaType(`application/json`) {
            complete {
              availableLanguages.toJson.prettyPrint
            }
          }
        }
      }
    }
}