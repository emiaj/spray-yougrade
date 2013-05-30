package com.yougrade

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._

import scala.collection.mutable

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._

import akka.actor.Actor

trait ExamService extends HttpService {
    
  var exams = mutable.Map.empty[String,ExamData]
  
  def getExamData(key:String) = {    
    exams.get(key) match {
        case None => {          
          val exam = ExamData(key,Nil)
          exams(key) = exam
          exam
        }
        case Some(exam) => exam
    }
  }   
  
  def updateAnswer(key:String,question:Int,alternative:Int) = {
    val original = getExamData(key)
    val answers = QuizAnswer(question,alternative)::original.answers.filterNot(_.question == question)
    val update = original.copy(answers=answers)
    exams(key) = update
    update
  }  
  
  implicit val quizAnswerFormat = jsonFormat2(QuizAnswer)
  implicit val examDataFormat = jsonFormat2(ExamData)
  
  val examServiceRoutes =
    path("exams/count") { 
    get {
      respondWithMediaType(`application/json`) {
        complete {
          Map(("count",exams.size)).toJson.prettyPrint
          }
        }
      }
    } ~
    path("exams/data" / PathElement){key => 
      get {
        respondWithMediaType(`application/json`) {
          complete {
            getExamData(key).toJson.prettyPrint
            }
          }
        }~ 
        post {
          formFields('question,'alternative).as(UpdateAnswerCommand){ command =>
            complete {
              updateAnswer(key,command.question,command.alternative).toJson.prettyPrint
              }
          }
        }
      }
}

object ExamsCountCommand

case class UpdateAnswerCommand(question:Int,alternative:Int)
case class ExamDataCommand(exam:String)

case class QuizAnswer(question:Int,alternative:Int)
case class ExamData(key:String,answers:List[QuizAnswer])


