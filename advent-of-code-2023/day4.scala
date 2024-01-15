import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.9.0`
import cats._
import cats.data._
import cats.syntax.all._

val data = os.read.lines(os.pwd / "data" / "day4.dat")

final case class Card(label: String, left: List[Int], right: List[Int])

val lineRegex = raw"(Card\s+\d+): ([\d\s]+) \| ([\d\s]+)".r

def parse(s: String) = {
  def parseNums(nums: String) =
    nums.split(" ").filter(!_.isEmpty).map(_.toInt).toList
  s
    .split("\n").toList
    .map {
      case lineRegex(label, left, right) =>
        Card(
          label,
          parseNums(left),
          parseNums(right),
        )
    }
}

val cards = parse(data.mkString("\n"))

println("Answer 1: " + cards.map {
  case Card(_, left, right) =>
    scala.math.pow(2, left.toSet.intersect(right.toSet).size - 1).toInt
}.sum)

val card2numMatches = cards.map {
  case Card(_, left, right) =>
    left.toSet.intersect(right.toSet).size
}
val memo = scala.collection.mutable.HashMap.empty[Int, Int].getOrElseUpdate
def loop(index: Int): Int = memo(index, {
  val numMatches = card2numMatches(index)
  if (numMatches == 0) 0
  else numMatches + ((index + 1) to (index + numMatches)).map(loop).sum
})
val originalCards = card2numMatches.length
val copiedCards = card2numMatches.indices.map(loop).toList.sum 
println("Answer 2: " + (originalCards + copiedCards))