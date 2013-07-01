package com.yougrade

import akka.actor._
import spray.routing.HttpService

trait Api extends Actor
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
