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
          List(
              QuizQuestion(
                  "In this episode, Sheldon proposes to Raj a variation for the classic \"Jokempo\" game: instead of \"rock-paper-scissors\", the variation is \"rock-paper-scissors-lizard-spock\". In his explanation for the game rules, Sheldon points out that paper beats Spock. How can Spock be defeated by paper?",
                  "iapcKVn7DdY",
                  List(
                      QuizAlternative("A large sheet of paper can wrap Spock, in the same way that it covers rock."),
                      QuizAlternative("Spock's fingers are not as sharp as scissors, and paper can cut his finger deep."),
                      QuizAlternative("Being Spock a scientist, a paper (academic research) can refute one of his theories."),
                      QuizAlternative("Paper doesn't beat Spock. In fact, Sheldon points out that Spock against paper ends in a tie."))
                      ),
              QuizQuestion(
                  "In \"The Friendship Algorithm\", Sheldon is learning about friendship and draws a flowchart in his white board. He then invites Kripke to go out with him in some activity. Sheldon uses the friendship algorithm, but every activity proposed by Kripke is rejected by Sheldon, and at some point he gets stuck in an infinite loop. How Howard managed to help him get out of the loop?",
                  "w7j7E7J3f6E",
                  List(
                      QuizAlternative("Howard convinced Sheldon to practice rock climbing with Kripke."),
                      QuizAlternative("By drawing a loop counter and an escape to the least objectionable activity."),
                      QuizAlternative("In fact, Howard doesn't help Sheldon at all. Sheldon decides to go rock climbing by itself."),
                      QuizAlternative("Sheldon doesn't get out of the infinite loop. He just get a break to do some internet search before finishing the friendship algorithm path.")                      
                      )),
              QuizQuestion(
                  "At the University cafeteria, Sheldon is discussing with his friend Leonard the problem with teleportation. At the end, Leonard seems to agree with Sheldon that there is a problem. What problem they are talking about?",
                  "PQZzSrAIp-E",
                  List(
                      QuizAlternative("Both agreed that it would be a problem if the teletransporter had to disintegrate the original Sheldon in order to create the new Sheldon."),
                      QuizAlternative("Both agreed that it would be a problem if the new Sheldon created by the teletransporter had no new improvements compared to the old Sheldon."),
                      QuizAlternative("Sheldon said it would be a problem if the teletransporter had to disintegrate the original Sheldon in order to create the new Sheldon, and Leonard just pretended to care about it."),
                      QuizAlternative("Sheldon said he would never use a teletransporter, because this would disintegrate the original Sheldon in order to create the new Sheldon. Leonard, on the other hand, thought it was a problem if the new Sheldon was exactly the same as the old Sheldon (without improvements), but Sheldon didn't understand Leonard's sarcasm.")                      
                      )),
              QuizQuestion(
                  "In this video, somebody is auctioning a time machine replica from the \"Time Machine\" movie. Leonard bid $800, and when the auction ends, Sheldon is surprised that no one else bid, since it's a piece of sci-fi movie memorabilia. In the end of the video, Sheldon says he understood why no-one else bid. Why he said so?",
                  "fRaUVp5DfRk",
                  List(
                      QuizAlternative("Because instead of a miniature, Sheldon found the time machine replica to be in fact full-sized and extravagant."),
                      QuizAlternative("Because Sheldon saw it wasn't a real time machine."),
                      QuizAlternative("Because it was nothing like the time machine in the movie."),
                      QuizAlternative("Because the time machine was damaged.")                      
                      )                  
              ))
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
