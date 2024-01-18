package de.htwg.se.blokus.models.blockInvImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.Random
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory

class BlockInventorySpec extends AnyWordSpec with Matchers {

  "A BlockInventory" when {
    "created with default values" should {
      "initialize all inventories and isFirstBlock correctly" in {
        val playerAmount = 4
        val initialCount = 1

        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        blockInventory.getPlayerAmount() shouldEqual playerAmount
        blockInventory.getAllInventories().foreach(inventory => inventory shouldEqual List.fill(21)(initialCount))
        blockInventory.getAllFirstBlock().foreach(isFirstBlock => isFirstBlock shouldEqual true)
      }

      "initialize all posPositions as empty lists" in {
        val playerAmount = 4
        val initialCount = 1

        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        blockInventory.getAllPotPositions().foreach(posPositions => posPositions shouldEqual List.empty)
      }
    }

    "withPosPositions" should {
      "update the posPositions correctly for a valid playerNumber" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 0
        val newPosPositions = List((1, 2), (3, 4))

        val updatedBlockInventory = blockInventory.withPotPositions(playerNumber, newPosPositions)

        updatedBlockInventory.getPotPositions(playerNumber) shouldEqual newPosPositions
      }

      "throw an exception for an invalid playerNumber" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 5
        val newPosPositions = List((1, 2), (3, 4))

        val exception = intercept[IllegalArgumentException] {
        blockInventory.withPotPositions(playerNumber, newPosPositions)
        }
        exception shouldBe a[IllegalArgumentException]
        exception.getMessage shouldEqual s"Invalid player number: $playerNumber"
      }

      "throw an exception for an negative playerNumber" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = -5
        val newPosPositions = List((1, 2), (3, 4))

        an[IllegalArgumentException] should be thrownBy blockInventory.withPotPositions(playerNumber, newPosPositions)
      }
    }

    "getPosPositions" should {
      "throw an Illegalargumentexception if player number invalid" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)
        an[IllegalArgumentException] should be thrownBy blockInventory.getPotPositions(-1)
      }
    }

    "getBlocks" should {
      "return the correct list of blocks for a valid playerNumber" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 2

        blockInventory.getBlocks(playerNumber) shouldEqual List.fill(21)(initialCount)
      }

      "throw an exception for an invalid playerNumber" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 5

        an[IllegalArgumentException] should be thrownBy blockInventory.getBlocks(playerNumber)
      }
    }

    "calling setNextBlock" should {
      var blockInventory = BlockInventory.getInstance(4, 1)

      "return the next available block for a player" in {
        val playerNumber = 0
        val currentBlock = 0
        val nextBlock = blockInventory.setNextBLock(playerNumber, currentBlock)
        nextBlock should be >= 0
        nextBlock should be < 21
        blockInventory.isAvailable(playerNumber, nextBlock) shouldBe true
      }

      "throw an exception when no blocks are left for a player" in {
        val playerNumber = 1
        val currentBlock = 0

        val updatedInventory = BlockInventory.getInstance(4,0)

        an[IllegalArgumentException] should be thrownBy {
          updatedInventory.setNextBLock(playerNumber, currentBlock)
        }
      }

      "cycle through blocks if the current block is the last one" in {
        val playerNumber = 2
        val currentBlock = 20

        val nextBlock = blockInventory.setNextBLock(playerNumber, currentBlock)
        nextBlock shouldEqual 0
      }

      "cycle through not available blocks if the current block is the last one" in {
        val playerNumber = 2
        val currentBlock = 18

        blockInventory = blockInventory.withUsedBlock(playerNumber, 19)
        blockInventory = blockInventory.withUsedBlock(playerNumber, 20)

        val nextBlock = blockInventory.setNextBLock(playerNumber, currentBlock)
        nextBlock shouldEqual 0
      }
    }

    "firstBlock" should {
      "return true for a player's first block" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 3

        blockInventory.firstBlock(playerNumber) shouldEqual true
      }

      "return false for a player's non-first block" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 2
        val blockType = 5

        blockInventory.withUsedBlock(playerNumber, blockType).firstBlock(playerNumber) shouldEqual false
      }
    }

    "isAvailable" should {
      "return true for an available block" in {
        val playerAmount = 4
        val initialCount = 3
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 2
        val blockType = 10

        blockInventory.isAvailable(playerNumber, blockType) shouldEqual true
      }

      "return false for a used block" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 1
        val blockType = 7

        blockInventory.withUsedBlock(playerNumber, blockType).isAvailable(playerNumber, blockType) shouldEqual false
      }

      "throw an exception for an invalid playerNumber" in {
        val playerAmount = 4
        val initialCount = 3
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 5
        val blockType = 10

        an[IllegalArgumentException] should be thrownBy blockInventory.isAvailable(playerNumber, blockType)
      }
    }

    "withUsedBlock" should {
      "update the inventories correctly for a valid playerNumber and blockType" in {
        val playerAmount = 4
        val initialCount = 3
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 2
        val blockType = 14

        val updatedBlockInventory = blockInventory.withUsedBlock(playerNumber, blockType)

        updatedBlockInventory.getBlocks(playerNumber)(blockType) shouldEqual initialCount - 1
      }

      "throw an exception for an invalid playerNumber" in {
        val playerAmount = 4
        val initialCount = 3
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 5
        val blockType = 10

        an[IllegalArgumentException] should be thrownBy blockInventory.withUsedBlock(playerNumber, blockType)
      }

      "throw an exception for an unavailable blockType" in {
        val playerAmount = 4
        val initialCount = 0
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val playerNumber = 3
        val blockType = 2

        an[RuntimeException] should be thrownBy blockInventory.withUsedBlock(playerNumber, blockType)
      }
    }

    "newInstance" should {
      "create a new BlockInventory instance with the specified values" in {
        val playerAmount = 4
        val initialCount = 1
        val blockInventory = BlockInventory.getInstance(playerAmount, initialCount)

        val newPlayerAmount = 2
        val newInventories = Array.fill(newPlayerAmount)(List.fill(21)(3))
        val newIsFirstBlock = Array.fill(newPlayerAmount)(false)
        val newPosPositions = Array.fill(newPlayerAmount)(List((1, 2), (3, 4)))

        val newInstance = blockInventory.newInstance(newPlayerAmount, newInventories, newIsFirstBlock, newPosPositions)

        newInstance.getPlayerAmount() shouldEqual newPlayerAmount
        newInstance.getAllInventories() shouldEqual newInventories
        newInstance.getAllFirstBlock() shouldEqual newIsFirstBlock
        newInstance.getAllPotPositions() shouldEqual newPosPositions
      }
    }
  }
}
