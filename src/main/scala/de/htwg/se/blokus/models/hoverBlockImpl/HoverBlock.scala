package de.htwg.se.blokus.models.hoverBlockImpl

import de.htwg.se.blokus.models.HoverBlockInterface
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.Block

class HoverBlock(x: Int, y: Int, playerAmount: Int, blockTypeU: Int, rotationU: Int, mirroredU: Boolean) extends HoverBlockInterface {
    var currentX = x
    var currentY = y
    var blockType = blockTypeU
    var rotation = rotationU
    var mirrored = mirroredU
    var currentPlayer = 0

    def getX: Int = currentX
    def getY: Int = currentY
    
    def getPlayer: Int = currentPlayer
    def getBlockType: Int = blockType
    def getRotation: Int = rotation
    def getMirrored: Boolean = mirrored

    def setX(newX: Int): Int = {
        val prevX = currentX
        currentX = newX
        prevX
    }

    def setY(newY: Int): Int = {
        val prevY = currentY
        currentY = newY
        prevY
    }

    def setPlayer(newPlayer: Int): Int = {
        val prevPlayer = currentPlayer
        currentPlayer = newPlayer
        prevPlayer
    }

    def setBlockType(newBlock: Int): Int = {
        val prevBlock = blockType
        blockType = newBlock
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
}

object HoverBlock {
    def createInstance(x: Int, y: Int, playerAmount: Int, blockType: Int, rotation: Int, mirrored: Boolean): HoverBlock = {
        new HoverBlock(x, y, playerAmount, blockType, rotation, mirrored)
    }
}