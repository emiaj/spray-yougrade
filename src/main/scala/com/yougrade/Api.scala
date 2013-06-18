package com.yougrade

import akka.actor._
import spray.routing.HttpService

class Api extends Actor
with HttpService
with QuizLanguagesService
with QuizService
with ExamService {
  def actorRefFactory = context
  def receive = runRoute(quizLanguagesServiceRoutes ~
    quizServiceRoutes ~
    examServiceRoutes
  )
}