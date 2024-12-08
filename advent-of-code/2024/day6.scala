import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day6.dat")
val grid = (for
  (line, row) <- data.reverse.zipWithIndex
  (cell, column) <- line.zipWithIndex
yield ((column + 1, row), cell)).toMap
var path = List.empty[(Int, Int)]
var (x, y) = grid.find(_._2 == '^').get._1
var direction: '^' | '>' | 'v' | '<'  = '^'

while (grid.contains((x, y))) {
  visited = (x, y) :: visited
  val (newX, newY) = direction match {
    case '^' => (x, y + 1)
    case '>' => (x + 1, y)
    case 'v' => (x, y - 1)
    case '<' => (x - 1, y)
  }
  grid.get((newX, newY)) match {
    case Some('#') =>
      direction = (direction match {
        case '^' => '>'
        case '>' => 'v'
        case 'v' => '<'
        case '<' => '^'
      })
    case _ =>
      x = newX
      y = newY
  }
}

println("Answer 1: " + visited.distinct.length)
