package blokus.models

class Controller {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben
    var blockField = Field(width, height)
    var currentX = 0
    var currentY = 0
    var currentBlockType = Block.block7
    var rotation = 0
    var mirrored = false
    var currentPlayer = 1 // Spieler 1 beginnt, du kannst es anpassen

    // Bewegt den Block in die angegebene Richtung (0 = rechts, 1 = runter, 2 = links, 3 = oben)
    def move(richtung: Int): Unit = {
        if (canMove(richtung)) {
            richtung match {
                case 0 => currentX += 1
                case 1 => currentY += 1
                case 2 => currentX -= 1
                case 3 => currentY -= 1
                case _ => // Ungültige Richtung, keine Aktion erforderlich
            }
        }
    }

    // Dreht den aktuellen Block um 90 Grad im Uhrzeigersinn
    def rotate(): Unit = {
        rotation = (rotation + 1) % 4
    }

    // Spiegelt den aktuellen Block horizontal
    def mirror(): Unit = {
        mirrored = !mirrored
    }

    // Überprüft, ob der Block in die angegebene Richtung bewegt werden kann
    def canMove(richtung: Int): Boolean = {
        val newPosition = richtung match {
            case 0 => (currentX + 1, currentY) // rechts
            case 1 => (currentX, currentY + 1) // runter
            case 2 => (currentX - 1, currentY) // links
            case 3 => (currentX, currentY - 1) // oben
            case _ => (currentX, currentY) // Ungültige Richtung
        }

        blockField.isValidPosition(Block.createBlock(currentBlockType, rotation, mirrored), newPosition._1, newPosition._2)
    }

    // Überprüft, ob der Block gesetzt werden kann
    def kannSetzen(): Boolean = {
        val position = (currentX, currentY)
        val rotatedBlock = Block.createBlock(currentBlockType, rotation, mirrored)
        val playerBlock = rotatedBlock.map { case (dx, dy) => (dx + position._1, dy + position._2) }
        playerBlock.forall { case (x, y) =>
            x >= 0 && x < width && y >= 0 && y < height && blockField.getFieldVector(x)(y) == 0
        }
    }

    // Setzt den Block an der aktuellen Position
    def setzen(): Unit = {
        if (kannSetzen()) {
            val position = (currentX, currentY)
            val rotatedBlock = Block.createBlock(currentBlockType, rotation, mirrored)
            val playerBlock = rotatedBlock.map { case (dx, dy) => (dx + position._1, dy + position._2) }
            playerBlock.foreach { case (x, y) =>
                blockField = blockField.changeField(x, y, currentPlayer)
            }
            // Hier kannst du Logik hinzufügen, um zum nächsten Spieler zu wechseln
            currentPlayer = if (currentPlayer == 1) 2 else 1
        }
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
