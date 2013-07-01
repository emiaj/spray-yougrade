package com.yougrade

import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import QuizFormats._

trait QuizService extends HttpService {

  private lazy val quizzes = QuizProvider.list

  private val quizzesByLang = quizzes.map(x => x._2.header).groupBy(_.lang)

  val quizServiceRoutes =
      path("quizzes" / IntNumber) {
        id =>
          get {
            jsonpWithParameter("callback") {
              respondWithMediaType(`application/json`) {
                complete {
                  quizzes.get(id) match {
                    case Some(Quiz(header,questions)) => QuizDetails(header,questions.map(_.header)).toJson.prettyPrint
                    case None => StatusCodes.NotFound
                  }
                }
              }
            }
          }
      }~
    path("quizzes" / Segment) {
      lang =>
        get {
          jsonpWithParameter("callback") {
            respondWithMediaType(`application/json`) {
              complete {
                quizzesByLang.get(lang) match {
                  case Some(x) => x.toJson.prettyPrint
                  case None => StatusCodes.NotFound
                }
              }
            }
          }
        }
    }
}


