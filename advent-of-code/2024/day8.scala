import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day8.dat")
val grid = (for
  (line, y) <- data.reverse.zipWithIndex
  (cell, x) <- line.zipWithIndex
  if cell != '.'
yield ((x, y), cell))

val (width, height) = (data.map(_.length).max, data.length)

val freq2coords = grid.groupBy(_._2).view.mapValues(_.map(_._1).toList).toList

println("Answer 1: " + freq2coords.flatMap((freq, coords) => 
  coords.combinations(2).toList.flatMap {
    case (x1, y1) :: (x2, y2) :: Nil =>
      val (deltaX, deltaY) = (x2 - x1, y2 - y1)
      List((x1 - deltaX, y1 - deltaY), (x2 + deltaX, y2 + deltaY))
    case _ => ???
  })
  .distinct
  .count((x, y) => x >= 0 && x < width && y >= 0 && y < height)
)