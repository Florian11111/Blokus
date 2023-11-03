package blokus

import models.Field
import models.Block

object Main {

  def configureTerminalForImmediateInput(): Unit = {
    if (System.getProperty("os.name").toLowerCase.contains("win")) {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
    } else {
      val cmd = "stty -icanon min 1 -echo"
      val pb = new ProcessBuilder("sh", "-c", cmd)
      pb.inheritIO().start().waitFor()
    }
  }

  def clearTerminal(): Unit = {
    print("\u001b[H\u001b[2J")
    System.out.flush()
  }

  def captureKeyPress(): Char = {
    System.in.read().toChar
  }

  def resetTerminalToNormal(): Unit = {
    val cmd = "stty echo icanon"
    val pb = ProcessBuilder("sh", "-c", cmd)
    pb.inheritIO().start().waitFor()
  }

  def isValidPosition(block: List[(Int, Int)], x: Int, y: Int, width: Int, height: Int): Boolean = {
    block.forall { case (dx, dy) =>
      val newX = x + dx
      val newY = y + dy
      newX >= 0 && newX < width && newY >= 0 && newY < height
    }
  }

  def main(args: Array[String]): Unit = {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben

    var blockField = Field(width, height)
    var currentX = 2
    var currentY = 2
    var currentBlockType = Block.block7
    var rotation = 0
    var mirrored = false

    configureTerminalForImmediateInput()

    try {
      while (true) {
        clearTerminal() // Terminal löschen
        val currentBlock = Block.createBlock(currentBlockType, rotation, mirrored)
        println(renderBlock(currentBlock, blockField, currentX, currentY, width, height).createFieldString.trim)

        println("Steuerung:")
        println("Pfeiltasten: Block bewegen")
        println("r: Block platzieren")
        println("q: Beenden")

        val input = captureKeyPress()

        input match {
          case 'q' => System.exit(0)
          case 'r' => {
            blockField = blockField.placeBlock(currentBlock, currentX, currentY, 2)
            println(blockField.createFieldString)
            currentBlockType = Block.block15
            currentX = 2
            currentY = 2
          }
          case 'w' if isValidPosition(currentBlock, currentX - 1, currentY, width, height) => currentX -= 1
          case 's' if isValidPosition(currentBlock, currentX + 1, currentY, width, height) => currentX += 1
          case 'a' if isValidPosition(currentBlock, currentX, currentY - 1, width, height) => currentY -= 1
          case 'd' if isValidPosition(currentBlock, currentX, currentY + 1, width, height) => currentY += 1
          case _ => // ignoriere andere Tastendrücke
        }
      }
    } finally {
      resetTerminalToNormal()
    }
  }

  def renderBlock(block: List[(Int, Int)], fieldOld: Field, x: Int, y: Int, width: Int, height: Int): Field = {
    var newField = fieldOld
    for ((dx, dy) <- block) {
      val newX = x + dx
      val newY = y + dy
      if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
        newField = newField.changeField(newX, newY, 0)
      }
    }
    newField
  }
}
