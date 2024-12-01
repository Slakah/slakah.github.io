import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.9.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day1.dat")

def column(i: Int) = data.map(_.split("\\s+")(i).toLong).toList
val lefts = column(0)
val rights = column(1)
println("Answer 1: " + lefts.sorted.zip(rights.sorted)
  .map { case (left, right) => (left - right).abs }
  .sum
)

val rightsFreq = rights.groupBy(identity).fmap(_.size)
println("Answer 2: " + lefts
  .map(left => left * (rightsFreq.get(left).getOrElse(0)))
  .sum
)