package com.yougrade

import spray.json._
import DefaultJsonProtocol._

object QuizFormats {
  implicit val alternativeFormat = jsonFormat1(Alternative)

  implicit val answerFormat = jsonFormat1(Answer)

  implicit val questionHeaderFormat = jsonFormat3(QuestionHeader)

  implicit val questionFormat = jsonFormat2(Question)

  implicit val quizHeaderFormat = jsonFormat5(QuizHeader)

  implicit val quizFormat = jsonFormat2(Quiz)

}