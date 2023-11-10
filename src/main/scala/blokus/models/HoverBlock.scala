package blokus.models
import blokus.models.Block

class HoverBlock {
    var currentX = 2
    var currentY = 2
    var currentBlockType = 0
    var rotation = 0
    var mirrored = false

    def getX(): Int = currentX
    def getY(): Int = currentY
    //def getBlockType(): Int = currentBlockType
    //def getRotation(): Int = rotation
    //def getMirrod(): Boolean = mirrored
    def getBlock(): List[(Int, Int)] = Block.createBlock(currentBlockType, rotation, mirrored)

    def move(feld: Field, richtung: Int): Unit = {
        if (canMove(feld, richtung)) {
            richtung match {
                case 0 => currentY += 1
                case 1 => currentX += 1
                case 2 => currentY -= 1
                case 3 => currentX -= 1
                case _ => // Ungültige Richtung, keine Aktion erforderlich
            }
        }
    }
    
    def canMove(feld: Field, richtung: Int): Boolean = {
        val newPosition = richtung match {
            case 0 => (currentY + 1, currentX) // rechts
            case 1 => (currentY, currentX + 1) // runter
            case 2 => (currentY - 1, currentX) // links
            case 3 => (currentY, currentX - 1) // oben
            case _ => (currentY, currentX) // Ungültige Richtung
        }
        feld.isValidPosition(Block.createBlock(currentBlockType, rotation, mirrored), newPosition._1, newPosition._2)
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
    def setzen(feld: Field, newBlockTyp: Int): Field = {
        val temp = feld.placeBlock(Block.createBlock(currentBlockType, rotation, mirrored), currentX, currentY, 0)
        currentX = 2
        currentY = 2
        rotation = 0
        mirrored = false
        currentBlockType = newBlockTyp
        temp
    }

    
}
