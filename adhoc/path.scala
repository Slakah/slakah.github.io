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

  val viablePaths = dests.flatMap(dest => chars.traverse(c => shortestPaths.get(c, dest)).map(distance => (dest, distance.sum)))
  val result = viablePaths.minByOption(_._2) match {
    case Some((cheapestDest, cheapestCost)) =>
      s"$cheapestDest (distance $cheapestCost)"
    case None => "No path"
  }
  s"$input\n$result"
}

println(data.map(solve).mkString("\n"))