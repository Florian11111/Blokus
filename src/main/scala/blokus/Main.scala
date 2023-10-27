package blokus

import models.Field // Angepasst an den tatsächlichen Dateipfad


@main def main: Unit =
  val field = new Field(20, 20)
  field.changeField(5, 4, 0)
  val reihe = field.rowToString(field.getFieldArray(0))
  val fieldString = field.createFieldString

  println(fieldString) // Drucke den resultierenden fieldString
