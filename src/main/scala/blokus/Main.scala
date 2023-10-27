package blokus

import models.Field

@main def main: Unit =
  val field = Field(5, 5) // Verwende das Begleitobjekt Field.apply, um ein Field-Objekt zu erstellen
  val updatedField = field.changeField(2, 2, 0)
  val fieldString = updatedField.createFieldString

  println(fieldString) // Drucke den resultierenden fieldString
