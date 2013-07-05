package com.yougrade

import akka.actor._
import spray.routing.HttpService

class Api(val examProvider:ActorRef,val languageProvider:ActorRef) extends Actor
with HttpService
with QuizLanguagesEndpoint
with QuizEndpoint
with ExamEndpoint {
  def actorRefFactory = context
  def receive = runRoute(quizLanguagesPaths ~
    quizPaths ~
    examPaths
  )
}
