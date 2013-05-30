package com.yougrade
import akka.actor._
import spray.routing._
class Api extends Actor 
	with HttpServiceActor 
	with QuizLanguagesService 
	with QuizService
{
	def receive = runRoute(quizLanguagesServiceRoutes ~ quizServiceRoutes)
}