class Field(val width: Int, val height: Int) {
  private var fieldArray: Array[Array[Int]] = Array.ofDim[Int](height, width).map(_ => Array.fill(width)(1))

  def getFieldArray: Array[Array[Int]] = fieldArray

  // Konvertiert eine Zeile von Ganzzahlen in Zeichen
  def rowToString(row: Array[Int]): String = {
    row.map {
      case 0 => "# "
      case 1 => "+ "
      case _ => "? "
    }.mkString
  }

  // Konvertiert das Array in einen String
  def createFieldString: String = {
    getFieldArray.map(rowToString).mkString("\n")
  }

  // Ändert das Feld an der angegebenen Position
  def changeField(x: Int, y: Int, inhalt: Int): Unit = {
    assert(x >= 0 && x < width && y >= 0 && y < height)
    fieldArray = fieldArray.updated(x, fieldArray(x).updated(y, inhalt))
  }
}

object FieldMain {
  val field = new Field(20, 20)
  field.changeField(5, 4, 0)
  val reihe = field.rowToString(field.getFieldArray(0))
  val fieldString = field.createFieldString

  println(fieldString) // Drucke den resultierenden fieldString
  
}
