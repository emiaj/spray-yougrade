package com.yougrade

import scala.collection.mutable

import akka.actor.Actor

class ExamStoreActor extends Actor {
    
  var exams = mutable.Map.empty[String,ExamData]
  
  def getExamData(key:String) = {    
    exams.get(key) match {
        case None => {          
          val exam = ExamData(key,Nil)
          exams(key) = exam
          exam
        }
        case Some(exam) => exam
    }
  }   
  
  def updateAnswer(key:String,question:Int,alternative:Int) = {
    val original = getExamData(key)
    val answers = QuizAnswer(question,alternative)::original.answers.filterNot(_.question == question)
    val update = original.copy(answers=answers)
    exams(key) = update
    update
  }  
  
  def receive = {
    case ExamsCountCommand => exams.size
    case ExamDataCommand(key) => getExamData(key)
    case UpdateAnswerCommand(key,question,alternative) => updateAnswer(key,question,alternative)
  }
}

object ExamsCountCommand

case class UpdateAnswerCommand(key:String,question:Int,alternative:Int)
case class ExamDataCommand(exam:String)

case class QuizAnswer(question:Int,alternative:Int)
case class ExamData(key:String,answers:List[QuizAnswer])


