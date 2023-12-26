package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.hoverBlockImpl.* 

trait HoverBlockInterface {
    def getX: Int
    def getY: Int
    def getPlayer: Int
    def getBlockType: Int
    def getRotation: Int
    def getMirrored: Boolean

    def setPlayer(newPlayer: Int): Int
    def setBlockType(newBlockType: Int): Int
    def setRotation(newRotation: Int): Int
    def setMirrored(newMirrored: Boolean): Boolean
    
    def getOutOfCorner(height: Int, wight: Int): Boolean

    // remove ---------
    /*
    def getBlock(): List[(Int, Int)]
    def getRawBlock(blockType: Int, rotation: Int, mirrored: Boolean): Block
    def canPlace(field: FieldInterface, firstPlace: Boolean): Boolean
    def place(field: FieldInterface, firstPlace: Boolean): FieldInterface
    def move(field: FieldInterface, direction: Int): Boolean
    
    def canMove(field: FieldInterface, direction: Int): Boolean
    def canRotate(field: FieldInterface): Boolean
    def rotate(field: FieldInterface): Boolean
    def canMirror(field: FieldInterface): Boolean
    def mirror(field: FieldInterface): Boolean
    */
    // ----------------
}
object HoverBlockInterface {
    def getInstance(playerAmount: Int, firstBlock: Int): HoverBlockInterface = new HoverBlock(playerAmount, firstBlock)
}
