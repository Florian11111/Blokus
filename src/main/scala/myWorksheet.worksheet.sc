// Field size
val width = 20
val height = 20

// Convert the array to a string
def createFieldString(field: Array[Array[Int]]): String = {
  // Map over rows and elements to convert to characters
    field.map { row =>
        row.map {
        case 0 => "# " 
        case 1 => "+ " 
        case _ => "? "
    }.mkString
  }.mkString("\n") // Use "\n" to join rows
}

def changeField(field: Array[Array[Int]], x: Int, y: Int, inhalt: Int): Array[Array[Int]] = {
    assert(x >= 0 && x < width && y >= 0 && y < height)
    field.updated(x, field(x).updated(y, inhalt))
}

val field = Array.ofDim[Int](height, width).map(_ => Array.fill(width)(1)) // Use _ => to fill with 1
val field2 = changeField(field, 5, 4, 0)
val fieldString = createFieldString(field2)
