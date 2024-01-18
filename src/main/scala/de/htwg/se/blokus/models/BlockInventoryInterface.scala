package de.htwg.se.blokus.models

import de.htwg.se.blokus.controller

import de.htwg.se.blokus.models.blockInvImpl.*
import scala.util.Try
/* BlockInventory store infomations of Potansial Place Posions, 
    if its the Player first Place
    and what Blocks a Player has in his inventory */
trait BlockInventoryInterface {
    def getAllInventories(): Array[List[Int]]
    def getAllFirstBlock(): Array[Boolean]
    def getAllPotPositions(): Array[List[(Int, Int)]]
    def getPlayerAmount(): Int
    def getPotPositions(playerNumber: Int): List[(Int, Int)]
    def withPotPositions(playerNumber: Int, potPositionsList: List[(Int, Int)]): BlockInventory
    def getBlocks(playerNumber: Int): List[Int]
    def setNextBLock(player: Int, currentBlock: Int): Int
    def firstBlock(playerNumber: Int): Boolean
    def isAvailable(playerNumber: Int, blockType: Int): Boolean
    def withUsedBlock(playerNumber: Int, blockType: Int): BlockInventory
    def newInstance(playerAmount: Int, inventories2: Array[List[Int]], isFirstBlock2: Array[Boolean], posPositions2: Array[List[(Int, Int)]]): BlockInventory
}

object BlockInventoryInterface {
    def getInstance(playerAmount: Int, initialCount: Int = 1): BlockInventoryInterface = BlockInventory.getInstance(playerAmount, initialCount)
}