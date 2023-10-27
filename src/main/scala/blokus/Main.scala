package blokus

import models.Field

@main def main: Unit =
  val field = new Field(20, 20)
  field.changeField(5, 4, 0)
  val reihe = field.rowToString(field.getFieldVector(0))
  val fieldString = field.createFieldString

  println(fieldString) // Drucke den resultierenden fieldString
