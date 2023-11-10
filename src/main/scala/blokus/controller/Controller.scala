package blokus.controller

import blokus.models.Field
import blokus.models.Block
import blokus.models.HoverBlock

class Controller {
    val width = 20 // Hier die Breite deines Felds eingeben
    val height = 20 // Hier die Höhe deines Felds eingeben
    var field = Field(width, height)
    var moveBlock = HoverBlock()
    
    def move(field:Field, richtung:Int) = moveBlock.move(field, richtung)
    def canMove(field:Field, richtung:Int) = moveBlock.canMove(field, richtung)
    def getBlock() = moveBlock.getBlock()
    def getField() = field.getFieldVector()
    def rotate() = moveBlock.rotate()
    def mirror() = moveBlock.mirror()
    def setzen(neuerTyp: Int) = moveBlock.setzen(field, neuerTyp)
    /*
    def renderField(): String = {
        val position = (moveBlock.getX(), moveBlock.getY())
        val rotatedBlock = moveBlock.getBlock()
        val playerBlock = rotatedBlock.map { case (dx, dy) => (dx + position._1, dy + position._2) }
        val renderedField = field.getFieldVector.map { row =>
            row.map {
                case 0 => "# " // \u001b[31mTEXT\u001b[0m farbe ROT und bitte "TEXT" ersetzen
                case 1 => "+ " // \u001b[33mTEXT\u001b[0m GELB
                case 2 => "|"  // \u001b[32mTEXT\u001b[0m GRÜN
                case _ => "? " // \u001b[34mTEXT\u001b[0m BLAU
            }
        }

        val updatedField = renderedField.zipWithIndex.map { case (row, x) =>
            row.zipWithIndex.map { case (cell, y) =>
                if (playerBlock.contains((x, y))) "* " else cell
            }
        }
        updatedField.map(_.mkString).mkString("\n")
    }*/
}
