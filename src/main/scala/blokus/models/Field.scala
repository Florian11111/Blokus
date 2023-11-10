package blokus.models

class Field(private val fieldVector: Vector[Vector[Int]]) {
    val width: Int = fieldVector.headOption.map(_.size).getOrElse(0)
    val height: Int = fieldVector.size

    def getFieldVector: Vector[Vector[Int]] = fieldVector

    def isValidPosition(block: List[(Int, Int)], x: Int, y: Int): Boolean = {
        block.forall { case (dx, dy) =>
            val newX = x + dx
            val newY = y + dy
            newX >= 0 && newX < width && newY >= 0 && newY < height
        }
    }


    def placeBlock(block: List[(Int, Int)], x: Int, y: Int, newValue: Int): Field = {
    if (isValidPosition(block, x, y)) {
        val updatedField = fieldVector.zipWithIndex.map { case (row, rowIndex) =>
        row.zipWithIndex.map {
            case (_, colIndex) =>
            if (block.contains((colIndex - x, rowIndex - y))) // Vertauschte X- und Y-Koordinaten
                newValue
            else
                fieldVector(rowIndex)(colIndex)
        }
        }
        new Field(updatedField)
    } else {
        throw new IllegalArgumentException("Ungültige Position für Platzierung des Blocks.")
    }
    }


}

object Field {
    def apply(width: Int, height: Int): Field = {
        val initialFieldVector = Vector.fill(height, width)(1)
        new Field(initialFieldVector)
    }
}
