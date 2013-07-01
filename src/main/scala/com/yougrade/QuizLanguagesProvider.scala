package com.yougrade

import akka.actor.Actor
import com.yougrade.QuizLanguagesCommands.AvailableLanguages

object QuizLanguagesProvider extends Actor{

  private val availableLanguages = List(Language("en", "English"))

  def receive = {
    case AvailableLanguages => sender ! availableLanguages
  }
}
