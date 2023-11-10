package blokus.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should "initialize with default values" in {
    val playerAmount = 4
    val firstBlock = 0
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlock, width, height)

    controller.getfield() shouldEqual Field(width, height).getFieldVector
    controller.getBlock() shouldEqual Block.createBlock(firstBlock, 0, false)
    controller.getCurrentPlayer shouldEqual 0
  }

  it should "correctly move the hover block" in {
    val playerAmount = 4
    val firstBlock = 0
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlock, width, height)

    val initialX = controller.hoverBlock.getX()
    val initialY = controller.hoverBlock.getY()

    controller.move(controller.field, 0) shouldEqual true
    controller.hoverBlock.getX() shouldEqual initialX
    controller.hoverBlock.getY() shouldEqual initialY + 1
  }

  it should "correctly rotate the hover block" in {
    val playerAmount = 4
    val firstBlock = 0
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlock, width, height)

    val initialRotation = controller.hoverBlock.rotation

    controller.rotate(controller.field) shouldEqual true
    controller.hoverBlock.rotation shouldEqual (initialRotation + 1) % 4
  }

  it should "correctly mirror the hover block" in {
    val playerAmount = 4
    val firstBlock = 0
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlock, width, height)

    val initialMirrored = controller.hoverBlock.mirrored

    controller.mirror(controller.field) shouldEqual true
    controller.hoverBlock.mirrored shouldEqual !initialMirrored
  }

  it should "correctly place the block on the field" in {
    val playerAmount = 4
    val firstBlock = 0
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlock, width, height)
    val newBlockType = 1

    val newField = controller.setzen(newBlockType)
    newField.getFieldVector shouldEqual controller.field.placeBlock(controller.getBlock(), 2, 2, controller.getCurrentPlayer).getFieldVector
    controller.hoverBlock.getX() shouldEqual 2
    controller.hoverBlock.getY() shouldEqual 2
    controller.hoverBlock.getCurrentPlayer shouldEqual 0
    controller.getBlock() shouldEqual Block.createBlock(newBlockType, 0, false)
  }

  it should "correctly change the player" in {
    val playerAmount = 4
    val firstBlock = 0
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlock, width, height)

    controller.changePlayer shouldEqual 1
    controller.changePlayer shouldEqual 2
    controller.changePlayer shouldEqual 3
    controller.changePlayer shouldEqual 0
  }
}
