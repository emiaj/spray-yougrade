package com.yougrade

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._

trait QuizService extends HttpService {
  implicit val quizHeaderFormat = jsonFormat5(QuizHeader)
  private val quizzesByLang = List(
      QuizHeader(1,
          "Learning English with \"The Big Bang Theory\"",
          "Show your knowledge on the English language using videos from The Big Bang Theory",
          "http://img.youtube.com/vi/fRaUVp5DfRk/0.jpg",
          "en")) 
  val quizServiceRoutes =    
    path("quizzes" / IntNumber) { id =>
        get {
          respondWithMediaType(`application/json`){
            complete {
              quizzesByLang(0).toJson.prettyPrint
            }
          }
        }
    } ~
    path("quizzes" / PathElement) { lang =>
      get {
        respondWithMediaType(`application/json`) { 
          complete {            
            quizzesByLang.toJson.prettyPrint
          }
        }
      }
    }
  	
}
case class QuizHeader(id:Int,title:String,description:String,thumbnail:String,lang:String)