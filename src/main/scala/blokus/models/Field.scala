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
            case _ => "? "
        }.mkString
    }

    // Konvertiert das Vector in einen String
    def createFieldString: String = {
        getFieldVector.map(rowToString).mkString("\n")
    }

    def changeField(x: Int, y: Int, inhalt: Int): Field = {
        assert(x >= 0 && x < width && y >= 0 && y < height)
        val newRow = fieldVector(x).updated(y, inhalt)
        val newFieldVector = fieldVector.updated(x, newRow)
        new Field(newFieldVector)
    }
}

object Field {
    def apply(width: Int, height: Int): Field = {
        val initialFieldVector = Vector.fill(height, width)(1)
        new Field(initialFieldVector)
    }
}