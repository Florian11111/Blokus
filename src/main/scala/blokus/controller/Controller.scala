package blokus.controller

import blokus.models.Field
import blokus.models.Block
import blokus.models.HoverBlock

class Controller(playerAmount: Int) {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben
    var field = Field(width, height)
    var hoverBlock = HoverBlock(playerAmount)
    
    def changePlayer = hoverBlock.changePlayer()
    def move(field:Field, richtung:Int) = hoverBlock.move(field, richtung)
    def rotate(field:Field) = hoverBlock.rotate(field:Field)
    def mirror(field:Field) = hoverBlock.mirror(field:Field)
    def setzen(neuerTyp: Int) = hoverBlock.setzen(field, neuerTyp)

}
