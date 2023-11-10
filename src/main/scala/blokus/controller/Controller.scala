package blokus.controller

import blokus.models.Field
import blokus.models.Block
import blokus.models.HoverBlock

class Controller(playerAmount: Int, firstBlock: Int, width: Int, height: Int) {

    var field = Field(width, height)
    var hoverBlock = HoverBlock(playerAmount, firstBlock)

    def getfield(): Vector[Vector[Int]] = field.getFieldVector
    def getBlock(): List[(Int, Int)] = hoverBlock.getBlock()
    def move(richtung:Int): Boolean = hoverBlock.move(field, richtung)
    def rotate(): Boolean = hoverBlock.rotate(field)
    def mirror(): Boolean = hoverBlock.mirror(field)
    def setzen(neuerTyp: Int): Field = hoverBlock.setzen(field, neuerTyp)
    def changePlayer(): Int = hoverBlock.changePlayer()
    def getCurrentPlayer(): Int = hoverBlock.getCurrentPlayer
}
