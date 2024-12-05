import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day4.dat")
val grid = (for
  (line, row) <- data.zipWithIndex
  (cell, column) <- line.zipWithIndex
yield ((column, row), cell)).toMap
val word = "XMAS"
val compassPoints = for
  column <- -1 to 1
  row <- -1 to 1
  if ((column, row) != (0, 0))
yield (column, row)
println("Answer 2: " + grid.map {
  case ((column, row), 'X') =>
    compassPoints.count((x, y) =>
      word.tail.zipWithIndex.map((c, i) => (c, i + 1)).forall((c, i) =>
        grid.get((column + (i * x), row + (i * y))) == Some(c)
      )
    )
  case _ => 0
}.sum)
