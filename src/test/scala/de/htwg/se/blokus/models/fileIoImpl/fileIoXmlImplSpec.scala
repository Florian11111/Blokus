package de.htwg.se.blokus.models.fileIoImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.io.File
import scala.io.Source
import de.htwg.se.blokus.models.fieldImpl.Field
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory
import de.htwg.se.blokus.models.GameState


class FileIoXmlImplSpec extends AnyWordSpec with Matchers {
  val testFilePath = "src/test/scala/de/htwg/se/blokus/models/fileIoImpl/test.xml" // Pfad zur Testdatei

  "FileIoXmlImpl" should {
    "load and save GameState correctly" in {
      val fieldVector = Vector(Vector(1, 2), Vector(3, 4))
      val field = new Field(fieldVector)
      val blockInventory = new BlockInventory(4, Array.fill(4)(List(1, 2, 3, 4)), Array.fill(4)(true), Array.fill(4)(List((1, 1), (2, 2))))

      val gameState = new GameState(field, 1, blockInventory)
      val fileIo = new fileIoXmlImpl

      // Speichern des GameState in einer Testdatei
      fileIo.save(gameState, testFilePath)

      // Laden des GameState aus der Testdatei
      val loadedGameState = fileIo.load(testFilePath)

      // Überprüfen, ob das geladene GameState den erwarteten Werten entspricht
      loadedGameState.getField().getFieldVector shouldEqual fieldVector
      loadedGameState.getCurrentPlayer() shouldEqual 1
      loadedGameState.getBlockInventory().getPlayerAmount() shouldEqual 4
      loadedGameState.getBlockInventory().getAllInventories() shouldEqual Array.fill(4)(List(1, 2, 3, 4))
      loadedGameState.getBlockInventory().getAllFirstBlock() shouldEqual Array.fill(4)(true)
      loadedGameState.getBlockInventory().getAllPotPositions() shouldEqual Array.fill(4)(List((1, 1), (2, 2)))

      // Testdatei löschen
      new File(testFilePath).delete()
    }
  }
}
