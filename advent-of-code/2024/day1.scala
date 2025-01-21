import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.13.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day1.dat")
val List(lefts, rights) = data.toList.map(_.split("\\s+").map(_.toLong)).transpose
println("Answer 1: " + lefts.sorted.zip(rights.sorted)
  .map { case (left, right) => (left - right).abs }
  .sum
)

val rightsFreq = rights.groupBy(identity).fmap(_.size)
println("Answer 2: " + lefts
  .map(left => left * (rightsFreq.get(left).getOrElse(0)))
  .sum
)