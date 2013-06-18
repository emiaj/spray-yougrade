package com.yougrade

import scala.collection.mutable
import spray.http.MediaTypes._
import spray.json._
import spray.routing.Directive._
import spray.routing._
import spray.routing.directives._
import spray.http.HttpMethods._
import spray.httpx._
import QuizFormats._

trait ExamService extends HttpService
with CrossLocationRouteDirectives
with CrossDomainOptionsDirectives
with SprayJsonSupport
{
  implicit val examsCountFormat = jsonFormat1(ExamsCount)
  implicit val updateAnswerCommandFormat = jsonFormat2(UpdateAnswerCommand)
  implicit val evalExamCommandFormat = jsonFormat2(EvalExamCommand)

  var exams = mutable.Map.empty[String, ExamData]

  def getExamData(key: String) = {
    exams.get(key) match {
      case None => {
        val exam = ExamData(key, Nil)
        exams(key) = exam
        exam
      }
      case Some(exam) => exam
    }
  }

  def updateAnswer(key: String, question: Int, alternative: Int) = {
    val original = getExamData(key)
    val answers = QuestionAnswer(question, alternative) :: original.answers.filterNot(_.question == question)
    val update = original.copy(answers = answers)
    exams(key) = update
    update
  }


  def evalExam(key: String, quiz: Int) = {
    val exam = getExamData(key)
    QuizProvider.list.get(quiz) match {
      case Some(q) => {
        val questions = q.questions.size
        val correct = q.questions.indices.zip(q.questions).map(x => x._1 + 1 -> x._2.answer.alternative)
          .intersect(exam.answers.map(x => x.question -> x.alternative))
          .size
        val incorrect = questions - correct
        val approved = correct > incorrect
        Grade(questions, correct, incorrect, approved)
      }
      case _ => Grade(0, 0, 0, false)
    }
  }

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
      path("exams/data" / Segment) {
        key =>
          get {
            jsonpWithParameter("callback") {
              respondWithMediaType(`application/json`) {
                complete {
                  getExamData(key).toJson.prettyPrint
                }
              }
            }
          } ~
            post {
              entity(as[UpdateAnswerCommand]) {
                command =>
                  complete {
                    fromObjectCross("*") {
                      updateAnswer(key, command.question, command.alternative).toJson.prettyPrint
                    }
                  }
              }
            } ~
            MethodDirectives.method(OPTIONS) {
              complete {
                withOptions("*", OPTIONS, POST)
              }
            }
      } ~
    path("exams/eval"){
      post{
        entity(as[EvalExamCommand]){
          command =>
            complete{
              fromObjectCross("*"){
                evalExam(command.exam,command.quiz).toJson.prettyPrint
              }
            }
        }
      } ~
      MethodDirectives.method(OPTIONS) {
        complete {
          withOptions("*", OPTIONS, POST)
        }
      }
    }
}

case class UpdateAnswerCommand(alternative: Int, question: Int)

case class EvalExamCommand(exam:String,quiz:Int)

case class ExamsCount(count: Int)




