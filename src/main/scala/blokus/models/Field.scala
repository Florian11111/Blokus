package blokus.models

class Field(private val fieldVector: Vector[Vector[Int]]) {
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

    def isValidPlace(block: List[(Int, Int)], x: Int, y: Int, currentPlayer: Int): Boolean = {
        if (isValidPosition(block, x, y)) {
            block.forall { case (dx, dy) =>
            val newY = y + dy
            val newX = x + dx
            fieldVector(newY)(newX) == -1
        }
        } else {
            false
        }
    }
    


    def placeBlock(block: List[(Int, Int)], x: Int, y: Int, currentPlayer: Int): Field = {
        if (isValidPlace(block, x, y, currentPlayer)) {
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
}

object Field {
    def apply(width: Int, height: Int): Field = {
        val initialFieldVector = Vector.fill(height, width)(-1)
        new Field(initialFieldVector)
    }
}
