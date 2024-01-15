package de.htwg.se.blokus.models.hoverBlockImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HoverBlockSpec extends AnyWordSpec with Matchers {
  "HoverBlock" should {
    "correctly initialize its properties" in {
      val currentX = 2
      val currentY = 3
      val playerAmount = 4
      val currentPlayer = 1
      val blockType = 0
      val rotation = 2
      val mirrored = true

      val hoverBlock = HoverBlock.createInstance(currentX, currentY, playerAmount, currentPlayer, blockType, rotation, mirrored)

      hoverBlock.getX shouldEqual currentX
      hoverBlock.getY shouldEqual currentY
      hoverBlock.getPlayer shouldEqual currentPlayer
      hoverBlock.getBlockType shouldEqual blockType
      hoverBlock.getRotation shouldEqual rotation
      hoverBlock.getMirrored shouldEqual mirrored
    }

    "create a new instance of HoverBlock with the given parameters" in {
      val currentX = 2
      val currentY = 3
      val playerAmount = 4
      val currentPlayer = 1
      val blockType = 0
      val rotation = 2
      val mirrored = true

      val hoverBlock = HoverBlock.createInstance(currentX, currentY, playerAmount, currentPlayer, blockType, rotation, mirrored)
      val newX = 5
      val newY = 6
      val newPlayerAmount = 3
      val newPlayer = 2
      val newBlockType = 1
      val newRotation = 1
      val newMirrored = false

      val newHoverBlock = hoverBlock.newInstance(newX, newY, newPlayerAmount, newPlayer, newBlockType, newRotation, newMirrored)

      newHoverBlock.getX shouldEqual newX
      newHoverBlock.getY shouldEqual newY
      newHoverBlock.getPlayer shouldEqual newPlayer
      newHoverBlock.getBlockType shouldEqual newBlockType
      newHoverBlock.getRotation shouldEqual newRotation
      newHoverBlock.getMirrored shouldEqual newMirrored
    }

    "return the correct player amount" in {
      val currentX = 2
      val currentY = 3
      val playerAmount = 4
      val currentPlayer = 1
      val blockType = 0
      val rotation = 2
      val mirrored = true

      val hoverBlock = HoverBlock.createInstance(currentX, currentY, playerAmount, currentPlayer, blockType, rotation, mirrored)

      hoverBlock.getplayerAmount shouldEqual playerAmount
    }
  }
}
