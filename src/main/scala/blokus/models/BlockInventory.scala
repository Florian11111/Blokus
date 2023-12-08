package blokus.models

import scala.util.Random

class BlockInventory(playerAmount: Int, initialCount: Int = 1) {
    private var inventories: Array[Array[Int]] = Array.fill(playerAmount, 21)(initialCount)

    def getBlocks(spielerNumber: Int): List[Int] = inventories(spielerNumber).toList

    def getRandomBlock(spielerNumber: Int): Option[Int] = {
            val availableBlocks = getAvailableBlocks(spielerNumber)
            if (availableBlocks.nonEmpty) {
            val randomIndex = Random.nextInt(availableBlocks.length)
            val randomBlock = availableBlocks(randomIndex)
            Some(randomBlock)
        } else {
            throw new RuntimeException(s"No Block is available for Player $spielerNumber.")
        }
    }

    def isAvailable(spielerNumber: Int, blockNumber: Int): Boolean = {
        inventories(spielerNumber)(blockNumber) > 0
    }

    def useBlock(spielerNumber: Int, blockNumber: Int): List[Int] = {
        if (isAvailable(spielerNumber, blockNumber)) {
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
