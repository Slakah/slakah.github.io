import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.9.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day3.dat")

final case class Coord(column: Int, row: Int)

object Coord {
  implicit def eqInstance: Eq[Coord] = Eq.fromUniversalEquals
}

def parseGrid(lines: List[String]) = {
  lines.zipWithIndex.map { (line, row) =>
    line.zipWithIndex.map((cell, column) => (Coord(column, row), cell)).toList
  }.toList
}

val grid = parseGrid(data.toList)
val coordToSymbols = grid.flatten.filter {
  case (_, '.') => false
  case (_, c) if c.isDigit => false
  case (_, _)  => true
}.toMap

def splitWhen[A](list: List[A], f: A => Boolean): List[List[A]] = {
  list.reverse.foldLeft((List.empty[List[A]], true)) {
    case ((Nil, _), e) if f(e) => (Nil, true)
    case ((Nil, _), e) => (List(List(e)), false)
    case ((head :: tail, _), e) if f(e) => (head :: tail, true)
    case ((head :: tail, false), e) => ((e :: head) :: tail, false)
    case ((head :: tail, true), e) => (List(e) :: head :: tail, false)
  }._1
}

val gearCoords = coordToSymbols.filter(_._2 == '*').keys.toSet

val results = grid
  .map(row => splitWhen(row, !_._2.isDigit).map(_.map { case (coord, c) => (coord, c.asDigit)}))
  .flatMap(_.foldLeft(List.empty[(Long, Option[Coord])]) { (acc, coordToDigit) =>
    val firstCoord = coordToDigit.head._1
    val lastCoord = coordToDigit.last._1
    val coords = List(
      Coord(firstCoord.column - 1, firstCoord.row - 1),
      Coord(firstCoord.column - 1, firstCoord.row),
      Coord(firstCoord.column - 1, firstCoord.row + 1),
      Coord(lastCoord.column + 1, firstCoord.row - 1),
      Coord(lastCoord.column + 1, firstCoord.row),
      Coord(lastCoord.column + 1, firstCoord.row + 1),
    ) ++ coordToDigit.flatMap { case (coord, _) => List(
      Coord(coord.column, firstCoord.row - 1),
      Coord(coord.column, firstCoord.row + 1),
    )}
    if (coords.exists(coordToSymbols.keys.toSet.contains_)) {
      val len = coordToDigit.length
      val value = coordToDigit.zipWithIndex.map { case ((_, digit), i) => digit * scala.math.pow(10, len - i - 1).intValue }.sum
      (value, coords.find(gearCoords)) :: acc
    } else {
      acc
    }
  }
)

println("Answer 1: " + results.map(_._1).sum)

val answer2 = results.filter(_._2.isDefined).groupBy(_._2).view.mapValues(_.map(_._1)).values

println(answer2.toList.mkString("\n"))

println("Answer 1: " + answer2.toList.flatMap {
  case x1 :: x2 :: _ => Some(x1 * x2)
  case _ => None
}.sum)
// grid.map(_.splitWhen(!_.isDigit))