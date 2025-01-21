import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.13.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day7.dat")

val equations = data.map(_.split(":").toList match {
  case left :: right :: Nil =>
    (left.toLong, right.trim.split("\\s+").map(_.toLong).toList)
  case _ => ???
})

println("Answer 1: " + equations.filter {
  case (result, x :: xs) =>
    // TODO: optimize lazy calculations, only need one match
    xs.foldLeft(List(x)) {
      case (totals, operand) =>
        totals.flatMap(total => List(total + operand, total * operand))
    }.contains(result)
  case _ => ???
}.map(_._1).sum)


def concatLong(l: Long, r: Long) = {
  l * Math.pow(10, Math.log10(r.toDouble).toInt + 1).toLong + r
}

println("Answer 2: " + equations.filter {
  case (result, x :: xs) =>
    // TODO: optimize lazy calculations, only need one match
    xs.foldLeft(List(x)) {
      case (totals, operand) =>
        totals.flatMap(total => List(total + operand, total * operand, concatLong(total, operand)))
    }.contains(result)
  case _ => ???
}.map(_._1).sum)