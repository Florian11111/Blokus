package blokus.models.blockInvImpl

import blokus.models.BlockInventoryInterface
import scala.util.Random

class BlockInventory(playerAmount: Int, initialCount: Int = 1) extends BlockInventoryInterface {
    private var inventories: Array[Array[Int]] = Array.fill(playerAmount, 21)(initialCount)
    private var isFirstBlock: Array[Boolean] = Array.fill(playerAmount)(true)

    def getBlocks(spielerNumber: Int): List[Int] = {
        if (spielerNumber >= 0 && spielerNumber < inventories.length) {
            inventories(spielerNumber).toList
        } else {
            throw new IllegalArgumentException(s"Invalid player number: $spielerNumber")
        }
    }

    def getRandomBlock(spielerNumber: Int): Option[Int] = {
        if (spielerNumber >= 0 && spielerNumber < inventories.length) {
        val availableBlocks = getAvailableBlocks(spielerNumber)
        if (availableBlocks.nonEmpty) {
            val randomIndex = Random.nextInt(availableBlocks.length)
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
            inventories(spielerNumber)(blockNumber) -= 1
            getBlocks(spielerNumber)
        } else {
            throw new RuntimeException(s"Block $blockNumber is not available for Player $spielerNumber.")
        }
    }

    def deepCopy: BlockInventory = {
        val copy = new BlockInventory(playerAmount)
        for {
            player <- 0 until playerAmount
            block <- 0 until inventories(player).length
        } {
            copy.inventories(player)(block) = inventories(player)(block)
        }
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