package com.yougrade

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._

trait QuizService extends HttpService {
  implicit val quizHeaderFormat = jsonFormat5(QuizHeader)
  implicit val quizAlternativeFormat = jsonFormat1(QuizAlternative)
  implicit val quizQuestionFormat = jsonFormat3(QuizQuestion)  
  implicit val quizFormat = jsonFormat2(Quiz)
  
  private val quizzes = List(
      Quiz(QuizHeader(1,
          "Learning English with \"The Big Bang Theory\"",
          "Show your knowledge on the English language using videos from The Big Bang Theory",
          "http://img.youtube.com/vi/fRaUVp5DfRk/0.jpg",
          "en"),
          List(QuizQuestion(
              "In this episode, Sheldon proposes to Raj a variation for the classic \"Jokempo\" game: instead of \"rock-paper-scissors\", the variation is \"rock-paper-scissors-lizard-spock\". In his explanation for the game rules, Sheldon points out that paper beats Spock. How can Spock be defeated by paper?",
              "iapcKVn7DdY",
              Nil))
              )
      )
  
  private val quizzesByLang = quizzes.map(_.header)
  
  val quizServiceRoutes =    
    path("quizzes" / IntNumber) { id =>
        get {
          respondWithMediaType(`application/json`){
            complete {
              quizzes(0).toJson.prettyPrint
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
case class QuizAlternative(text:String)
case class QuizQuestion(description:String,video:String,alternatives:List[QuizAlternative])
case class Quiz(header:QuizHeader,questions:List[QuizQuestion])
