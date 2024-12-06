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
println(section2)