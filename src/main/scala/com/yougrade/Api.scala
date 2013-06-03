package com.yougrade

import akka.actor._
import spray.routing._

class Api extends Actor
with HttpServiceActor
with QuizLanguagesService
with QuizService
with ExamService {
  def receive = runRoute(quizLanguagesServiceRoutes ~
    quizServiceRoutes ~
    examServiceRoutes
  )
}