package de.htwg.se.blokus.models

import de.htwg.se.blokus.controller

import de.htwg.se.blokus.models.blockInvImpl.*
import scala.util.Random

trait BlockInventoryInterface {
    def setPosPositions(playerNumber: Int, eckenList: List[(Int, Int)]): Unit
    def getPosPositions(playerNumber: Int): List[(Int, Int)] 
    def getBlocks(playerNumber: Int): List[Int]
    def getRandomBlock(playerNumber: Int, rand: Random): Option[Int]
    def firstBlock(playerNumber: Int): Boolean
    def isAvailable(playerNumber: Int, blockNumber: Int): Boolean
    def useBlock(playerNumber: Int, blockNumber: Int): List[Int]
    def deepCopy: BlockInventoryInterface
}

object BlockInventoryInterface {
    def getInstance(playerAmount: Int, initialCount: Int = 1): BlockInventoryInterface =
    new BlockInventory(playerAmount, initialCount)
}