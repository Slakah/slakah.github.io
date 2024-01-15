import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.9.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day1.dat")

println("Answer 1: " + data.map { line =>
  line.find(_.isDigit).get.asDigit * 10 + line.reverse.find(_.isDigit).get.asDigit
}.sum)

def maybeWordDigitToDigit(s: String) = s match {
    case "1" | "one" => Some(1)
    case "2" | "two" => Some(2)
    case "3" | "three" => Some(3)
    case "4" | "four" => Some(4)
    case "5" | "five" => Some(5)
    case "6" | "six" => Some(6)
    case "7" | "seven" => Some(7)
    case "8" | "eight" => Some(8)
    case "9" | "nine" => Some(9)
    case _ => None
  }

def calibrationValues(line: String) = {
  val indexedFoundDigits = List(1, 3, 4, 5)
    .map(line.sliding(_).map(maybeWordDigitToDigit).zipWithIndex.toList
      .flatMap {
        case (Some(num), i) => Some((num, i))
        case (None, _) => None
      }
    )
    .flatten
  val calibration1 = indexedFoundDigits.minBy(_._2)._1
  val calibration2 = indexedFoundDigits.maxBy(_._2)._1
  calibration1 * 10 + calibration2
}

println("Answer 2: " + data.map(calibrationValues).sum)