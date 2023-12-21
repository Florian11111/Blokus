package blokus.models

import blokus.controller

import blokus.models.blockInvImpl.*

trait BlockInventoryInterface {
    def getBlocks(playerNumber: Int): List[Int]
    def getRandomBlock(playerNumber: Int): Option[Int]
    def firstBlock(playerNumber: Int): Boolean
    def isAvailable(playerNumber: Int, blockNumber: Int): Boolean
    def useBlock(playerNumber: Int, blockNumber: Int): List[Int]
    def deepCopy: BlockInventoryInterface
}

object BlockInventoryInterface {
    def getInstance(playerAmount: Int, initialCount: Int = 1): BlockInventoryInterface =
    new BlockInventory(playerAmount, initialCount)
}