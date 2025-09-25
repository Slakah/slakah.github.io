import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.13.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "path.dat").filter(!_.isEmpty)

val inputRegex = """(\w+): ([\w,\s\->]+)""".r
val transformRegex = """(\w)\->(\w)""".r

def solve(input: String): String = {
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

  def loop(c: Char, depth: Long, seen: Set[(Char)]): List[(Char, Long)] = {
    if (seen.contains(c)) List.empty
    else (c, depth) :: transforms.get(c).toList.flatMap(_.flatMap(loop(_, depth + 1, seen + c)))
  }

  val dests = transforms.values.flatten.toSet

  // generate shortest paths with distance
  val shortestPaths = chars
    .flatMap(startC =>
      loop(startC, 0L, Set.empty).map { case (endC, depth) => ((startC, endC), depth)}
    )
    .groupMap(_._1)(_._2)
    .view.mapValues(_.min)
    .toMap

  val viablePaths = dests.flatMap(dest => chars.map(c => shortestPaths.get(c, dest)).sequence.map(distance => (dest, distance.sum)))
  val (cheapestDest, cheapestCost) = viablePaths.minBy(_._2)

  s"$input\n$cheapestDest (cost $cheapestCost)"
}

println(data.map(solve).mkString("\n"))