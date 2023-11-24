package blokus.controller

import blokus.models.Field
import blokus.models.Block
import blokus.models.HoverBlock
import blokus.util.{Observable, Observer}

import scala.util.{Try, Success, Failure}

class Controller(playerAmount: Int, firstBlock: Int, width: Int, height: Int) extends Observable[ControllerEvent] {
    assert(playerAmount >= 1 && playerAmount < 5)
    var field = Field(width, height)
    var hoverBlock = HoverBlock(playerAmount, firstBlock)

    def setzen(newBlock: Int): Try[Unit] = execute(SetBlockCommand(this, field, getcurrentPlayer(), newBlock: Int))

    def setzen_2(neuerTyp: Int): Unit = {
        field = hoverBlock.setzen(field, neuerTyp)
        notifyObservers(ControllerEvent.Update)
    }

    def getcurrentPlayer(): Int = hoverBlock.getCurrentPlayer

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

    def canSetzten(): Boolean = {
        hoverBlock.canSetzen(field)
    }

    def changeBlock(neuerBlock: Int): Int = {
        val currentBlock = hoverBlock.currentBlockTyp 
        hoverBlock.currentBlockTyp = neuerBlock
        currentBlock
    }

    def getRotation(): Int = hoverBlock.getRotation()

    def nextPlayer(): Int = {
        val currentPlayer = hoverBlock.changePlayer()
        notifyObservers(ControllerEvent.PlayerChange(currentPlayer))
        currentPlayer
    } 

    def changePlayer(newPlayer: Int): Try[Unit] = {
        Try {
            val currentPlayer = hoverBlock.setPlayer(newPlayer)
            notifyObservers(ControllerEvent.PlayerChange(currentPlayer))
        }
    }

    // TODO:
    def isOver(): Boolean = false

    private var undoStack: List[Command] = List()
    private var redoStack: List[Command] = List()

    private def execute(command: Command): Try[Unit] = {
		undoStack = command :: undoStack
		redoStack = List()
		command.execute()
	}

    def undo(): Try[Unit] = {
        undoStack match {
            case Nil => {
                Failure(new NoSuchElementException("Nothing to undo!"))
            }
            case head :: tail => {
                head.undo() 
                undoStack = tail
                redoStack = head :: redoStack
                Success(())
            }
        }
    }

    def redo(): Try[Unit] = {
		redoStack match {
			case Nil => Failure(new NoSuchElementException("Nothing to redo!"))
			case head :: tail => {
				head.redo()
				redoStack = tail
				undoStack = head :: undoStack
				Success(())
			}
		}
	}

    private trait Command {
		def execute(): Try[Unit]
		def undo(): Unit
		def redo(): Try[Unit]
	}

    private class SetBlockCommand(controller: Controller, newField: Field, player: Int, blockTyp: Int) extends Command {
        private val originalField = controller.field

        override def execute(): Try[Unit] = Try {
            controller.setzen_2(blockTyp)
        }

        override def undo(): Unit = {
            controller.field = originalField
            controller.changePlayer(player)
            controller.changeBlock(blockTyp)
            notifyObservers(ControllerEvent.Update)
        }

        override def redo(): Try[Unit] = execute()
    }
}


sealed trait ControllerEvent
object ControllerEvent {
    case object Update extends ControllerEvent
    case class PlayerChange(player: Int) extends ControllerEvent
}
