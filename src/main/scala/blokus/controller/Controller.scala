package blokus.controller

import blokus.models.Field
import blokus.models.Block
import blokus.models.HoverBlock
import blokus.util.{Observable, Observer}

class Controller(playerAmount: Int, firstBlock: Int, width: Int, height: Int) extends Observable[ControllerEvent] {
    assert(playerAmount >= 1 && playerAmount < 5)
    var field = Field(width, height)
    var hoverBlock = HoverBlock(playerAmount, firstBlock)

    def getField(): Vector[Vector[Int]] = field.getFieldVector

    def getBlock(): List[(Int, Int)] = hoverBlock.getBlock()

    def move(richtung: Int): Boolean = {
        val moved = hoverBlock.move(field, richtung)
        if (moved) {
            notifyObservers(ControllerEvent.Update)
        }
        moved
    }

    def rotate(): Boolean = {
        val rotated = hoverBlock.rotate(field)
        if (rotated) {
            notifyObservers(ControllerEvent.Update)
        }
        rotated
    }

    def mirror(): Boolean = {
        val mirrored = hoverBlock.mirror(field)
        if (mirrored) {
            notifyObservers(ControllerEvent.Update)
        }
        mirrored
    }

    def setzen(neuerTyp: Int): Unit = {
        field = hoverBlock.setzen(field, neuerTyp)
        notifyObservers(ControllerEvent.Update)
    }

    def canSetzten(): Boolean = {
        hoverBlock.canSetzen(field)
    }

    def getRotation(): Int = hoverBlock.getRotation()

    def changePlayer(): Int = {
        val currentPlayer = hoverBlock.changePlayer()
        notifyObservers(ControllerEvent.PlayerChange(currentPlayer))
        currentPlayer
    }

    def getCurrentPlayer: Int = hoverBlock.getCurrentPlayer
}

sealed trait ControllerEvent
object ControllerEvent {
    case object Update extends ControllerEvent
    case class PlayerChange(player: Int) extends ControllerEvent
}
