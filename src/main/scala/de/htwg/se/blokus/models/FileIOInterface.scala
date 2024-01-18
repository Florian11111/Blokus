package de.htwg.se.blokus.models

import scala.util.Try
import de.htwg.se.blokus.models.GameState
import de.htwg.se.blokus.models.fileIoImpl.fileIoJsonImpl
import de.htwg.se.blokus.models.fileIoImpl.fileIoXmlImpl

trait FileIOInterface {
    def load(path: String): GameState
    def save(grid: GameState, path: String): Try[Unit]
}

object FileIOInterface {
    def getInstanceXml(): FileIOInterface = new fileIoXmlImpl()
    def getInstanceJson(): FileIOInterface = new fileIoJsonImpl()
}
