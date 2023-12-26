package de.htwg.se.blokus.controller.controllerInvImpl

import de.htwg.se.blokus.models.Block
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock
import de.htwg.se.blokus.models.BlockInventoryInterface
import de.htwg.se.blokus.models.HoverBlockInterface
import de.htwg.se.blokus.controller.*
import de.htwg.se.blokus.util.{Observable, Observer}
import scala.util.{Try, Success, Failure}

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

    def getWidth(): Int = width
    def getHeight(): Int = height

    def placeBlock(): Try[Unit] = {
        execute(SetBlockCommand(this, blockInventory, field, getCurrentPlayer(), hoverBlock.getCurrentBlock))
    }

    def isGameOver(): Boolean = {
        false
    }

    def isGameOverPlayer(player: Int): Boolean = {
        val blocks = blockInventory.getBlocks(player)
        var ecken = blockInventory.getEcken(player)
        ecken.exists(ecke => {
            val exists = blocks.exists(blockNumm => {
                (0 to 3).exists(i => {
                    List(false, true).exists(j => {
                        val block = Block.createBlock(blockNumm, i, j)
                        field.checkPos(ecke._1, ecke._2, block.baseForm, block.corners, block.edges, player)
                    })
                })
            })
            if (!exists) {
                ecken = ecken.filter(_ != ecke)
            } 
            exists
        })
    }

    def place(): Boolean = {
        if (canPlace()) {
            blockInventory.useBlock(getCurrentPlayer(), hoverBlock.getBlockType)
            val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
            val newField = field.placeBlock(hoverBlock, firstPlace)
            currentX = (field.width / 2) - 1
            currentY = (field.height / 2) - 1
            rotation = 0
            mirrored = false
            currentBlockTyp = -1
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }
    
    def canPlace(): Boolean = {
        hoverBlock.canPlace(field, blockInventory.firstBlock(getCurrentPlayer()))
    }

    def getBlocks(): List[Int] = blockInventory.getBlocks(getCurrentPlayer())

    def changeCurrentBlock(newBlock: Int): Try[Unit] = Try {
        hoverBlock.setCurrentBlock(newBlock) 
        hoverBlock.getOutOfCorner(height, width)
        notifyObservers(UpdateEvent)
    }

    def getCurrentPlayer(): Int = hoverBlock.getCurrentPlayer

    def getNextPlayer(): Int = (getCurrentPlayer() + 1) % playerAmount

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


    def changeBlock(neuerBlock: Int): Int = {
        val currentBlock = hoverBlock.getCurrentBlock
        hoverBlock.setCurrentBlock(neuerBlock)
        currentBlock
    }

    def getRotation(): Int = hoverBlock.getRotation()

    def nextPlayer(): Try[Unit] = {
        changePlayer((hoverBlock.getCurrentPlayer + 1) % playerAmount)
    }

    def changePlayer(newPlayer: Int): Try[Unit] = {
        Try {
            hoverBlock.setPlayer(newPlayer)
            hoverBlock.setCurrentBlock(blockInventory.getRandomBlock(newPlayer).get)
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


    private class SetBlockCommand(controller: Controller, blockInventory: BlockInventoryInterface, newField: FieldInterface, player: Int, currentBlock: Int) extends Command {
        private val originalField = controller.field
        private val originalBlocksBefore = blockInventory.deepCopy
        private val originalCurrentBlock = controller.hoverBlock.getCurrentBlock

        override def execute(): Try[Unit] = Try {
            controller.place()
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
