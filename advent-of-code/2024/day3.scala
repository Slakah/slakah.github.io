import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day3.dat")
val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)""".r
println("Answer 1: " + data.flatMap(line => mulRegex.findAllIn(line).map {
  case mulRegex(x, y) => x.toInt * y.toInt
}).sum)