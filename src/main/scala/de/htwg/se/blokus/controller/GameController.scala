package de.htwg.se.blokus.controller

import de.htwg.se.blokus.models.{HoverBlockInterface}
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.util.{Observable, Observer}

import scala.util.Try

trait GameController extends Observer[Event] with Observable[Event] {
    def TESTMETHOD(player: Int): List[(Int, Int)]
    def start(playerAmt: Int, w: Int, h: Int): Unit
    def exit(): Unit


    def getWidth(): Int 
    def getHeight(): Int 
    def getPlayerAmount(): Int
    def getBlockAmount(): List[Int]
    def isGameOverPlayer(player: Int): Boolean
    def placeBlock(): Try[Unit]
    def canPlace(): Boolean
    def changeCurrentBlock(newBlock: Int): Try[Unit]
    def getCurrentPlayer(): Int
    def getField(): Vector[Vector[Int]]
    def getBlocks(): List[Int]
    def getBlock(): List[(Int, Int)]
    def move(richtung: Int): Boolean
    def setXandY(x: Int, y: Int): Boolean 
    def rotate(): Boolean
    def mirror(): Boolean
    
    
    def undo(): Try[Unit]
    def redo(): Try[Unit]
}