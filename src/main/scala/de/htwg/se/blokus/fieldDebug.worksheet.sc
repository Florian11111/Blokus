import de.htwg.se.blokus.models.Block
import scala.util.Random
import de.htwg.se.blokus.models.HoverBlockInterface
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock
import de.htwg.se.blokus.models.fileIoImpl.fileIoXmlImpl
import de.htwg.se.blokus.models.fileIoImpl.fileIoJsonImpl
import de.htwg.se.blokus.models.{BlockInventoryInterface, FieldInterface, GameState}
import de.htwg.se.blokus.models.fieldImpl.Field
import de.htwg.se.blokus.models.BlockInventoryInterface.*
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory
import de.htwg.se.blokus.models.fieldImpl.Field

import de.htwg.se.blokus.models.GameState

var field = new Field(Vector(Vector(-1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1)))
var blockInventory = new BlockInventory(2, initialCount = 2);

val playerAmount = 2

var hoverBlock = HoverBlockInterface.getInstance(3, 0, 2, 10, 0, false)
hoverBlock.setPlayer(0)
place()
field.getFieldVector

hoverBlock = HoverBlockInterface.getInstance(0, 3, 2, 7, 0, false)
hoverBlock.setPlayer(1)
place()
field.getFieldVector

val result1 = blockInventory.getPosPositions(1)

result1

hoverBlock = HoverBlockInterface.getInstance(3, 2, 2, 0, 0, false)
hoverBlock.setPlayer(0)
place()
field.getFieldVector


val result = blockInventory.getAllFirstBlock()
for (i <- result) {
    print(i.toString() + "\n")
}

isGameOverPlayer(0)
isGameOverPlayer(1)

def getCurrentPlayer(): Int = {
    hoverBlock.getPlayer
}

def place(): Boolean = {
        if (canPlace()) {
            field = field.placeBlock(hoverBlock, blockInventory.firstBlock(getCurrentPlayer()))
            blockInventory.useBlock(getCurrentPlayer(), hoverBlock.getBlockType)
            // print("Player " + (getCurrentPlayer()) + ": " + blockInventory.firstBlock(getCurrentPlayer()) + "\n")
            blockInventory.setPosPositions(getCurrentPlayer(), addPotentialPositionsToInventory(getCurrentPlayer()))
            hoverBlock.setX((field.width / 2) - 1)
            hoverBlock.setY((field.height / 2) - 1)
            hoverBlock.setRotation(0)
            hoverBlock.setMirrored(false)
            switchPlayerAndCheckGameOver()
            hoverBlock.setBlockType(blockInventory.getRandomBlock(hoverBlock.getPlayer, Random).get)
            for (i <- 0 until playerAmount) {
                blockInventory.setPosPositions(i, filterPotentialPositions(i))
            }
            true
        } else {
            false
        }
    }

def canPlace(): Boolean = {
    val firstPlace = blockInventory.firstBlock(getCurrentPlayer())
    firstPlace && field.isInCorner(hoverBlock) || field.isGameRuleConfirm(hoverBlock)
}

def addPotentialPositionsToInventory(player: Int): List[(Int, Int)] = {
    val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
    var posPositions = blockInventory.getPosPositions(player)
    (posPositions ++ block.corners.map(e => (e._1 + hoverBlock.getX, e._2 + hoverBlock.getY)))
}

def isGameOverPlayer(player: Int): Boolean = {
    var posPositions = blockInventory.getPosPositions(player)
    posPositions.isEmpty && !blockInventory.firstBlock(player) || blockInventory.getBlocks(getCurrentPlayer()).forall(_ == 0)
}

def switchPlayerAndCheckGameOver(): Boolean = {
    if ((0 until playerAmount).forall(isGameOverPlayer)) {
        print("Game Over")
    }
    var nextPlayer = (getCurrentPlayer() + 1) % playerAmount
    
    for (i <- 0 until playerAmount) {
        if (isGameOverPlayer(nextPlayer)) {
            nextPlayer = (nextPlayer + 1) % playerAmount
        }
    }
    hoverBlock.setPlayer(nextPlayer)
    false
}

def isValidPotentialPositions(x: Int, y: Int, player: Int): Boolean = {
        val neighbors = List((x-1, y), (x+1, y), (x, y-1), (x, y+1))
        val fieldtemp = field.getFieldVector.transpose
        val ergenis = field.isInBounds(x, y) && fieldtemp(x)(y) == -1
        var allValid = true
        for ((nx, ny) <- neighbors) {
            if (!(nx < 0 || ny < 0 || nx >= field.width || ny >= field.height ||
                (nx >= 0 && ny >= 0 && nx < field.width && ny < field.height && fieldtemp(nx)(ny) != player))) { // changed
                allValid = false
            }
        }
        allValid && ergenis
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
                        val tempHoverBlock =  HoverBlock.createInstance(ecke._1, ecke._2, 2, block, i, j) // changed
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