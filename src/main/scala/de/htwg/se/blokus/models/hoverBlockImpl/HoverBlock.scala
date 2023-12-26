package de.htwg.se.blokus.models.hoverBlockImpl

import de.htwg.se.blokus.models.HoverBlockInterface
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.Block

class HoverBlock(playerAmount: Int, firstBlock: Int) extends HoverBlockInterface {
    var currentX = 2
    var currentY = 2
    var BlockTyp = firstBlock
    var rotation = 0
    var mirrored = false
    var currentPlayer = 0

    def getX: Int = currentX
    def getY: Int = currentY
    
    def getPlayer: Int = currentPlayer
    def getBlockType: Int = BlockTyp
    def getRotation: Int = rotation
    def getMirrored: Boolean = mirrored

    def setPlayer(newPlayer: Int): Int = {
        val prevPlayer = currentPlayer
        currentPlayer = newPlayer
        prevPlayer
    }

    def setBlockType(newBlock: Int): Int = {
        val prevBlock = BlockTyp
        BlockTyp = newBlock
        prevBlock
    }

    def setRotation(newRotation: Int): Int = {
        val prevRotation = rotation
        rotation = newRotation
        prevRotation
    }

    def setMirrored(newMirrored: Boolean): Boolean = {
        val prevMirrored = mirrored
        mirrored = newMirrored
        prevMirrored
    }

    def getOutOfCorner(height: Int, wight: Int): Boolean = {
        if (currentX < 2) {
            currentX = 2
            true
        } else if ((currentX > wight - 2)) {
            currentX = wight - 2
            true
        } else if (currentY < 2) {
            currentY = 2
            true
        } else if ((currentY > height - 2)) {
            currentY = height - 2
            true
        } else {
            false
        }
    }
    /*
    def changePlayer(): Int = {
        currentPlayer = (currentPlayer + 1) % playerAmount
        currentPlayer
    }

    def getBlock(): List[(Int, Int)] = {
        Block.createBlock(BlockTyp, rotation, mirrored).baseForm.map { case (x, y) => (x + currentX, y + currentY) }
    }
    def getRawBlock(blockTypeRaw: Int, rotationRaw: Int, mirroredRaw: Boolean): Block = {
        Block.createBlock(blockTypeRaw, rotationRaw, mirroredRaw)
    }

    def move(feld: FieldInterface, richtung: Int): Boolean = {
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

    def canMove(feld: FieldInterface, richtung: Int): Boolean = {
    richtung match {
        case 0 => 
            feld.isValidPosition(Block.createBlock(BlockTyp, rotation, mirrored).baseForm, currentX, currentY + 1)
        case 1 => 
            feld.isValidPosition(Block.createBlock(BlockTyp, rotation, mirrored).baseForm, currentX + 1, currentY)
        case 2 => 
            feld.isValidPosition(Block.createBlock(BlockTyp, rotation, mirrored).baseForm, currentX, currentY - 1)
        case 3 => 
            feld.isValidPosition(Block.createBlock(BlockTyp, rotation, mirrored).baseForm, currentX - 1, currentY)
        case _ => 
            false
        }
    }


    def canRotate(feld: FieldInterface): Boolean = {
        feld.isValidPosition(
            Block.createBlock(BlockTyp, (rotation + 1) % 4, mirrored).baseForm,
            currentX,
            currentY
        )
    }

    // Dreht den aktuellen Block um 90 Grad im Uhrzeigersinn
    def rotate(feld: FieldInterface): Boolean = {
        if (canRotate(feld)) {
            rotation = (rotation + 1) % 4
            true
        } else {
            false
        }
    }


    def canMirror(feld: FieldInterface): Boolean = {
        feld.isValidPosition(Block.createBlock(BlockTyp, rotation, !mirrored).baseForm, currentX, currentY)
    }

    // Spiegelt den aktuellen Block horizontal
    def mirror(feld: FieldInterface): Boolean = {
        if (canMirror(feld)) {
            mirrored = !mirrored
            true
        } else {
            false
        }
    }


    def canPlace(feld: FieldInterface, firstPlace: Boolean): Boolean = {
        val block = Block.createBlock(BlockTyp, rotation, mirrored)
        if (firstPlace) {
            feld.isLogicFirstPlace(block.baseForm, currentX, currentY)
        } else {
            feld.isLogicPlace(block.baseForm, block.corners, block.edges, currentX, currentY, currentPlayer)
        }
    }

    // Setzt den Block an der aktuellen Position
    def place(field: FieldInterface, firstPlace: Boolean): FieldInterface = {
        val block = Block.createBlock(BlockTyp, rotation, mirrored)
        val newField = field.placeBlock(block.baseForm, block.corners, block.edges, currentX, currentY, currentPlayer, firstPlace)
        currentX = (field.width / 2) - 1
        currentY = (field.height / 2) - 1
        rotation = 0
        mirrored = false
        BlockTyp = -1
        newField
    }
    */
}

object HoverBlock {
    private var instance: HoverBlock = _
    def getInstance(playerAmount: Int, firstBlock: Int): HoverBlock = {
        if (instance == null) {
        instance = new HoverBlock(playerAmount, firstBlock)
        }
        instance
    }
}