package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.hoverBlockImpl.* 

trait HoverBlockInterface {
    def getX(): Int
    def getY(): Int
    def getRotation(): Int
    def getCurrentPlayer: Int
    def getCurrentBlock: Int
    def getBlock(): List[(Int, Int)]
    def setCurrentBlock(newBlock: Int): Int
    def changePlayer(): Int
    def setPlayer(newPlayer: Int): Int
    def getOutOfCorner(fieldHeight: Int, fieldWight: Int): Boolean
    def move(field: FieldInterface, direction: Int): Boolean
    def canMove(field: FieldInterface, direction: Int): Boolean
    def canRotate(field: FieldInterface): Boolean
    def rotate(field: FieldInterface): Boolean
    def canMirror(field: FieldInterface): Boolean
    def mirror(field: FieldInterface): Boolean
    def canPlace(field: FieldInterface, firstPlace: Boolean): Boolean
    def place(field: FieldInterface, newBlockTyp: Int, firstPlace: Boolean): FieldInterface
}

object HoverBlockInterface {
    def getInstance(playerAmount: Int, firstBlock: Int): HoverBlockInterface = new HoverBlock(playerAmount, firstBlock)
}
