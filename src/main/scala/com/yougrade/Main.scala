package com.yougrade

import java.io.File
import akka.actor._
import akka.io.IO
import spray.can.Http
import org.eligosource.eventsourced.core._
import org.eligosource.eventsourced.journal.leveldb._

object Main extends App {

  implicit val system = ActorSystem("yougrade-system")

  val journal: ActorRef = LeveldbJournalProps(new File("target/yougrade-journal"), native = false).createJournal

  val extension: EventsourcingExtension = EventsourcingExtension(system,journal)

  val languageProvider = system.actorOf(Props(QuizLanguagesProvider))

  val examProvider = extension.processorOf(Props(new ExamProvider with Receiver with Eventsourced {val id = 1} ), Some("exam-processor"))

  val service = system.actorOf(Props(new Api(examProvider, languageProvider) {
  }), "yougrade-service")


  extension.recover()


  val host = "0.0.0.0"
  val port = Option(System.getenv("PORT")).getOrElse("8080").toInt
  IO(Http) ! Http.Bind(service, host, port = port)
}



