package com.yougrade

import scala.collection.mutable
import spray.http.MediaTypes._
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.routing.Directive._
import spray.routing.HListDeserializer._
import spray.routing._
import spray.routing.directives._
import spray.routing.directives.MethodDirectives._
import spray.http.HttpMethods
trait ExamService extends HttpService 
with CrossLocationRouteDirectives 
with CrossDomainOptionsDirectives
with spray.httpx.SprayJsonSupport
{
    
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
  implicit val examsCountFormat = jsonFormat1(ExamsCount)  
  implicit val updateAnswerCommandFormat = jsonFormat2(UpdateAnswerCommand)
  
  val examServiceRoutes =
    path("exams/count") { 
    get {
      respondWithMediaType(`application/json`) {
        complete {              
          ExamsCount(exams.size).toJson.prettyPrint
          }
        }
      }
    } ~
    path("exams/data" / PathElement){key => 
      get {
        jsonpWithParameter("callback") {
          respondWithMediaType(`application/json`) {
            complete {
              getExamData(key).toJson.prettyPrint
              }
            }
          }
        }~ 
        post {
          entity(as[UpdateAnswerCommand]){ command =>            
              complete {  
              fromObjectCross("*"){                      
            	 updateAnswer(key,command.question,command.alternative).toJson.prettyPrint               
            	}
            }
          }
        }~
        MethodDirectives.method(HttpMethods.OPTIONS) {
          complete{
            withOptions("*","POST,OPTIONS")
          }
        }
      }
}

object ExamsCountCommand

case class UpdateAnswerCommand(alternative:Int,question:Int)

case class ExamsCount(count:Int)
case class QuizAnswer(question:Int,alternative:Int)
case class ExamData(key:String,answers:List[QuizAnswer])


