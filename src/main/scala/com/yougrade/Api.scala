package com.yougrade

import akka.actor._

trait Api extends Actor
with QuizLanguagesEndpoint
with QuizService
with ExamEndpoint {
  def actorRefFactory = context
  def receive = runRoute(quizLanguagesPaths ~
    quizServiceRoutes ~
    examPaths
  )
}
