package blokus.controller

import blokus.models.{Field, HoverBlock, BlockInventory}
import blokus.util.{Observable, Observer}

import scala.util.{Try, Success, Failure}

// GameController Interface
trait GameController {
    def placeBlock(newBlock: Int): Try[Unit]
    def changeCurrentBlock(newBlock: Int): Try[Unit]
    def move(direction: Int): Boolean
    def rotate(): Boolean
    def mirror(): Boolean
    def canPlace(): Boolean
    def changeBlock(newBlock: Int): Int
    def getRotation(): Int
    def nextPlayer(): Int
    def changePlayer(newPlayer: Int): Try[Unit]
    def undo(): Try[Unit]
    def redo(): Try[Unit]
}

// BlockInventory Interface
trait BlockInventory {
    def useBlock(player: Int, block: Int): Unit
    def getBlocks(player: Int): List[Int]
    def getRandomBlock(player: Int): Option[Int]
    def deepCopy(): BlockInventory
}

// Field Interface
trait FieldInterface {
    def getFieldVector(): Vector[Vector[Int]]
}

// HoverBlock Interface
trait HoverBlockInterface {
    def place(field: FieldInterface, block: Int): FieldInterface
    def getOutOfCorner(height: Int, width: Int): Unit
    def getBlock(): List[(Int, Int)]
    def setCurrentBlock(newBlock: Int): Int
    def move(field: FieldInterface, direction: Int): Boolean
    def rotate(field: FieldInterface): Boolean
    def mirror(field: FieldInterface): Boolean
    def canPlace(field: FieldInterface): Boolean
    def getCurrentPlayer(): Int
    def changePlayer(): Int
}

// Command Interface
trait Command {
    def execute(): Try[Unit]
    def undo(): Unit
    def redo(): Try[Unit]
}

// Observable and Observer Interfaces
trait ObservableInterface[T] {
    def addObserver(observer: Observer[T]): Unit
    def removeObserver(observer: Observer[T]): Unit
    def notifyObservers(event: T): Unit
}

trait Observer[T] {}