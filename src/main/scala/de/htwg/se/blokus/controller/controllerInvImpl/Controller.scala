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
import de.htwg.se.blokus.models.FileIOInterface
import de.htwg.se.blokus.models.GameState

class Controller extends GameController with Observable[Event] {

    private var playerAmount: Int = _
    private var width: Int = _
    private var height: Int = _

    var blockInventory: BlockInventoryInterface = _
    var hoverBlock: HoverBlockInterface = _
    var field: FieldInterface = _
    val fileIoJson: FileIOInterface = FileIOInterface.getInstanceJson()
    val fileIoXml: FileIOInterface = FileIOInterface.getInstanceXml()

    def exit(): Unit = {
        notifyObservers(ExitEvent)
    }

    def getPosPositions(player: Int): List[(Int, Int)] = {
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
        } else if (playerAmt == 2 || playerAmt == 3) {
            blockInventory = BlockInventoryInterface.getInstance(playerAmount, 2)
        } else {
            blockInventory = BlockInventoryInterface.getInstance(playerAmount, 1)
        }
        hoverBlock = HoverBlockInterface.getInstance(width / 2, height / 2, playerAmount, 0, 0, 0, false)
        field = FieldInterface.getInstance(width, height)
        notifyObservers(StartGameEvent)
    }

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
        var posPositions = blockInventory.getPosPositions(player)
        posPositions.isEmpty && !blockInventory.firstBlock(player) || blockInventory.getBlocks(getCurrentPlayer()).forall(_ == 0)
    }

    def canPlace(): Boolean = {
        val firstPlace = blockInventory.firstBlock(getCurrentPlayer())
        firstPlace && field.isInCorner(hoverBlock) || field.isGameRuleConfirm(hoverBlock)
    }

    def switchPlayerAndCheckGameOver(): Boolean = {
        if (playerAmount == 1 && isGameOverPlayer(0)) {
            notifyObservers(EndGameEvent)
            true
        } else {
            if ((0 until playerAmount).forall(isGameOverPlayer)) {
                notifyObservers(EndGameEvent)
                true
            } else {
            var nextPlayer = (getCurrentPlayer() + 1) % playerAmount
            for (i <- 0 until playerAmount) {
                if (isGameOverPlayer(nextPlayer)) {
                    nextPlayer = (nextPlayer + 1) % playerAmount
                }
            }
            hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount,
                nextPlayer, hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
            false
            }
        }
    }

    def place(): Boolean = {
        if (canPlace() && blockInventory.isAvailable(hoverBlock.getPlayer, hoverBlock.getBlockType)) {
            field = field.placeBlock(hoverBlock, blockInventory.firstBlock(getCurrentPlayer()))
            blockInventory = blockInventory.withUsedBlock(getCurrentPlayer(), hoverBlock.getBlockType)
            blockInventory = blockInventory.withPosPositions(getCurrentPlayer(), addPotentialPositionsToInventory(getCurrentPlayer()))
            for (i <- 0 until playerAmount) {
                blockInventory = blockInventory.withPosPositions(i, filterPotentialPositions(i))
            }
            switchPlayerAndCheckGameOver()
            val nextBlock = blockInventory.setNextBLock(getCurrentPlayer(), hoverBlock.getBlockType)
            hoverBlock = hoverBlock.newInstance((field.width / 2) - 1, (field.height / 2) - 1, playerAmount, getCurrentPlayer(),
                nextBlock, 0, false)
            notifyObservers(PlaceBlockEvent)
            true
        } else {
            false
        }
    }

    def filterPotentialPositions(player: Int): List[(Int, Int)] = {
        var posPositions = blockInventory.getPosPositions(player)
        val blocks = blockInventory.getBlocks(player)
        posPositions = posPositions.filter { ecke =>
            isValidPotentialPositions(ecke._1, ecke._2, player)
        }
        posPositions.filter { ecke =>
            blocks.zipWithIndex.exists { case (blockamount, block) =>
                if (blockamount > 0) {
                    (0 to 3).exists { i =>
                        List(false, true).exists { j =>
                            val tempHoverBlock =  HoverBlock.createInstance(ecke._1, ecke._2, playerAmount, player, block, i, j)
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

    def isValidPotentialPositions(x: Int, y: Int, player: Int): Boolean = {
        val neighbors = List((x-1, y), (x+1, y), (x, y-1), (x, y+1))
        val fieldtemp = field.getFieldVector.transpose
        val ergebnis = field.isInBounds(x, y) && fieldtemp(x)(y) == -1
        var allValid = true
        for ((nx, ny) <- neighbors) {
            if (!(nx < 0 || ny < 0 || nx >= field.width || ny >= field.height ||
                (nx >= 0 && ny >= 0 && nx < field.width && ny < field.height && fieldtemp(nx)(ny) != player))) {
                allValid = false
            }
        }
        allValid && ergebnis
    }

    def addPotentialPositionsToInventory(player: Int): List[(Int, Int)] = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        var posPositions = blockInventory.getPosPositions(player)
        (posPositions ++ block.corners.map(e => (e._1 + hoverBlock.getX, e._2 + hoverBlock.getY)))
    }

    def getBlocks(): List[Int] = blockInventory.getBlocks(getCurrentPlayer())

    def changeCurrentBlock(newBlock: Int): Try[Unit] = Try {
        if(newBlock < 0 | newBlock > 20){
            throw new IllegalArgumentException(s"Invalid Block number: $newBlock")
        }
        val blocks = blockInventory.getBlocks(getCurrentPlayer())
        if(blocks(newBlock) <= 0){
            throw new RuntimeException(s"Block $newBlock is not available for Player.")
        }
        hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getPlayer,
            newBlock, hoverBlock.getRotation, hoverBlock.getMirrored)

        notifyObservers(UpdateEvent)
    }

    def setNextBLock(): Try[Unit] = Try {
        val nextBlock = blockInventory.setNextBLock(getCurrentPlayer(), hoverBlock.getBlockType)
        hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getPlayer,
            nextBlock, hoverBlock.getRotation, hoverBlock.getMirrored)
        notifyObservers(UpdateEvent)
    }

    def getCurrentPlayer(): Int = hoverBlock.getPlayer

    def getNextPlayer(): Int = (getCurrentPlayer() + 1) % playerAmount

    def getField(): Vector[Vector[Int]] = field.copy().getFieldVector

    def getBlock(): List[(Int, Int)] = Block.createBlock(hoverBlock.getBlockType,
        hoverBlock.getRotation, hoverBlock.getMirrored).baseForm.map(e => (e._1 + hoverBlock.getX, e._2 + hoverBlock.getY))

    def move(xCord: Int, yCord: Int): Boolean = {
        val x = hoverBlock.getX + xCord
        val y = hoverBlock.getY + yCord
        val tempHoverBlock = HoverBlock.createInstance(x, y, playerAmount, hoverBlock.getPlayer, hoverBlock.getBlockType,
            hoverBlock.getRotation, hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock = hoverBlock.newInstance(x, y, playerAmount, hoverBlock.getPlayer, hoverBlock.getBlockType,
                hoverBlock.getRotation, hoverBlock.getMirrored)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }

    def rotate(): Boolean = {
        val tempHoverBlock = HoverBlock.createInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getPlayer,
            hoverBlock.getBlockType, (hoverBlock.getRotation + 1) % 4, hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getPlayer, hoverBlock.getBlockType, (hoverBlock.getRotation + 1) % 4, hoverBlock.getMirrored)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }

    def mirror(): Boolean = {
        val tempHoverBlock = HoverBlock.createInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getPlayer,
            hoverBlock.getBlockType, hoverBlock.getRotation, !hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount,
                hoverBlock.getPlayer, hoverBlock.getBlockType, hoverBlock.getRotation, !hoverBlock.getMirrored)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }

    def setXandY(x: Int, y: Int): Boolean = {
        val tempHoverBlock = HoverBlock.createInstance(x, y, playerAmount, hoverBlock.getPlayer,  hoverBlock.getBlockType,
            hoverBlock.getRotation, hoverBlock.getMirrored)
        if (field.isInsideField(tempHoverBlock)) {
            hoverBlock = hoverBlock.newInstance(x, y, playerAmount, hoverBlock.getPlayer, hoverBlock.getBlockType,
                hoverBlock.getRotation, hoverBlock.getMirrored)
            notifyObservers(UpdateEvent)
            true
        } else {
            false
        }
    }


    def changeBlock(neuerBlock: Int): Int = {
        val currentBlock = hoverBlock.getBlockType
        hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, hoverBlock.getPlayer,
            neuerBlock, hoverBlock.getRotation, hoverBlock.getMirrored)
        currentBlock
    }

    def getRotation(): Int = hoverBlock.getRotation
    def getMirrored(): Boolean = hoverBlock.getMirrored

    def load(path: String): Try[Unit] = {
        var gameState: GameState = null
        Try {
            val fileIo = path match {
                case p if p.endsWith(".xml") => gameState = fileIoXml.load(path)
                case p if p.endsWith(".json") => gameState  = fileIoJson.load(path)
                case _ => throw new IllegalArgumentException("File type not supported!")
            }
            start(gameState.getBlockInventory().getPlayerAmount(), gameState.getField().width, gameState.getField().height)
            field = gameState.getField()
            hoverBlock = hoverBlock.newInstance(hoverBlock.getX, hoverBlock.getY, playerAmount, gameState.getCurrentPlayer(),
                gameState.getBlockInventory().getBlocks(gameState.getCurrentPlayer()).head, 0, false)
            blockInventory = gameState.getBlockInventory()
            notifyObservers(PlaceBlockEvent)
        }
    }
    def save(path: String): Try[Unit] = {
        Try {
            val fileIo = path match {
                case p if p.endsWith(".xml") => fileIoXml.save(new GameState(field, getCurrentPlayer(), blockInventory), path)
                case p if p.endsWith(".json") => fileIoJson.save(new GameState(field, getCurrentPlayer(), blockInventory), path)
                case _ => throw new IllegalArgumentException("File type not supported!")
            }
            notifyObservers(PlaceBlockEvent)
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
        private val originalBlocksBefore = blockInventory
        private val originalCurrentBlock = controller.hoverBlock.getBlockType

        override def execute(): Try[Unit] = Try {
            controller.place()
        }

        override def undo(): Unit = {
            controller.field = originalField
            controller.hoverBlock = controller.hoverBlock.newInstance(controller.hoverBlock.getX, controller.hoverBlock.getY,
                controller.playerAmount, controller.hoverBlock.getPlayer, originalCurrentBlock, controller.hoverBlock.getRotation, controller.hoverBlock.getMirrored)
            controller.blockInventory = originalBlocksBefore
            controller.changeBlock(currentBlock)
            notifyObservers(PlaceBlockEvent)
        }
        override def redo(): Try[Unit] = execute()
    }
    def update(event: Event): Unit = {}
}
