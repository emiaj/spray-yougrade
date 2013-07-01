package com.yougrade

import akka.actor._

trait Api extends Actor
with QuizLanguagesEndpoint
with QuizService
with ExamService {
  def actorRefFactory = context
  def receive = runRoute(quizLanguagesServiceRoutes ~
    quizServiceRoutes ~
    examServiceRoutes
  )
}
