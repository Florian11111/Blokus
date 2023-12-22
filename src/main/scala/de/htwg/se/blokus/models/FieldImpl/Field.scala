package de.htwg.se.blokus.models.FieldImpl
import de.htwg.se.blokus.models.FieldInterface


class Field(private val fieldVector: Vector[Vector[Int]]) extends FieldInterface {
    val width: Int = fieldVector.headOption.map(_.size).getOrElse(0)
    val height: Int = fieldVector.size

    def getFieldVector: Vector[Vector[Int]] = fieldVector

    def isValidPosition(block: List[(Int, Int)], x: Int, y: Int): Boolean = {
    block.forall { case (dx, dy) =>
        val newY = y + dy
        val newX = x + dx
        newY >= 0 && newY < height && newX >= 0 && newX < width
    }
    }

    private def isInBounds(x: Int, y: Int): Boolean = x >= 0 && x < width && y >= 0 && y < height

    def isGameOver(block: List[(Int, Int)], x: Int, y: Int, currentPlayer: Int): Boolean = {
        false
    }
    
    def isCorner(x: Int, y: Int): Boolean = {
        (x == 0 && y == 0) || (x == width - 1 && y == 0) || (x == 0 && y == height - 1) || (x == width - 1 && y == height - 1)
    }

    def isLogicFirstPlace(block: List[(Int, Int)], x: Int, y: Int): Boolean = {
        isValidPosition(block, x, y) && 
        block.forall {case (dx, dy) => isInBounds(x + dx, y + dy) && fieldVector(y + dy)(x + dx) == -1} &&
        block.exists {case (dx, dy) => isInBounds(x + dx, y + dy) && isCorner(x + dx, y + dy)}
    }

    def isLogicPlace(
        block: List[(Int, Int)],
        ecken: List[(Int, Int)],
        kanten: List[(Int, Int)],
        x: Int,
        y: Int,
        currentPlayer: Int
    ): Boolean = {
        if (isValidPosition(block, x, y)) {
            // Überprüfe, ob keine Kante vom Gegner benachbart ist
            if (kanten.forall { case (dx, dy) => isInBounds(x + dx, y + dy) && fieldVector(y + dy)(x + dx) != currentPlayer }) {
                // Überprüfe, ob an mindestens einer Ecke ein Block vom eigenen Spieler ist
                ecken.exists { case (dx, dy) => isInBounds(x + dx, y + dy) && fieldVector(y + dy)(x + dx) == currentPlayer }
            } else {
                false
            }
            } else {
            false
        }
    }

    // geht durch den block und speichert alle ecken in einer liste. dann wird der block nochmal durchgegannen
    // und alle direkten nachbarn gecheckt. => muss fals sein wenn es einen nachbarn vom selben block gibt der nicht
    // teil des blocks ist und löscht alle direkten nachbarn aus der liste. Dann wird die ecken liste überprüft ob es 
    // mindestens einen match gibt
    /*
    def getAllCorners(blocks: List[List[(Int, Int)]]): List[(Int, Int)] = {
        blocks.flatMap { block =>
        block.flatMap { case (x, y) =>
            List(
            (x + 1, y + 1),
            (x + 1, y - 1),
            (x - 1, y + 1),
            (x - 1, y - 1)
            )
        }
        }.distinct
    }*/


    def placeBlock(block: List[(Int, Int)], ecken: List[(Int, Int)], kanten: List[(Int, Int)], x: Int, y: Int, currentPlayer: Int, firstPlace: Boolean): Field = {
        if (isLogicPlace(block, ecken, kanten, x, y, currentPlayer) || (firstPlace && isLogicFirstPlace(block, x, y))) {
            val updatedField = fieldVector.zipWithIndex.map { case (row, rowIndex) =>
                row.zipWithIndex.map { case (_, colIndex) =>
                if (block.contains((colIndex - x, rowIndex - y)))
                    currentPlayer
                else
                    fieldVector(rowIndex)(colIndex)
                }
            }
            new Field(updatedField)
        } else {
            throw new IllegalArgumentException("Invalid position for block placement.")
        }
    }
    def copy(): Field = {
        val copiedVector = fieldVector.map(_.toVector).toVector
        new Field(copiedVector)
    }
}

object Field {
    private var instance: Option[Field] = None

    def getInstance(width: Int, height: Int): Field = {
        instance.getOrElse {
        val initialFieldVector = Vector.fill(height, width)(-1)
        val fieldInstance = new Field(initialFieldVector)
        instance = Some(fieldInstance)
        fieldInstance
        }
    }

    def apply(width: Int, height: Int): Field = new Field(Vector.fill(height, width)(-1))
}