import $ivy.`com.lihaoyi::os-lib:0.9.2`
import $ivy.`org.typelevel::cats-core:2.13.0`
import cats._
import cats.data._
import cats.syntax.all._
import scala.collection.mutable.{ArrayBuffer, ArrayDeque}

val data = os.read.lines(os.pwd / "data" / "day9.dat")(0).toList.map(_.asDigit)
enum ContiguousBlock:
  case File(fileID: Long, size: Int)
  case Free(size: Int)

enum Block:
  case File(fileID: Long)
  case Free

val contiguousBlocks = data
  .zipWithIndex
  .map {
    case (size, i) if i % 2 == 0 => ContiguousBlock.File(i.toLong / 2, size)
    case (size, _) => ContiguousBlock.Free(size)
  }

def contiguousToBlocks(contiguousBlocks: List[ContiguousBlock]): List[Block] = contiguousBlocks.flatMap {
  case ContiguousBlock.File(fileID, size) => List.fill(size)(Block.File(fileID))
  case ContiguousBlock.Free(size) => List.fill(size)(Block.Free)
}

val blocks = contiguousToBlocks(contiguousBlocks)

val answer1Result = {
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
  result.reverse
}
println("Answer 1: " + answer1Result.zipWithIndex.map((x, i) => x * i.toLong).sum)

val result = {
  var result: ArrayBuffer[ContiguousBlock] = ArrayBuffer.from(contiguousBlocks)
  var currIndex = contiguousBlocks.lastIndexWhere {
    case _: ContiguousBlock.File => true
    case _ => false
  }

  while (currIndex > 0) {
    result(currIndex) match {
      case ContiguousBlock.Free(size) =>
        currIndex = currIndex - 1
      case file @ ContiguousBlock.File(fileID, size) =>
        val freeIndex = result.indexWhere {
          case ContiguousBlock.Free(freeSize)
            if freeSize >= size => true
          case _ => false
        }
        result.lift(freeIndex) match {
          case _ if freeIndex > currIndex =>
            currIndex = currIndex - 1
          case Some(ContiguousBlock.Free(freeSize)) if freeSize == size =>
            result(currIndex) = ContiguousBlock.Free(freeSize)
            result(freeIndex) = file
            currIndex = currIndex - 1
          case Some(ContiguousBlock.Free(freeSize)) if freeSize > size =>
            result(currIndex) = ContiguousBlock.Free(size)
            result(freeIndex) = file
            result.insert(freeIndex + 1, ContiguousBlock.Free(freeSize - size))
            currIndex = currIndex - 1
          case _ =>
            currIndex = currIndex - 1
        }
    }
  }
  result.toList
}

println("Answer 2: " + contiguousToBlocks(result.toList).zipWithIndex.map {
  case (Block.File(fileID), i) => fileID * i.toLong
  case _ => 0
}.sum)