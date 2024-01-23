package de.htwg.se.blokus.models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blokus.models.{FieldInterface, BlockInventoryInterface}

class GameStateSpec extends AnyWordSpec with Matchers {

  "A GameState" should {
    "initialize with the provided field, currentPlayer, and blockInventory" in {
      val mockField = FieldInterface.getInstance(10,10)
      val mockBlockInventory = BlockInventoryInterface.getInstance(1,1)
      val gameState = new GameState(mockField, 1, mockBlockInventory)

      gameState.getField() should be(mockField)
      gameState.getCurrentPlayer() should be(1)
      gameState.getBlockInventory() should be(mockBlockInventory)
    }
  }
}