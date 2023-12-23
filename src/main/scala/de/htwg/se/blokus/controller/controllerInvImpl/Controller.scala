package de.htwg.se.blokus.controller.controllerInvImpl

import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock
import de.htwg.se.blokus.controller.*
import de.htwg.se.blokus.models.BlockInventoryInterface
import de.htwg.se.blokus.util.{Observable, Observer}
import scala.util.{Try, Success, Failure}
import de.htwg.se.blokus.models.HoverBlockInterface
import com.google.inject.Guice
import com.google.inject.*

class Controller extends GameController with Observable[Event] {

    private var playerAmount: Int = _
    private var firstBlock: Int = _
    private var width: Int = _
    private var height: Int = _

    var blockInventory: BlockInventoryInterface = _
    var hoverBlock: HoverBlockInterface = _
    var field: FieldInterface = _


    def start(playerAmt: Int, firstBlk: Int, w: Int, h: Int): Unit = {
        this.playerAmount = playerAmt
        this.firstBlock = firstBlk
        this.width = w
        this.height = h

        assert(playerAmount >= 1 && playerAmount < 5)

        blockInventory = BlockInventoryInterface.getInstance(playerAmount, 1)
        hoverBlock = HoverBlockInterface.getInstance(playerAmount, 2)
        field = FieldInterface.getInstance(width, height)
    }
    def gameOver() = {


    }

    def getWidth(): Int = width
    def getHeight(): Int = height

    def placeBlock(newBlock: Int): Try[Unit] = {
        execute(SetBlockCommand(this, blockInventory, field, getCurrentPlayer(), newBlock, hoverBlock.getCurrentBlock))
    }

    def place(neuerTyp: Int): Unit = {
        val firstPlace = blockInventory.firstBlock(getCurrentPlayer())
        blockInventory.useBlock(getCurrentPlayer(), hoverBlock.getCurrentBlock)
        val randomNextBlock = blockInventory.getRandomBlock(getCurrentPlayer())
        print(randomNextBlock)
        randomNextBlock.foreach(block => {
            field = hoverBlock.place(field, block, firstPlace)
            notifyObservers(UpdateEvent)
        })
    }

    def getBlocks(): List[Int] = blockInventory.getBlocks(getCurrentPlayer())

    def changeCurrentBlock(newBlock: Int): Try[Unit] = Try {
        hoverBlock.setCurrentBlock(newBlock) 
        hoverBlock.getOutOfCorner(height, width)
        notifyObservers(UpdateEvent)
    }

    def getCurrentPlayer(): Int = hoverBlock.getCurrentPlayer

    def getField(): Vector[Vector[Int]] = field.getFieldVector

    def getBlock(): List[(Int, Int)] = hoverBlock.getBlock()

    def move(richtung: Int): Boolean = {
        val moved = hoverBlock.move(field, richtung)
        if (moved) {
            notifyObservers(UpdateEvent)
        }
        moved
    }

    def rotate(): Boolean = {
        val rotated = hoverBlock.rotate(field)
        if (rotated) {
            notifyObservers(UpdateEvent)
        }
        rotated
    }

    def mirror(): Boolean = {
        val mirrored = hoverBlock.mirror(field)
        if (mirrored) {
            notifyObservers(UpdateEvent)
        }
        mirrored
    } 

    def canPlace(): Boolean = {
        hoverBlock.canPlace(field, blockInventory.firstBlock(getCurrentPlayer()))
    }

    def changeBlock(neuerBlock: Int): Int = {
        val currentBlock = hoverBlock.getCurrentBlock
        hoverBlock.setCurrentBlock(neuerBlock)
        currentBlock
    }

    def getRotation(): Int = hoverBlock.getRotation()

    def nextPlayer(): Int = {
        val currentPlayer = hoverBlock.changePlayer()
        notifyObservers(UpdateEvent)
        currentPlayer
    }

    def changePlayer(newPlayer: Int): Try[Unit] = {
        Try {
            val currentPlayer = hoverBlock.setPlayer(newPlayer)
            notifyObservers(UpdateEvent)
        }
    }

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


    private class SetBlockCommand(controller: Controller, blockInventory: BlockInventoryInterface, newField: FieldInterface, player: Int, blockTyp: Int, currentBlock: Int) extends Command {
        private val originalField = controller.field
        private val originalBlocksBefore = blockInventory.deepCopy
        private val originalCurrentBlock = controller.hoverBlock.getCurrentBlock

        override def execute(): Try[Unit] = Try {
            controller.place(blockTyp)
        }

        override def undo(): Unit = {
            controller.field = originalField
            controller.changePlayer(player)
            controller.blockInventory = originalBlocksBefore
            controller.changeBlock(currentBlock)
            controller.hoverBlock.setCurrentBlock(originalCurrentBlock)
            notifyObservers(UpdateEvent)
        }
        override def redo(): Try[Unit] = execute()
    }
    def update(event: Event): Unit = {}
}
