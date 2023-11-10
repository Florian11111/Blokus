package blokus.controller
import blokus.models.*

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {
    "initialize with default values" in {
      val playerAmount = 4
      val firstBlock = 0
      val width = 10
      val height = 10
      val controller = new Controller(playerAmount, firstBlock, width, height)

      "have the initial field" in {
        controller.getfield() shouldEqual Field(width, height).getFieldVector
      }

      "have the initial block" in {
        controller.getBlock() shouldEqual Block.createBlock(firstBlock, 0, false)
      }

      "have the initial player" in {
        controller.getCurrentPlayer() shouldEqual 0
      }
    }

    "correctly move the hover block" in {
      val playerAmount = 4
      val firstBlock = 0
      val width = 10
      val height = 10
      val controller = new Controller(playerAmount, firstBlock, width, height)

      "when moving down" in {
        val initialY = controller.hoverBlock.getY()
        controller.move(0) shouldEqual true
        controller.hoverBlock.getY() shouldEqual initialY + 1
      }
    }

    "correctly rotate the hover block" in {
      val playerAmount = 4
      val firstBlock = 0
      val width = 10
      val height = 10
      val controller = new Controller(playerAmount, firstBlock, width, height)

      "when rotating clockwise" in {
        val initialRotation = controller.hoverBlock.rotation
        controller.rotate() shouldEqual true
        controller.hoverBlock.rotation shouldEqual (initialRotation + 1) % 4
      }
    }

    "correctly mirror the hover block" in {
      val playerAmount = 4
      val firstBlock = 0
      val width = 10
      val height = 10
      val controller = new Controller(playerAmount, firstBlock, width, height)

      "when mirroring horizontally" in {
        val initialMirrored = controller.hoverBlock.mirrored
        controller.mirror() shouldEqual true
        controller.hoverBlock.mirrored shouldEqual !initialMirrored
      }
    }

    "correctly place the block on the field" in {
      val playerAmount = 4
      val firstBlock = 0
      val width = 10
      val height = 10
      val controller = new Controller(playerAmount, firstBlock, width, height)
      val newBlockType = 1

      "when placing a new block" in {
        val newField = controller.setzen(newBlockType)
        val expectedField = controller.field.placeBlock(controller.getBlock(), 2, 2, controller.getCurrentPlayer())

        "have the updated field" in {
          newField.getFieldVector shouldEqual expectedField.getFieldVector
        }

        "reset the hover block's position and player" in {
          controller.hoverBlock.getX() shouldEqual 2
          controller.hoverBlock.getY() shouldEqual 2
          controller.hoverBlock.getCurrentPlayer shouldEqual 0
        }

        "have the updated block" in {
          controller.getBlock() shouldEqual Block.createBlock(newBlockType, 0, false)
        }
      }
    }

    "correctly change the player" in {
      val playerAmount = 4
      val firstBlock = 0
      val width = 10
      val height = 10
      val controller = new Controller(playerAmount, firstBlock, width, height)

      "when changing the player" in {
        controller.changePlayer() shouldEqual 1
        controller.changePlayer() shouldEqual 2
        controller.changePlayer() shouldEqual 3
        controller.changePlayer() shouldEqual 0
      }
    }
  }
}
