package com.yougrade
import akka.actor._
import spray.routing._
import QuizFormats._


// Protocol
object QuizLanguagesProtocol{
  case object AvailableLanguages
}
import QuizLanguagesProtocol._


// Provider
object QuizLanguagesProvider extends Actor{
  private val availableLanguages = List(Language("en", "English"))
  def receive = {
    case AvailableLanguages => sender ! availableLanguages
  }
}

// Endpoint
trait QuizLanguagesEndpoint extends HttpService  {

  import akka.pattern._
  import akka.util._
  import scala.concurrent.duration._
  import spray.http.MediaTypes._

  import spray.httpx.SprayJsonSupport._
  import scala.concurrent.ExecutionContext.Implicits.global

  def languageProvider:ActorRef

  implicit private val timeout = Timeout(1.minutes)

  def quizLanguagesPaths = {
    path("languages" / "list"){
      get {
        jsonpWithParameter("callback"){
          respondWithMediaType(`application/json`){
            complete {
              (languageProvider ? AvailableLanguages).mapTo[List[Language]]
            }
          }
        }
      }
    }
  }
}
