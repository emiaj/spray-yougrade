package com.yougrade

import spray.json._

object QuizFormats extends DefaultJsonProtocol {
  implicit val alternativeFormat = jsonFormat1(Alternative)

  implicit val answerFormat = jsonFormat1(Answer)

  implicit val questionHeaderFormat = jsonFormat3(QuestionHeader)

  implicit val questionFormat = jsonFormat2(Question)

  implicit val quizHeaderFormat = jsonFormat5(QuizHeader)

  implicit val quizFormat = jsonFormat2(Quiz)

  implicit val quizResponseFormat = jsonFormat2(QuizDetails)

  implicit val languageFormat = jsonFormat2(Language)

  implicit val gradeFormat = jsonFormat4(Grade)

  implicit val questionAnswerFormat = jsonFormat2(QuestionAnswer)

  implicit val examDataFormat = jsonFormat2(ExamData)

}