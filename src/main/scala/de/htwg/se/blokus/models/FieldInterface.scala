package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.fieldImpl.* 

trait FieldInterface {
    val width: Int
    val height: Int
    def getFieldVector: Vector[Vector[Int]]
    def setFieldVector(newFieldVector: Vector[Vector[Int]]): FieldInterface
    def getBlockAmount(blockType: Int): Int
    def isInBounds(x: Int, y : Int): Boolean
    def cornerCheck(hoverBlock: HoverBlockInterface): Boolean
    def isInCorner(hoverBlock: HoverBlockInterface): Boolean
    def isGameRuleConfirm(hoverBlock: HoverBlockInterface): Boolean
    def isInsideField(hoverBlock: HoverBlockInterface): Boolean
    def placeBlock(hoverBlock: HoverBlockInterface, firstPlace: Boolean): FieldInterface
    def copy(): FieldInterface
}

object FieldInterface {
    def getInstance(width: Int, height: Int): FieldInterface = new Field(Vector.fill(height, width)(-1))
}
