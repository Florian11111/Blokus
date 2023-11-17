package blokus.models

class Field(private val fieldVector: Vector[Vector[Int]]) {
    val width: Int = fieldVector.headOption.map(_.size).getOrElse(0)
    val height: Int = fieldVector.size

    def getFieldVector: Vector[Vector[Int]] = fieldVector

    def isValidPosition(block: List[(Int, Int)], x: Int, y: Int): Boolean = {
        block.forall { case (dy, dx) =>
        val newY = y + dy
        val newX = x + dx
        println("    newX: " + newX + " newY: " + newY +  " => " + (newY >= 0 && newY < height && newX >= 0 && newX < width && fieldVector(newY)(newX) == 1))
        //newY >= 0 && newY < height && newX >= 0 && newX < width && fieldVector(newY)(newX) == 1
        newY >= 0 && newY < height && newX >= 0 && newX < width
        }
    }

    def placeBlock(block: List[(Int, Int)], x: Int, y: Int, newValue: Int): Field = {
        if (isValidPosition(block, x, y)) {
        val updatedField = fieldVector.zipWithIndex.map { case (row, rowIndex) =>
            row.zipWithIndex.map { case (_, colIndex) =>
            if (block.contains((colIndex - x, rowIndex - y)))
                newValue
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
        val initialFieldVector = Vector.fill(height, width)(1)
        new Field(initialFieldVector)
    }
}
