package blokus.controller

import blokus.models.Field
import blokus.models.Block
import blokus.models.HoverBlock

class Controller(playerAmount: Int, width: Int, height: Int) {
    var field = Field(width, height)
    var hoverBlock = HoverBlock(playerAmount)
    
    def move(field:Field, richtung:Int) = hoverBlock.move(field, richtung)
    def rotate(field:Field) = hoverBlock.rotate(field:Field)
    def mirror(field:Field) = hoverBlock.mirror(field:Field)
    def setzen(neuerTyp: Int) = hoverBlock.setzen(field, neuerTyp)
    def changePlayer = hoverBlock.changePlayer()
}
