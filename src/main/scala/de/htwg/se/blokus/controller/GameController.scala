package de.htwg.se.blokus.controller

import de.htwg.se.blokus.models.{HoverBlockInterface}
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.util.{Observable, Observer}

import scala.util.Try

trait GameController extends Observer[Event] with Observable[Event] {
    /* Starts the Controller */
    def start(playerAmt: Int, w: Int, h: Int): Unit

    /* Exit the Controller */
    def exit(): Unit

    def getWidth(): Int 

    def getHeight(): Int 

    def getPlayerAmount(): Int

    /* return the Amount of Blocks each player has placed */
    def getBlockAmount(): List[Int]

    def isGameOverPlayer(player: Int): Boolean
    def placeBlock(): Try[Unit]
    def canPlace(): Boolean

    /* return all Potansial Positions of a given player */
    def getPosPositions(player: Int): List[(Int, Int)]

    def changeCurrentBlock(newBlock: Int): Try[Unit]
    def setNextBLock(): Try[Unit]

    def getCurrentPlayer(): Int

    /* return the current Field */
    def getField(): Vector[Vector[Int]]

    /* return blocks a player has in ther inventory */
    def getBlocks(): List[Int]

    /* return the current Block (not placed yet) */
    def getBlock(): List[(Int, Int)]

    /* moves the x and y in a given direktion */
    def move(x: Int, y: Int): Boolean

    /* change x and y */
    def setXandY(x: Int, y: Int): Boolean 

    def rotate(): Boolean
    def mirror(): Boolean
    
    def load(path: String): Try[Unit]
    def save(path: String): Try[Unit]

    def undo(): Try[Unit]
    def redo(): Try[Unit]
}