import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import scala.annotation.tailrec
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day6.dat")
val grid = (for
  (line, row) <- data.reverse.zipWithIndex
  (cell, column) <- line.zipWithIndex
yield ((column + 1, row), cell)).toMap
var visited = List.empty[(Int, Int)]
val startingPosition = grid.find(_._2 == '^').get._1
type Direction = '^' | '>' | 'v' | '<'
type Path = List[((Int, Int), Direction)]
type Grid = Map[(Int, Int), Char]
var direction: Direction  = '^'

def rotate90(direction: Direction): Direction = direction match {
  case '^' => '>'
  case '>' => 'v'
  case 'v' => '<'
  case '<' => '^'
}

def forward(position: (Int, Int), direction: Direction): (Int, Int) = {
  val (x, y) = position
  direction match {
    case '^' => (x, y + 1)
    case '>' => (x + 1, y)
    case 'v' => (x, y - 1)
    case '<' => (x - 1, y)
  }
}

@tailrec
def walk(path: Path, theGrid: Grid = grid, depth: Int = 0): (Path, Boolean) = {
  val segment = path.head
  if (depth % 500 == 0 && path.tail.contains(segment)) {
    // loop detected
    // loop detection is slow, so only run when loop is more likely
    (path, true)
  } else {
    val ((x, y), direction: Direction) = segment
    val (newX, newY) = forward((x, y), direction)
    theGrid.get((newX, newY)) match {
      case Some('#') =>
        val newDirection: Direction = rotate90(direction)
        walk(((x, y), newDirection) :: path, theGrid, depth + 1)
      case Some(_) =>
        walk(((newX, newY), direction) :: path, theGrid, depth + 1)
      case None => (path, false)
    }
  }
}
val path = walk(List((startingPosition, direction)))._1
println("Answer 1: " + path.map(_._1).distinct.length)

println("Answer 2: " + path.reverse.foldLeft((List.empty[(Int, Int)], List.empty[(Int, Int)])) {
  case ((visited, loopObstacles), (position, direction: Direction)) =>
    val obstaclePos = forward(position, direction)
    if (!visited.contains(obstaclePos) && walk(List((position, direction)), grid + (obstaclePos -> '#'))._2) {
      (position :: visited, obstaclePos :: loopObstacles)
    } else (position :: visited, loopObstacles)
  }._2.distinct.length
)
