package com.yougrade

case class Alternative(text: String)

case class Answer(alternative: Int)

case class QuestionHeader(description: String, video: String, alternatives: List[Alternative])

case class Question(header: QuestionHeader, answer: Answer)

case class QuizHeader(id: Int, title: String, description: String, thumbnail: String, lang: String)

case class Quiz(header: QuizHeader, questions: List[Question])
