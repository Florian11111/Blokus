package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.hoverBlockImpl.* 

trait HoverBlockInterface {
    def getX: Int
    def getY: Int
    def getPlayer: Int
    def getBlockType: Int
    def getRotation: Int
    def getMirrored: Boolean

    def setX(newX: Int): Int
    def setY(newY: Int): Int
    def setPlayer(newPlayer: Int): Int
    def setBlockType(newBlockType: Int): Int
    def setRotation(newRotation: Int): Int
    def setMirrored(newMirrored: Boolean): Boolean
    
    def getOutOfCorner(height: Int, wight: Int): Boolean
}
object HoverBlockInterface {
    def getInstance(x: Int, y: Int, playerAmount: Int, blockType: Int, rotation: Int, mirrored: Boolean): HoverBlockInterface = new HoverBlock(x, y, playerAmount, blockType, rotation, mirrored)
}
