package blokus.models

class Field(val width: Int, val height: Int) {
    private var fieldVector: Vector[Vector[Int]] = Vector.fill(height, width)(1)

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

    // Ändert das Feld an der angegebenen Position
    def changeField(x: Int, y: Int, inhalt: Int): Unit = {
        assert(x >= 0 && x < width && y >= 0 && y < height)
        fieldVector = fieldVector.updated(x, fieldVector(x).updated(y, inhalt))
    }
}
