import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day3.dat")
val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)""".r
println("Answer 1: " + data
  .flatMap(line => mulRegex.findAllIn(line))
  .map { case mulRegex(x, y) => x.toInt * y.toInt }
  .sum
)

val opRegex = """(do|don\'t|mul)\(([\d,]*)\)""".r
val mulArgRegex = """(\d{1,3}),(\d{1,3})""".r
println("Answer 2: " + data
  .flatMap(line => opRegex.findAllIn(line))
  .foldLeft((true, 0)) { case ((enabled, sum), opMatch) => opMatch match {
    case opRegex("mul", mulArgRegex(x, y)) if enabled => (enabled, sum + x.toInt * y.toInt)
    case opRegex("mul", _) => (enabled, sum)
    case opRegex("do", "") => (true, sum)
    case opRegex("don't", "") => (false, sum)
  }}
  ._2
)