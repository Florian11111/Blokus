package de.htwg.se.blokus.models.blockInvImpl

import de.htwg.se.blokus.models.BlockInventoryInterface
import scala.util.Random

class BlockInventory(playerAmount2: Int, initialCount: Int = 1) extends BlockInventoryInterface {
    private var playerAmount = playerAmount2
    private var inventories: Array[List[Int]] = Array.fill(playerAmount)(List.fill(21)(initialCount))
    private var isFirstBlock: Array[Boolean] = Array.fill(playerAmount)(true)
    private var posPositions: Array[List[(Int, Int)]] = Array.fill(playerAmount)(List.empty)
    
    def getAllInventories(): Array[List[Int]] = inventories
    def setAllInventories(inventories2: Array[List[Int]]): Array[List[Int]] = {
        val oldInventories = inventories
        inventories = inventories2
        oldInventories
    }
    
    def getAllFirstBlock(): Array[Boolean] = isFirstBlock
    def setAllFirstBlock(isFirstBlock2: Array[Boolean]): Array[Boolean] = {
        val oldIsFirstBlock = isFirstBlock
        isFirstBlock = isFirstBlock2
        oldIsFirstBlock
    }
    
    def getAllPosPositions(): Array[List[(Int, Int)]] = posPositions
    def setAllPosPositions(posPositions2: Array[List[(Int, Int)]]): Array[List[(Int, Int)]] = {
        val oldPosPositions = posPositions
        posPositions = posPositions2
        oldPosPositions
    }

    def getPlayerAmount(): Int = playerAmount
    
    def setPlayerAmount(playerAmount2: Int): Unit = {
        playerAmount = playerAmount2
    }

    def getPosPositions(playerNumber: Int): List[(Int, Int)] = {
        if (playerNumber >= 0 && playerNumber < posPositions.length) {
            posPositions(playerNumber)
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }
    def setPosPositions(playerNumber: Int, posPositionsList: List[(Int, Int)]): Unit = {
        if (playerNumber >= 0 && playerNumber < posPositions.length) {
            posPositions(playerNumber) = posPositionsList
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }
    
    def getBlocks(spielerNumber: Int): List[Int] = {
        if (spielerNumber >= 0 && spielerNumber < inventories.length) {
            inventories(spielerNumber).toList
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $spielerNumber")
        }
    }

    def getRandomBlock(spielerNumber: Int, rand: Random): Option[Int] = {
        if (spielerNumber >= 0 && spielerNumber < inventories.length) {
        val availableBlocks = getAvailableBlocks(spielerNumber)
        if (availableBlocks.nonEmpty) {
            val randomIndex = rand.nextInt(availableBlocks.length)
            val randomBlock = availableBlocks(randomIndex)
            Some(randomBlock)
        } else {
            throw new RuntimeException(s"No Block is available for Player $spielerNumber.")
        }
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $spielerNumber")
        }
    }

    def firstBlock(spielerNumber: Int): Boolean = {
        isFirstBlock(spielerNumber)
    }

    def isAvailable(spielerNumber: Int, blockNumber: Int): Boolean = {
        if (spielerNumber >= 0 && spielerNumber < inventories.length) {
            inventories(spielerNumber)(blockNumber) > 0
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $spielerNumber")
        }
    }

    def useBlock(spielerNumber: Int, blockNumber: Int): List[Int] = {
        if (isAvailable(spielerNumber, blockNumber)) {
            isFirstBlock(spielerNumber) = false
            inventories(spielerNumber) = inventories(spielerNumber).updated(blockNumber, inventories(spielerNumber)(blockNumber) - 1)
            getBlocks(spielerNumber)
        } else {
            throw new RuntimeException(s"Block $blockNumber is not available for Player $spielerNumber.")
        }
    }

    def deepCopy: BlockInventory = {
        val copy = new BlockInventory(playerAmount)
        for (player <- 0 until playerAmount) {
            val copiedInventory = inventories(player).map(identity) // clone each block
            copy.inventories(player) = copiedInventory
        }
        copy.isFirstBlock = isFirstBlock.clone() // clone isFirstBlock
        copy.posPositions = posPositions.map(_.map(identity)) // clone posPositions
        copy
    }

    private def getAvailableBlocks(spielerNumber: Int): List[Int] = {
        if (inventories.length > spielerNumber && inventories(spielerNumber).nonEmpty) {
            inventories(spielerNumber).indices.filter(isAvailable(spielerNumber, _)).toList
        } else {
            List.empty
        }
    }
}

object BlockInventory {
    def getInstance(playerAmount: Int, initialCount: Int = 1): BlockInventory = {
        new BlockInventory(playerAmount, initialCount)
    }
}