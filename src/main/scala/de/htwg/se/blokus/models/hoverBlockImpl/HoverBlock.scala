package de.htwg.se.blokus.models.hoverBlockImpl

import de.htwg.se.blokus.models.HoverBlockInterface
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.Block

class HoverBlock(val currentX: Int, val currentY: Int, val playerAmount: Int, val currentPlayer: Int, val blockType: Int, val rotation: Int, val mirrored: Boolean) extends HoverBlockInterface {

    def getX: Int = currentX
    def getY: Int = currentY

    def getPlayer: Int = currentPlayer
    def getplayerAmount: Int = playerAmount
    def getBlockType: Int = blockType
    def getRotation: Int = rotation
    def getMirrored: Boolean = mirrored

    def newInstance(newX: Int, newY: Int, newPlayerAmount: Int, player: Int,  newBlockType: Int, newRotation: Int, newMirrored: Boolean): HoverBlock = {
        new HoverBlock(newX, newY, newPlayerAmount, player, newBlockType, newRotation, newMirrored)
    }
}

object HoverBlock {
    def createInstance(x: Int, y: Int, playerAmount: Int, player: Int,  blockType: Int, rotation: Int, mirrored: Boolean): HoverBlock = {
        new HoverBlock(x, y, playerAmount, player, blockType, rotation, mirrored)
    }
}