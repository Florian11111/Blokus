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

    def getPosPositions(player: Int): List[(Int, Int)] = {
        if (player >= 0 && player < posPositions.length) {
            posPositions(player)
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $player")
        }
    }

    def withPosPositions(player: Int, posPositionsList: List[(Int, Int)]): BlockInventory = {
        if (player >= 0 && player < posPositions.length) {
            val newPosPositions = posPositions.updated(player, posPositionsList)
            new BlockInventory(playerAmount, inventories, isFirstBlock, newPosPositions)
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $player")
        }
    }
    
    def getBlocks(player: Int): List[Int] = {
        if (player >= 0 && player < inventories.length) {
            inventories(player).toList
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $player")
        }
    }

    def setNextBLock(player: Int, currentBlock: Int): Int = {
        if (inventories(player).forall(_ <= 0)) {
            throw new IllegalArgumentException("No blocks left!")
        }
        val current = currentBlock
        var next = (current + 1) % 21
        while (inventories(player)(next) <= 0) {
            next = (next + 1) % 21
        }
        next
    }

    def firstBlock(player: Int): Boolean = {
        isFirstBlock(player)
    }

    def isAvailable(player: Int, blockType: Int): Boolean = {
        if (player >= 0 && player < inventories.length) {
            inventories(player)(blockType) > 0
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $player")
        }
    }

    
    def withUsedBlock(player: Int, blockType: Int): BlockInventory = {
        if (isAvailable(player, blockType)) {
            val newIsFirstBlock = isFirstBlock.updated(player, false)
            val newInventories = inventories(player).updated(blockType, inventories(player)(blockType) - 1)
            new BlockInventory(playerAmount, inventories.updated(player, newInventories), newIsFirstBlock, posPositions)
        } else {
            throw new RuntimeException(s"Block $blockType is not available for Player $player.")
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