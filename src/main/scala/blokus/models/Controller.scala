package blokus.models

class Controller {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben
    var blockField = Field(width, height)
    var currentX = 2
    var currentY = 2
    var currentBlockType = Block.block7
    var rotation = 0
    var mirrored = false
    var currentPlayer = 1 // Spieler 1 beginnt, du kannst es anpassen

    // Bewegt den Block in die angegebene Richtung (0 = rechts, 1 = runter, 2 = links, 3 = oben)
    def move(richtung: Int): Unit = {
        if (canMove(richtung)) {
            richtung match {
                case 0 => currentY += 1
                case 1 => currentX += 1
                case 2 => currentY -= 1
                case 3 => currentX -= 1
                case _ => // Ungültige Richtung, keine Aktion erforderlich
            }
        }
    }

    // Überprüft, ob der Block in die angegebene Richtung bewegt werden kann
    def canMove(richtung: Int): Boolean = {
        val newPosition = richtung match {
            case 0 => (currentY + 1, currentX) // rechts
            case 1 => (currentY, currentX + 1) // runter
            case 2 => (currentY - 1, currentX) // links
            case 3 => (currentY, currentX - 1) // oben
            case _ => (currentY, currentX) // Ungültige Richtung
        }
        blockField.isValidPosition(Block.createBlock(currentBlockType, rotation, mirrored), newPosition._1, newPosition._2)
    }
    
    // Dreht den aktuellen Block um 90 Grad im Uhrzeigersinn
    def rotate(): Unit = {
        rotation = (rotation + 1) % 4
    }

    // Spiegelt den aktuellen Block horizontal
    def mirror(): Unit = {
        mirrored = !mirrored
    }

    // Setzt den Block an der aktuellen Position
    def setzen(): Unit = {
        blockField = blockField.placeBlock(Block.createBlock(currentBlockType, rotation, mirrored), currentX, currentY, 0)
        currentX = 2
        currentY = 2
    }


    // Andere Methoden, um den aktuellen Spieler, das Spielfeld, Konflikte, usw. abzurufen

    def getSpieler(): Int = {
        currentPlayer
    }

    def getGameField(): Field = {
        blockField
    }

    def renderField(): String = {
        val position = (currentX, currentY)
        val rotatedBlock = Block.createBlock(currentBlockType, rotation, mirrored)
        val playerBlock = rotatedBlock.map { case (dx, dy) => (dx + position._1, dy + position._2) }
        val renderedField = blockField.getFieldVector.map { row =>
            row.map {
                case 0 => "# "
                case 1 => "+ "
                case 2 => "|"
                case _ => "? "
            }
        }

        // Erzeuge eine kopierte Version des aktuellen Blocks im Spielfeld
        val updatedField = renderedField.zipWithIndex.map { case (row, x) =>
            row.zipWithIndex.map { case (cell, y) =>
                if (playerBlock.contains((x, y))) "* " else cell
            }
        }

        updatedField.map(_.mkString).mkString("\n")
    }

}
