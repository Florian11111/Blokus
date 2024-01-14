package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.hoverBlockImpl.* 

trait HoverBlockInterface {
    def getX: Int
    def getY: Int
    
    def getPlayer: Int
    def getBlockType: Int
    def getRotation: Int
    def getMirrored: Boolean

    def newInstance(newX: Int, newY: Int, newPlayerAmount: Int, player: Int,  newBlockType: Int, newRotation: Int, newMirrored: Boolean): HoverBlock
}

object HoverBlockInterface {
    def getInstance(x: Int, y: Int, playerAmount: Int, player: Int, blockType: Int, rotation: Int, mirrored: Boolean): HoverBlockInterface = new HoverBlock(x, y, playerAmount, player, blockType, rotation, mirrored)
}
