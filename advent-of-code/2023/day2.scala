import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.9.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day2.dat")

case class Round(red: Int, green: Int, blue: Int)
case class Game(id: Int, rounds: List[Round])

val roundRegex = raw"(\d+) (red|green|blue)".r

def parseGame(line: String) = {
  val List(gameID, roundsS) = line.stripPrefix("Game ").split(":").toList
  val rounds = roundsS.split(";").toList
    .map(_.split(",").toList.map(_.trim match {
      case roundRegex(num, color) => (color, num.toInt)
    }).toMap)
    .map(colorToCount => Round(
      colorToCount.getOrElse("red", 0),
      colorToCount.getOrElse("green", 0),
      colorToCount.getOrElse("blue", 0)
    ))
  Game(gameID.toInt, rounds)
}

def isValidGame(game: Game, maxRed: Int, maxGreen: Int, maxBlue: Int) = {
  !game.rounds.exists {
    case Round(red, green, blue) => red > maxRed || green > maxGreen || blue > maxBlue
  }
}

val games = data.map(parseGame)

println("Answer 1: " + games.filter(isValidGame(_, 12, 13, 14)).map(_.id).sum)

val answer2 = games
  .map(game =>
    game.rounds.map(_.red).max * game.rounds.map(_.green).max * game.rounds.map(_.blue).max
  )
  .sum

println("Answer 2: " + answer2)
