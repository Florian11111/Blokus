package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.FieldImpl.* 

trait FieldInterface {
    val width: Int
    val height: Int
    def getFieldVector: Vector[Vector[Int]]
    def isValidPosition(block: List[(Int, Int)], x: Int, y: Int): Boolean
    def isCorner(x: Int, y: Int): Boolean
    def checkPos(x: Int, y: Int, block: List[(Int, Int)], ecken: List[(Int, Int)], kanten: List[(Int, Int)], currentPlayer: Int): Boolean
    def isLogicFirstPlace(block: List[(Int, Int)], x: Int, y: Int): Boolean
    def isLogicPlace(block: List[(Int, Int)], ecken: List[(Int, Int)], kanten: List[(Int, Int)], x: Int, y: Int, currentPlayer: Int): Boolean
    def placeBlock(block: List[(Int, Int)], ecken: List[(Int, Int)], kanten: List[(Int, Int)], x: Int, y: Int, currentPlayer: Int, firstPlace: Boolean): FieldInterface
    def copy(): FieldInterface
}

object FieldInterface {
    def getInstance(width: Int, height: Int): FieldInterface = new Field(Vector.fill(height, width)(-1))
}
