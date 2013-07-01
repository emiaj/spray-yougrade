package com.yougrade
import akka.actor._
import spray.routing._
import QuizFormats._


// Commands
object QuizLanguagesCommands{
  case object AvailableLanguages
}

// Provider
object QuizLanguagesProvider extends Actor{
  import com.yougrade.QuizLanguagesCommands.AvailableLanguages
  private val availableLanguages = List(Language("en", "English"))
  def receive = {
    case AvailableLanguages => sender ! availableLanguages
  }
}

// Endpoint
trait QuizLanguagesEndpoint extends Actor  {
  this: HttpService =>

  import akka.pattern._
  import akka.util._
  import scala.concurrent.duration._
  import QuizLanguagesCommands._
  import spray.http.MediaTypes._


  import spray.httpx.SprayJsonSupport._
  import scala.concurrent.ExecutionContext.Implicits.global

  def quizLanguagesActor:ActorRef

  implicit val timeout = Timeout(1.minutes)

  def quizLanguagesServiceRoutes = {
    path("languages" / "list"){
      get {
        jsonpWithParameter("callback"){
          respondWithMediaType(`application/json`){
            complete {
              (quizLanguagesActor ? AvailableLanguages).mapTo[List[Language]]
            }
          }
        }
      }
    }
  }
}
