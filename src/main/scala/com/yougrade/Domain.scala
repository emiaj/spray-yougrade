package com.yougrade

case class Alternative(text: String)

case class Answer(alternative: Int)

case class QuestionHeader(description: String, video: String, alternatives: List[Alternative])

case class Question(header: QuestionHeader, answer: Answer)

case class QuizHeader(id: Int, title: String, description: String, thumbnail: String, lang: String)

case class Quiz(header: QuizHeader, questions: List[Question])

case class QuizDetails(header:QuizHeader,questions:List[QuestionHeader])

case class Language(name: String, title: String)

case class QuestionAnswer(question:Int,alternative:Int)

case class ExamData(key: String, answers: List[QuestionAnswer])

case class Grade(questions:Int,correct:Int,incorrect:Int,approved:Boolean)