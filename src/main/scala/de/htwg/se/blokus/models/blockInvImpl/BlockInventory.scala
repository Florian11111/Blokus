package de.htwg.se.blokus.models.blockInvImpl

import de.htwg.se.blokus.models.BlockInventoryInterface
import scala.util.Random

class BlockInventory(val playerAmount: Int, 
    val inventories: Array[List[Int]], 
    val isFirstBlock: Array[Boolean],
    val posPositions: Array[List[(Int, Int)]]) extends BlockInventoryInterface {

    def getAllInventories(): Array[List[Int]] = inventories
    
    def getAllFirstBlock(): Array[Boolean] = isFirstBlock
    
    def getAllPosPositions(): Array[List[(Int, Int)]] = posPositions

    def getPlayerAmount(): Int = playerAmount

    def getPosPositions(playerNumber: Int): List[(Int, Int)] = {
        if (playerNumber >= 0 && playerNumber < posPositions.length) {
            posPositions(playerNumber)
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }

    def withPosPositions(playerNumber: Int, posPositionsList: List[(Int, Int)]): BlockInventory = {
        if (playerNumber >= 0 && playerNumber < posPositions.length) {
            val newPosPositions = posPositions.updated(playerNumber, posPositionsList)
            new BlockInventory(playerAmount, inventories, isFirstBlock, newPosPositions)
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }
    
    def getBlocks(playerNumber: Int): List[Int] = {
        if (playerNumber >= 0 && playerNumber < inventories.length) {
            inventories(playerNumber).toList
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }

    def getRandomBlock(playerNumber: Int, rand: Random): Option[Int] = {
        if (playerNumber >= 0 && playerNumber < inventories.length) {
        val availableBlocks = getAllInventories()(playerNumber)
        if (availableBlocks.nonEmpty) {
            val randomIndex = rand.nextInt(availableBlocks.length)
            val randomBlock = availableBlocks(randomIndex)
            Some(randomBlock)
        } else {
            throw new RuntimeException(s"No Block is available for Player $playerNumber.")
        }
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }

    def firstBlock(playerNumber: Int): Boolean = {
        isFirstBlock(playerNumber)
    }

    def isAvailable(playerNumber: Int, blockType: Int): Boolean = {
        if (playerNumber >= 0 && playerNumber < inventories.length) {
            inventories(playerNumber)(blockType) > 0
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $playerNumber")
        }
    }

    
    def withUsedBlock(playerNumber: Int, blockType: Int): BlockInventory = {
        if (isAvailable(playerNumber, blockType)) {
            val newIsFirstBlock = isFirstBlock.updated(playerNumber, false)
            val newInventories = inventories(playerNumber).updated(blockType, inventories(playerNumber)(blockType) - 1)
            new BlockInventory(playerAmount, inventories.updated(playerNumber, newInventories), newIsFirstBlock, posPositions)
        } else {
            throw new RuntimeException(s"Block $blockType is not available for Player $playerNumber.")
        }
    }

    def newInstance(playerAmount: Int, inventories2: Array[List[Int]], isFirstBlock2: Array[Boolean], posPositions2: Array[List[(Int, Int)]]): BlockInventory = {
        new BlockInventory(playerAmount, inventories2, isFirstBlock2, posPositions2)
    }
}

object BlockInventory {
    def getInstance(playerAmount: Int, initialCount: Int = 1): BlockInventory = {
        new BlockInventory(playerAmount, Array.fill(playerAmount)(List.fill(21)(initialCount)), 
        Array.fill(playerAmount)(true), 
        Array.fill(playerAmount)(List.empty))
    }
}