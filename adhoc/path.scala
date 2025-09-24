import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.13.0`
import cats._
import cats.data._
import cats.syntax.all._

val input = "ABC: A->B,B->C"

val inputRegex = """(\w+): ([\w,\s\->]+)""".r
val transformRegex = """(\w)\->(\w)""".r

// parse the input
val (chars, transforms) = input match {
  case inputRegex(chars, transforms) =>
    (chars.toList, transforms.split("\\s*,\\s*").toList.map {
      case transformRegex(left, right) =>
        (left.charAt(0), right.charAt(0))
    }
    .groupMap(_._1)(_._2)
    .view.mapValues(_.toSet)
    .toMap)
}

def loop(c: Char, depth: Long): List[(Char, Long)] = {
  (c, depth) :: transforms.get(c).toList.flatMap(_.flatMap(loop(_, depth + 1)))
}

// generate shortest paths with distance
val shortestPaths = transforms.keys.toList
  .flatMap(startC =>
    loop(startC, 0).map { case (endC, depth) => ((startC, endC), depth)}
  )
  .groupMap(_._1)(_._2)
  .view.mapValues(_.min)
  .toMap

println("shortest paths: " + shortestPaths)

val dests = transforms.values.flatten

val dests2cost = dests.map(dest => (dest, input.flatMap(shortestPaths.get(_, dest)).sum))
val (cheapestDest, cheapestCost) = dests2cost.minBy(_._2)

println(s"$cheapestDest (cost $cheapestCost)")
