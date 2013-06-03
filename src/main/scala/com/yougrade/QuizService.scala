package com.yougrade

import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import QuizFormats._

trait QuizService extends HttpService {


  private val quizzes = QuizProvider.list

  private val quizzesByLang = quizzes.map(x => x._2.header).groupBy(_.lang)



  val quizServiceRoutes =
    path("quizzes" / IntNumber) {
      id =>
        get {
          jsonpWithParameter("callback") {
            respondWithMediaType(`application/json`) {
              complete {
                quizzes.get(id) match {
                  case Some(Quiz(header,questions)) => QuizResponse(header,questions.map(_.header)).toJson.prettyPrint
                  case _ => ""
                }
              }
            }
          }
        }
    } ~
      path("quizzes" / PathElement) {
        lang =>
          get {
            jsonpWithParameter("callback") {
              respondWithMediaType(`application/json`) {
                complete {
                  quizzesByLang.get(lang).toJson.prettyPrint
                }
              }
            }
          }
      }
}


