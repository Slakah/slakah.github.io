import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day5.dat")
val (section1, section2) = data.span(!_.isEmpty) match { case (s1, s2) => (
    s1.map(_.split("\\|").map(_.toInt).toList match { case List(x, y) => (x, y) }),
    s2.filter(!_.isEmpty).map(_.split(",").map(_.toInt).toList)
  )
}
val orderingRules = section1.groupBy(_._1).view.mapValues(_.map(_._2).toList).toMap

println(section2.map(_.zipWithIndex).filter { line =>
  line.forall((x, i) => orderingRules.get(x).getOrElse(List.empty).forall(y => line.find(_._1 == y) match {
    case Some((_, j)) => j > i
    case None => true
  }))
}.map(line => line(line.length / 2)._1).sum)
