import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.12.0`
// import $ivy.`org.typelevel::cats-collections-core:0.9.9`
import cats._
// import cats.collections._
import cats.data._
import cats.syntax.all._
import scala.collection.mutable.ArrayDeque

val data = os.read.lines(os.pwd / "data" / "day9.dat")(0).toList.map(_.asDigit)
// val data = "2333133121414131402".toList.map(_.asDigit)
enum Block:
  case File(fileID: Long)
  case Free

val blocks = data
  .zipWithIndex
  .flatMap {
    case (x, i) if i % 2 == 0 => List.fill(x)(Block.File(i.toLong / 2))
    case (x, _) => List.fill(x)(Block.Free)
  }

var dequeue: ArrayDeque[Block] = ArrayDeque.from(blocks)
var result = List.empty[Long]
var currentOption = dequeue.removeHeadOption()
while (!currentOption.isEmpty) {
  currentOption.get match {
    case Block.File(fileID) =>
      result = fileID :: result
      currentOption = dequeue.removeHeadOption()
    case Block.Free =>
      dequeue.removeLastWhile(_ == Block.Free)
      currentOption = dequeue.removeLastOption()
  }
}
// println("Answer 1: " + result.reverse.take(100))
println("Answer 1: " + result.reverse.zipWithIndex.map((x, i) => x * i.toLong).sum)