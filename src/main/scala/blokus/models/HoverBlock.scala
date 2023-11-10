package blokus.models
import blokus.models.Block

class HoverBlock(playerAmount: Int, firstBlock: Int) {
    var currentX = 2
    var currentY = 2
    var currentBlockTyp = firstBlock
    var rotation = 0
    var mirrored = false
    var currentPlayer = 0

    def getX(): Int = currentX
    def getY(): Int = currentY
    def getRotation(): Int = rotation
    def getCurrentPlayer: Int = currentPlayer
    def getBlock(): List[(Int, Int)] = {
        val baseForm = Block.createBlock(currentBlockTyp, rotation, mirrored)
        baseForm.map { case (x, y) => (x + currentX, y + currentY) }
    }

    def changePlayer(): Int = {
        currentPlayer = (currentPlayer + 1) % playerAmount
        currentPlayer
    }

    def move(feld: Field, richtung: Int): Boolean = {
        if (canMove(feld, richtung)) {
            richtung match {
                case 0 => currentY += 1
                case 1 => currentX += 1
                case 2 => currentY -= 1
                case 3 => currentX -= 1
                case _ => // Ungültige Richtung, keine Aktion erforderlich
            }
            true
        } else {
            false
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
        feld.isValidPosition(Block.createBlock(currentBlockTyp, rotation, mirrored), newPosition._1, newPosition._2)
    }

    def canRotate(feld: Field): Boolean = {
        feld.isValidPosition(Block.createBlock(currentBlockTyp, (rotation + 1) % 4, mirrored), currentX, currentY)
    }

    // Dreht den aktuellen Block um 90 Grad im Uhrzeigersinn
    def rotate(feld: Field): Boolean = {
        if (canRotate(feld)) {
            rotation = (rotation + 1) % 4
            true
        } else {
            false
        }
    }

    def canMirror(feld: Field): Boolean = {
        feld.isValidPosition(Block.createBlock(currentBlockTyp, rotation, !mirrored), currentX, currentY)
    }

    // Spiegelt den aktuellen Block horizontal
    def mirror(feld: Field): Boolean = {
        if (canMirror(feld)) {
            mirrored = !mirrored
            true
        } else {
            false
        }
    }

    // Setzt den Block an der aktuellen Position
    def setzen(feld: Field, newBlockTyp: Int): Field = {
        val temp = feld.placeBlock(Block.createBlock(currentBlockTyp, rotation, mirrored), currentX, currentY, currentPlayer)
        currentX = 2
        currentY = 2
        rotation = 0
        mirrored = false
        currentBlockTyp = newBlockTyp
        temp
    }
}
