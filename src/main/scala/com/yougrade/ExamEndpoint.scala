package com.yougrade

import akka.actor._
import spray.routing._
import QuizFormats._


// Protocol
object ExamProtocol {

  // Counting
  case object CountRequest

  case class CountResponse(value: Int)

  // Getting
  case class GetExamRequest(key: String)

  // Updating
  case class UpdateAnswerRequest(alternative: Int, question: Int) {
    def toUpdateExamAnswerRequest(key: String) = UpdateExamAnswerRequest(key, alternative, question)
  }

  case class UpdateExamAnswerRequest(key: String, alternative: Int, question: Int)

  // Evaluating
  case class EvalExamRequest(exam: String, quiz: Int)


  // Custom Formats
  implicit val countResponseFormat = jsonFormat1(CountResponse)
  implicit val updateAnswerRequestFormat = jsonFormat2(UpdateAnswerRequest)
  implicit val evalExamRequestFormat = jsonFormat2(EvalExamRequest)
}

import ExamProtocol._


// Provider
object ExamProvider extends Actor {

  import QuizProtocol._
  import akka.util._
  import scala.concurrent.duration._


  implicit private val timeout = Timeout(1.minutes)


  private def getExam(key: String, exams: Map[String, ExamData]) = {
    exams.get(key) match {
      case Some(exam) => exam
      case None => {
        val exam = ExamData(key, Nil)
        val newState = state(exams + (key -> exam))
        context.become(newState)
        exam
      }
    }
  }

  def receive = state(Map.empty)

  def state(exams: Map[String, ExamData]): Receive = {
    case CountRequest => sender ! CountResponse(exams.size)
    case GetExamRequest(key) => {
      val exam = getExam(key, exams)
      sender ! exam
    }
    case UpdateExamAnswerRequest(key, alternative, question) => {
      val original = getExam(key, exams)
      val answers = QuestionAnswer(question, alternative) :: original.answers.filterNot(_.question == question)
      val update = original.copy(answers = answers)
      val newState = state(exams.updated(key, update))
      context.become(newState)
      sender ! update
    }
    case EvalExamRequest(key, quiz) => {
      val exam = getExam(key, exams)
      QuizProvider.quizById(quiz) match{
        case Some(q) => {
          val questions = q.questions.size
          val correct = q.questions.indices.zip(q.questions).map(x => x._1 + 1 -> x._2.answer.alternative)
            .intersect(exam.answers.map(x => x.question -> x.alternative))
            .size
          val incorrect = questions - correct
          val approved = correct > incorrect
          sender ! Grade(questions, correct, incorrect, approved)
        }
        case _ => sender ! Grade(0, 0, 0, false)
      }
    }
  }
}


// Endpoint
trait ExamEndpoint extends Actor with CrossLocationRouteDirectives with CrossDomainOptionsDirectives {
  this: HttpService =>

  import akka.pattern._
  import akka.util._
  import scala.concurrent.duration._
  import spray.http.MediaTypes._
  import spray.http.HttpMethods._

  import QuizProtocol._


  import spray.httpx.SprayJsonSupport._
  import scala.concurrent.ExecutionContext.Implicits.global

  def examProvider: ActorRef

  implicit private val timeout = Timeout(1.minutes)

  def examPaths = {
    pathPrefix("exams") {
      path("count") {
        get {
          respondWithMediaType(`application/json`) {
            complete {
              (examProvider ? CountRequest).mapTo[CountResponse]
            }
          }
        }
      } ~
      path("data" / Segment) {
        key => {
          get {
            jsonpWithParameter("callback") {
              respondWithMediaType(`application/json`) {
                complete {
                  (examProvider ? GetExamRequest(key)).mapTo[ExamData]
                }
              }
            }
          } ~
            post {
              entity(as[UpdateAnswerRequest]) {
                e => {
                  complete {
                    fromObjectCross("*") {
                      val r = e.toUpdateExamAnswerRequest(key)
                      (examProvider ? r).mapTo[ExamData]
                    }
                  }
                }
              }
            } ~
            options {
              complete {
                withOptions("*", OPTIONS, POST)
              }
            }
        }
      } ~
      path("eval") {
        post {
          entity(as[EvalExamRequest]) {
            e => {
              complete {
                fromObjectCross("*") {
                  (examProvider ? e).mapTo[Grade]
                }
              }
            }
          }
        } ~
          options {
            complete {
              withOptions("*", OPTIONS, POST)
            }
          }
      }
    }
  }

}
