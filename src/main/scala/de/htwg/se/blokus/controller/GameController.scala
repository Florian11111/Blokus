package de.htwg.se.blokus.controller

import de.htwg.se.blokus.models.{FieldInterface, HoverBlockInterface}
import de.htwg.se.blokus.util.{Observable, Observer}

import scala.util.Try

trait GameController extends Observer[Event] with Observable[Event] {
    def start(playerAmt: Int, firstBlk: Int, w: Int, h: Int): Unit
    def getWidth(): Int 
    def getHeight(): Int 
    def placeBlock(newBlock: Int): Try[Unit]
    def place(neuerTyp: Int): Unit
    def changeCurrentBlock(newBlock: Int): Try[Unit]
    def getCurrentPlayer(): Int
    def getField(): Vector[Vector[Int]]
    def getBlocks(): List[Int]
    def getBlock(): List[(Int, Int)]
    def nextPlayer(): Int
    def move(richtung: Int): Boolean
    def rotate(): Boolean
    def mirror(): Boolean
    def canPlace(): Boolean
    def changePlayer(newPlayer: Int): Try[Unit]
    def undo(): Try[Unit]
    def redo(): Try[Unit]
}