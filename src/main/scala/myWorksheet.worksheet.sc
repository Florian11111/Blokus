// Field size
val width = 20
val height = 20

// Convert a row of integers to characters
def rowToString(row: Array[Int]): String = {
  row.map {
    case 0 => "# "
    case 1 => "+ "
    case _ => "? "
  }.mkString
}

// Convert the array to a string
def createFieldString(field: Array[Array[Int]]): String = {
  field.map(rowToString).mkString("\n")
}

def changeField(field: Array[Array[Int]], x: Int, y: Int, inhalt: Int): Array[Array[Int]] = {
  assert(x >= 0 && x < width && y >= 0 && y < height)
  field.updated(x, field(x).updated(y, inhalt))
}


val field = Array.ofDim[Int](height, width).map(_ => Array.fill(width)(1))
val field2 = changeField(field, 5, 4, 0)
val fieldString = createFieldString(field2)

println(fieldString) // Print the resulting fieldString
