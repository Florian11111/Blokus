package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.fieldImpl.* 

trait FieldInterface {
    val width: Int
    val height: Int
    def getFieldVector: Vector[Vector[Int]]
    def setFieldVector(newFieldVector: Vector[Vector[Int]]): FieldInterface
    def getBlockAmount(blockType: Int): Int
    def isInBounds(x: Int, y : Int): Boolean

    /* checks all checks if a block can be placed in any way on a given position */
    def potPositionsCheck(hoverBlock: HoverBlockInterface): Boolean

    /* checks if a block is in a Corner */
    def isInCorner(hoverBlock: HoverBlockInterface): Boolean

    /* checks if a Block is Game Rule Confirm */
    def isGameRuleConfirm(hoverBlock: HoverBlockInterface): Boolean

    /* checks if a Block is in the Field */
    def isInsideField(hoverBlock: HoverBlockInterface): Boolean
    
    def placeBlock(hoverBlock: HoverBlockInterface, firstPlace: Boolean): FieldInterface
    def copy(): FieldInterface
}

object FieldInterface {
    def getInstance(width: Int, height: Int): FieldInterface = new Field(Vector.fill(height, width)(-1))
}
