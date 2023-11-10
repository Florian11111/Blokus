package blokus.models

class Field(private val fieldVector: Vector[Vector[Int]]) {
    val width: Int = fieldVector.headOption.map(_.size).getOrElse(0)
    val height: Int = fieldVector.size

    def getFieldVector: Vector[Vector[Int]] = fieldVector

    // Konvertiert eine Zeile von Ganzzahlen in Zeichen
    def rowToString(row: Vector[Int]): String = {
        row.map {
            case 0 => "# "
            case 1 => "+ "
            case 2 => "| "
            case _ => "? "
        }.mkString
    }

    // Konvertiert das Vector in einen String
    def createFieldString: String = {
        getFieldVector.map(rowToString).mkString("\n")
    }

    def changeField(x: Int, y: Int, inhalt: Int): Field = {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            val newRow = fieldVector(x).updated(y, inhalt)
            val newFieldVector = fieldVector.updated(x, newRow)
            new Field(newFieldVector)
        } else {
            throw new IllegalArgumentException("Ungültige Position für Änderung des Felds.")
        }
    }

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
                row.zipWithIndex.map { case (_, colIndex) =>
                    if (block.contains((rowIndex - x, colIndex - y))) newValue else fieldVector(rowIndex)(colIndex)
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
