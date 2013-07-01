package com.yougrade

object QuizProvider {

  private val quizzes = List(
    Quiz(QuizHeader(1,
      "Learn English with \"The Big Bang Theory\"",
      "Show your knowledge on the English language using videos from The Big Bang Theory",
      "http://img.youtube.com/vi/fRaUVp5DfRk/0.jpg",
      "en"),
      List(
        Question(
          QuestionHeader(
            "In this episode, Sheldon proposes to Raj a variation for the classic \"Jokempo\" game: instead of \"rock-paper-scissors\", the variation is \"rock-paper-scissors-lizard-spock\". In his explanation for the game rules, Sheldon points out that paper beats Spock. How can Spock be defeated by paper?",
            "iapcKVn7DdY",
            List(
              Alternative("A large sheet of paper can wrap Spock, in the same way that it covers rock."),
              Alternative("Spock's fingers are not as sharp as scissors, and paper can cut his finger deep."),
              Alternative("Being Spock a scientist, a paper (academic research) can refute one of his theories."),
              Alternative("Paper doesn't beat Spock. In fact, Sheldon points out that Spock against paper ends in a tie."))
          ),
          Answer(3)),
        Question(
          QuestionHeader(
            "In \"The Friendship Algorithm\", Sheldon is learning about friendship and draws a flowchart in his white board. He then invites Kripke to go out with him in some activity. Sheldon uses the friendship algorithm, but every activity proposed by Kripke is rejected by Sheldon, and at some point he gets stuck in an infinite loop. How Howard managed to help him get out of the loop?",
            "w7j7E7J3f6E",
            List(
              Alternative("Howard convinced Sheldon to practice rock climbing with Kripke."),
              Alternative("By drawing a loop counter and an escape to the least objectionable activity."),
              Alternative("In fact, Howard doesn't help Sheldon at all. Sheldon decides to go rock climbing by itself."),
              Alternative("Sheldon doesn't get out of the infinite loop. He just get a break to do some internet search before finishing the friendship algorithm path."))
          ),
          Answer(2)),
        Question(
          QuestionHeader(
            "At the University cafeteria, Sheldon is discussing with his friend Leonard the problem with teleportation. At the end, Leonard seems to agree with Sheldon that there is a problem. What problem they are talking about?",
            "PQZzSrAIp-E",
            List(
              Alternative("Both agreed that it would be a problem if the teletransporter had to disintegrate the original Sheldon in order to create the new Sheldon."),
              Alternative("Both agreed that it would be a problem if the new Sheldon created by the teletransporter had no new improvements compared to the old Sheldon."),
              Alternative("Sheldon said it would be a problem if the teletransporter had to disintegrate the original Sheldon in order to create the new Sheldon, and Leonard just pretended to care about it."),
              Alternative("Sheldon said he would never use a teletransporter, because this would disintegrate the original Sheldon in order to create the new Sheldon. Leonard, on the other hand, thought it was a problem if the new Sheldon was exactly the same as the old Sheldon (without improvements), but Sheldon didn't understand Leonard's sarcasm.")
            )),
          Answer(4)),
        Question(
          QuestionHeader(
            "In this video, somebody is auctioning a time machine replica from the \"Time Machine\" movie. Leonard bid $800, and when the auction ends, Sheldon is surprised that no one else bid, since it's a piece of sci-fi movie memorabilia. In the end of the video, Sheldon says he understood why no-one else bid. Why he said so?",
            "fRaUVp5DfRk",
            List(
              Alternative("Because instead of a miniature, Sheldon found the time machine replica to be in fact full-sized and extravagant."),
              Alternative("Because Sheldon saw it wasn't a real time machine."),
              Alternative("Because it was nothing like the time machine in the movie."),
              Alternative("Because the time machine was damaged.")
            )),
          Answer(1))
      )
    )
  )

  lazy val list =  quizzes.map(x => (x.header.id, x)).toMap
}