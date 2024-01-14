package de.htwg.se.blokus.models

import de.htwg.se.blokus.controller

import de.htwg.se.blokus.models.blockInvImpl.*
import scala.util.Random

trait BlockInventoryInterface {
    def getAllInventories(): Array[List[Int]]
    
    def getAllFirstBlock(): Array[Boolean]
    
    def getAllPosPositions(): Array[List[(Int, Int)]]

    def getPlayerAmount(): Int

    def getPosPositions(playerNumber: Int): List[(Int, Int)]

    def withPosPositions(playerNumber: Int, posPositionsList: List[(Int, Int)]): BlockInventory
    
    def getBlocks(playerNumber: Int): List[Int]

    def getRandomBlock(playerNumber: Int, rand: Random): Option[Int]

    def firstBlock(playerNumber: Int): Boolean

    def isAvailable(playerNumber: Int, blockType: Int): Boolean

    def withUsedBlock(playerNumber: Int, blockType: Int): BlockInventory

    def newInstance(playerAmount: Int, inventories2: Array[List[Int]], isFirstBlock2: Array[Boolean], posPositions2: Array[List[(Int, Int)]]): BlockInventory
}

object BlockInventoryInterface {
    def getInstance(playerAmount: Int, initialCount: Int = 1): BlockInventoryInterface = BlockInventory.getInstance(playerAmount, initialCount)
}