package blokus

import models.Field
import models.Block

import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben

    var blockField = Field(width, height)
    var currentX = 0
    var currentY = 0
    var currentBlockType = Block.block7
    var rotation = 0
    var mirrored = false

    while (true) {
      val currentBlock = Block.createBlock(currentBlockType, rotation, mirrored)
      println(renderBlock(currentBlock, blockField, currentX, currentY).createFieldString)

      println("Steuerung:")
      println("Pfeiltasten: Block bewegen")
      println("r: Block platzieren")
      println("q: Beenden")
      
      def renderBlock(block: List[(Int, Int)], fieldOld: Field, x: Int, y: Int): Field = {
        var newField = Field(width, height)
        for ((dx, dy) <- block) {
          val newX = x + dx
          val newY = y + dy
          if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
            newField = newField.changeField(newX, newY, 0)
          }
        }
        newField
      }


      val input = StdIn.readLine()

      input match {
        case "q" => System.exit(0)
        case "r" => {
          blockField = blockField.placeBlock(currentBlock, currentX, currentY, 2)
          println(blockField.createFieldString)
          currentBlockType = Block.block15
          currentX = 0
          currentY = 0
        }
        case _ => {
          val dx = input match {
            case "w" => -1
            case "s" => 1
            case _ => 0
          }
          val dy = input match {
            case "a" => -1
            case "d" => 1
            case _ => 0
          }

          currentX += dx
          currentY += dy
        }
      }
    }
  }
}
