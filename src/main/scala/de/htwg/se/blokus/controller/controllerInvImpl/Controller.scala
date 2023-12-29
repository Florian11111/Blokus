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
import org.checkerframework.checker.units.qual.h

class Controller extends GameController with Observable[Event] {

    private var playerAmount: Int = _
    private var width: Int = _
    private var height: Int = _

    var blockInventory: BlockInventoryInterface = _
    var hoverBlock: HoverBlockInterface = _
    var field: FieldInterface = _

    def exit(): Unit = {
        notifyObservers(ExitEvent)
    }

    def TESTMETHOD(player: Int): List[(Int, Int)] = {
        blockInventory.getPosPositions(player)
    }

    def start(playerAmt: Int, w: Int, h: Int): Unit = {
        this.playerAmount = playerAmt
        this.width = w
        this.height = h
        assert(playerAmount >= 1 && playerAmount < 5)
        assert(width > 0 && height > 0)
        if (playerAmt == 1) { 
            blockInventory = BlockInventoryInterface.getInstance(playerAmount, 4)
        } else if (playerAmt == 2) {
            blockInventory = BlockInventoryInterface.getInstance(playerAmount, 2)
        } else {
            blockInventory = BlockInventoryInterface.getInstance(playerAmount, 1)
        }
        hoverBlock = HoverBlockInterface.getInstance(width / 2, height / 2, playerAmount, 0, 0, false)
        field = FieldInterface.getInstance(width, height)
        notifyObservers(StartGameEvent)
    }

    // count how many block each player has on the field an return it in a list 
    def getBlockAmount(): List[Int] = {
        var blockAmount: List[Int] = List()
        for (i <- 0 until playerAmount) {
            blockAmount = blockAmount :+ field.getBlockAmount(i)
        }
        blockAmount
    }

    def getWidth(): Int = width
    
    def getHeight(): Int = height


    def getPlayerAmount(): Int = playerAmount

    def placeBlock(): Try[Unit] = {
        execute(SetBlockCommand(this, blockInventory, field, getCurrentPlayer(), hoverBlock.getBlockType))
    }

    def isGameOverPlayer(player: Int): Boolean = {
        var posPositions = filterPotentialPositions(player)
        posPositions.isEmpty && !blockInventory.firstBlock(player) || blockInventory.getBlocks(getCurrentPlayer()).forall(_ == 0)
    }

    def canPlace(): Boolean = {
        val firstPlace = blockInventory.firstBlock(getCurrentPlayer())
        firstPlace && field.isInCorner(hoverBlock) || field.isGameRuleConfirm(hoverBlock)
    }

    def switchPlayerAndCheckGameOver(): Boolean = {
        print(getCurrentPlayer())
        if (playerAmount == 1 && isGameOverPlayer(0)) {
            notifyObservers(EndGameEvent)
        } else {
            if ((0 until playerAmount).forall(isGameOverPlayer)) {
                notifyObservers(EndGameEvent)
            }
            var nextPlayer = (getCurrentPlayer() + 1) % playerAmount
            
            for (i <- 0 until playerAmount) {
                if (isGameOverPlayer(nextPlayer)) {
                    nextPlayer = (nextPlayer + 1) % playerAmount
                }
            }
            hoverBlock.setPlayer(nextPlayer)
        }
        print(getCurrentPlayer())
        false
    }

    def place(): Boolean = {
        if (canPlace()) {
            field = field.placeBlock(hoverBlock, blockInventory.firstBlock(getCurrentPlayer()))
            blockInventory.useBlock(getCurrentPlayer(), hoverBlock.getBlockType)
            blockInventory.setPosPositions(getCurrentPlayer(), addPotentialPositionsToInventory(getCurrentPlayer()))
            hoverBlock.setX((field.width / 2) - 1)
            hoverBlock.setY((field.height / 2) - 1)
            hoverBlock.setRotation(0)
            hoverBlock.setMirrored(false)
            switchPlayerAndCheckGameOver()
            hoverBlock.setBlockType(blockInventory.getRandomBlock(hoverBlock.getPlayer, Random).get)
            blockInventory.setPosPositions(getCurrentPlayer(), filterPotentialPositions(getCurrentPlayer()))
            notifyObservers(PlaceBlockEvent)
            true
        } else {
            false
        }
    }

    private def filterPotentialPositions(player: Int): List[(Int, Int)] = {
        var posPositions = blockInventory.getPosPositions(player)
        val blocks = blockInventory.getBlocks(player)
        posPositions = posPositions.filter { ecke =>
            isValidPotentialPositions(ecke._1, ecke._2)
        }
        posPositions.filter { ecke =>
            blocks.zipWithIndex.exists { case (blockamount, block) =>
                if (blockamount > 0) {
                    (0 to 3).exists { i =>
                        List(false, true).exists { j =>
                            val tempHoverBlock =  HoverBlock.createInstance(ecke._1, ecke._2, playerAmount, block, i, j)
                            tempHoverBlock.setPlayer(player)
                            val ergebenis = field.cornerCheck(tempHoverBlock)
                            ergebenis
                        }
                    } 
                } else {
                    false
                }
            }
        }
    }


    def isValidPotentialPositions(x: Int, y: Int): Boolean = {
        val neighbors = List((x-1, y), (x+1, y), (x, y-1), (x, y+1))
        val fieldtemp = field.getFieldVector.transpose
        val ergenis = field.isInBounds(x, y) && fieldtemp(x)(y) == -1
        
        var allValid = true
        for ((nx, ny) <- neighbors) {
            if (!(nx < 0 || ny < 0 || nx >= field.width || ny >= field.height ||
                (nx >= 0 && ny >= 0 && nx < field.width && ny < field.height && fieldtemp(nx)(ny) != getCurrentPlayer()))) {
                allValid = false
            }
        }
        allValid && ergenis
    }

    def addPotentialPositionsToInventory(player: Int): List[(Int, Int)] = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        var posPositions = blockInventory.getPosPositions(player)
        (posPositions ++ block.corners.map(e => (e._1 + hoverBlock.getX, e._2 + hoverBlock.getY)))
    }

    def getBlocks(): List[Int] = blockInventory.getBlocks(getCurrentPlayer())

    def changeCurrentBlock(newBlock: Int): Try[Unit] = Try {
        hoverBlock.setBlockType(newBlock) 
        hoverBlock.getOutOfCorner(height, width)
        notifyObservers(UpdateEvent)
    }

    def getCurrentPlayer(): Int = hoverBlock.getPlayer

    def getNextPlayer(): Int = (getCurrentPlayer() + 1) % playerAmount

    def getField(): Vector[Vector[Int]] = field.copy().getFieldVector

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
        tempHoverBlock.setPlayer(hoverBlock.getPlayer)
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
        tempHoverBlock.setPlayer(hoverBlock.getPlayer)
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
        tempHoverBlock.setPlayer(hoverBlock.getPlayer)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock.setMirrored(!hoverBlock.getMirrored)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    } 

    def setXandY(x: Int, y: Int): Boolean = {
        val tempHoverBlock = HoverBlock.createInstance(x, y, playerAmount, hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        tempHoverBlock.setPlayer(hoverBlock.getPlayer)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock.setX(x)
            hoverBlock.setY(y)
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
            notifyObservers(PlaceBlockEvent)
        }
        override def redo(): Try[Unit] = execute()
    }
    def update(event: Event): Unit = {}
}
