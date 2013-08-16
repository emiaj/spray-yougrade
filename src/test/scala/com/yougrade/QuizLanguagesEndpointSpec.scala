package com.yougrade

import akka.actor.Actor
import akka.testkit.TestActorRef

import com.yougrade.QuizFormats._
import com.yougrade.QuizLanguagesProtocol._

import org.specs2.mutable.Specification

import spray.testkit.Specs2RouteTest
import spray.json._
import spray.http._
import spray.http.MediaTypes._

class QuizLanguagesEndpointSpec extends Specification with Specs2RouteTest with QuizLanguagesEndpoint {

  def expectedLanguages =  Language("en","English") :: Nil

  def actorRefFactory = system

  def languageProvider = TestActorRef(new Actor {
    def receive = {
      case AvailableLanguages => sender ! expectedLanguages
    }
  })

  "The endpoint" should {
    "leave GET requests to other paths unhandled" in {
      Get("/nada") ~> quizLanguagesPaths ~> check { handled must beFalse }
    }
    "GET requests to 'languages/list'" should {
      "be handled" in {
        Get("/languages/list") ~> quizLanguagesPaths ~> check {
          handled must beTrue
        }
      }
      "return available languages" in {
        Get("/languages/list") ~> quizLanguagesPaths ~> check {
          entityAs[String] mustEqual expectedLanguages.toJson.prettyPrint
        }
      }
      "respond with the json media type" in {
        Get("/languages/list") ~> quizLanguagesPaths ~> check {
          mediaType mustEqual `application/json`
        }
      }
    }
    "GET requests to 'languages/list' that have the callback parameter" should {
      "be handled" in {
        Get("/languages/list?callback=JS_CALLBACK") ~> quizLanguagesPaths ~> check {
          handled must beTrue
        }
      }
      "return available languages wrapped in callback fun" in {
        Get("/languages/list?callback=JS_CALLBACK") ~> quizLanguagesPaths ~> check {
          entityAs[String] mustEqual s"JS_CALLBACK(${expectedLanguages.toJson.prettyPrint})"
        }
      }
      "respond with the javascript media type" in {
        Get("/languages/list?callback=JS_CALLBACK") ~> quizLanguagesPaths ~> check {
          mediaType mustEqual `application/javascript`
        }
      }
    }
  }
}
