import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day2.dat")
val reports = data.map(_.split("\\s+").map(_.toLong).toList)
val deltaLines = reports.map(_
    .sliding2
    .map((x, y) => (x - y))
  )
println("Answer 1: " + deltaLines
  .count(deltas =>
    // Any two adjacent levels differ by at least one and at most three
    deltas.forall(delta => (1 to 3).contains(delta.abs))
    // The levels are either all increasing or all decreasing.
    && (deltas.forall(_ > 0) || deltas.forall(_ <0))
  )
)

println("Answer 2: " + deltaLines
  .count(deltas => {
    // Any two adjacent levels differ by at least one and at most three
    deltas.count(delta => (1 to 3).contains(delta.abs))
    + (deltas.count(_))
    val incrementing =
  }
  )