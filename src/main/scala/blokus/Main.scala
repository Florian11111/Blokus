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
    val pb = new ProcessBuilder("sh", "-c", cmd)
    pb.inheritIO().start().waitFor()
  }

  def main(args: Array[String]): Unit = {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben

    var blockField = Field(width, height)
    var currentX = 0
    var currentY = 0
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
            currentX = 0
            currentY = 0
          }
          case 'w' => currentX -= 1
          case 's' => currentX += 1
          case 'a' => currentY -= 1
          case 'd' => currentY += 1
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