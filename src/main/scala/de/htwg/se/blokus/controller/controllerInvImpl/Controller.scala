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
import scala.util.Random

class Controller extends GameController with Observable[Event] {

    private var playerAmount: Int = _
    private var width: Int = _
    private var height: Int = _

    var blockInventory: BlockInventoryInterface = _
    var hoverBlock: HoverBlockInterface = _
    var field: FieldInterface = _

    def start(playerAmt: Int, w: Int, h: Int): Unit = {
        this.playerAmount = playerAmt
        this.width = w
        this.height = h

        assert(playerAmount >= 1 && playerAmount < 5)

        blockInventory = BlockInventoryInterface.getInstance(playerAmount, 1)
        hoverBlock = HoverBlockInterface.getInstance(5, 5, playerAmount, 0, 0, false)
        field = FieldInterface.getInstance(width, height)
    }

    def getWidth(): Int = width
    def getHeight(): Int = height

    def placeBlock(): Try[Unit] = {
        execute(SetBlockCommand(this, blockInventory, field, getCurrentPlayer(), hoverBlock.getBlockType))
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
                        val tempHoverBlock = HoverBlock.createInstance(ecke._1, ecke._2, playerAmount, blockNumm, i, j)
                        field.cornerCheck(tempHoverBlock)
                    })
                })
            })
            if (!exists) {
                ecken = ecken.filter(_ != ecke)
            } 
            exists
        })
    }

    def canPlace(): Boolean = {
        val firstPlace = blockInventory.firstBlock(getCurrentPlayer())
        firstPlace && field.isInCorner(hoverBlock) || field.isGameRuleConfirm(hoverBlock)
    }

    def place(): Boolean = {
        if (canPlace()) {
            
            field = field.placeBlock(hoverBlock, blockInventory.firstBlock(getCurrentPlayer()))
            blockInventory.useBlock(getCurrentPlayer(), hoverBlock.getBlockType)
            print(field.getFieldVector)
            hoverBlock.setX((field.width / 2) - 1)
            hoverBlock.setY((field.height / 2) - 1)
            hoverBlock.setRotation(0)
            hoverBlock.setMirrored(false)
            hoverBlock.setPlayer((hoverBlock.getPlayer + 1) % playerAmount)
            hoverBlock.setBlockType(blockInventory.getRandomBlock(hoverBlock.getPlayer, Random).get)
            println("ICH war hier")
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }

    def getBlocks(): List[Int] = blockInventory.getBlocks(getCurrentPlayer())

    def changeCurrentBlock(newBlock: Int): Try[Unit] = Try {
        hoverBlock.setBlockType(newBlock) 
        hoverBlock.getOutOfCorner(height, width)
        notifyObservers(UpdateEvent)
    }

    def getCurrentPlayer(): Int = hoverBlock.getPlayer

    def getNextPlayer(): Int = (getCurrentPlayer() + 1) % playerAmount

    def getField(): Vector[Vector[Int]] = field.getFieldVector

    def getBlock(): List[(Int, Int)] = Block.createBlock(hoverBlock.getBlockType, 
        hoverBlock.getRotation, hoverBlock.getMirrored).baseForm.map(e => (e._1 + hoverBlock.getX, e._2 + hoverBlock.getY))

    def move(richtung: Int): Boolean = {
        val (x, y) = richtung match {
            case 0 => (hoverBlock.getX, hoverBlock.getY + 1)
            case 1 => (hoverBlock.getX + 1, hoverBlock.getY)
            case 2 => (hoverBlock.getX, hoverBlock.getY - 1)
            case 3 => (hoverBlock.getX - 1, hoverBlock.getY)
        }
        val tempHoverBlock = HoverBlock.createInstance(x, y, playerAmount, hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock.setX(x)
            hoverBlock.setY(y)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }

    def rotate(): Boolean = {
        val tempHoverBlock = HoverBlock.createInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getBlockType, (hoverBlock.getRotation + 1) % 4, hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock.setRotation((hoverBlock.getRotation + 1) % 4)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }

    def mirror(): Boolean = {
        val tempHoverBlock = HoverBlock.createInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getBlockType, hoverBlock.getRotation, !hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock.setMirrored(!hoverBlock.getMirrored)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    } 


    def changeBlock(neuerBlock: Int): Int = {
        val currentBlock = hoverBlock.getBlockType
        hoverBlock.setBlockType(neuerBlock)
        currentBlock
    }

    def getRotation(): Int = hoverBlock.getRotation

    /*
    def nextPlayer(): Try[Unit] = {
        changePlayer((hoverBlock.getCurrentPlayer + 1) % playerAmount)
    }

    def changePlayer(newPlayer: Int): Try[Unit] = {
        Try {
            hoverBlock.setPlayer(newPlayer)
            hoverBlock.setCurrentBlock(blockInventory.getRandomBlock(newPlayer).get)
            notifyObservers(UpdateEvent)
        }
    }*/

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
        private val originalCurrentBlock = controller.hoverBlock.getBlockType

        override def execute(): Try[Unit] = Try {
            controller.place()
        }

        override def undo(): Unit = {
            controller.field = originalField
            controller.hoverBlock.setPlayer(player)
            controller.blockInventory = originalBlocksBefore
            controller.changeBlock(currentBlock)
            controller.hoverBlock.setBlockType(originalCurrentBlock)
            notifyObservers(UpdateEvent)
        }
        override def redo(): Try[Unit] = execute()
    }
    def update(event: Event): Unit = {}
}
