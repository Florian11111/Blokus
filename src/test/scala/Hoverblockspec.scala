package blokus.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HoverBlockSpec extends AnyWordSpec with Matchers {

  "A HoverBlock" should {
    "initialize with default values" in {
      val playerAmount = 4
      val firstBlock = 0
      val hoverBlock = new HoverBlock(playerAmount, firstBlock)

      hoverBlock.getX() shouldEqual 2
      hoverBlock.getY() shouldEqual 2
      hoverBlock.getCurrentPlayer shouldEqual 0
      hoverBlock.getBlock() shouldEqual Block.createBlock(firstBlock, 0, false)
    }

    "correctly change the player" in {
      val playerAmount = 4
      val firstBlock = 0
      val hoverBlock = new HoverBlock(playerAmount, firstBlock)

      hoverBlock.changePlayer() shouldEqual 1
      hoverBlock.changePlayer() shouldEqual 2
      hoverBlock.changePlayer() shouldEqual 3
      hoverBlock.changePlayer() shouldEqual 0
    }

    "correctly move the block" in {
      val playerAmount = 4
      val firstBlock = 0
      val hoverBlock = new HoverBlock(playerAmount, firstBlock)
      val field = Field(5, 5)

      hoverBlock.move(field, 0) shouldEqual true
      hoverBlock.getX() shouldEqual 2
      hoverBlock.getY() shouldEqual 3

      hoverBlock.move(field, 3) shouldEqual true // Can't move up
      hoverBlock.getX() shouldEqual 1
      hoverBlock.getY() shouldEqual 3
    }

    "correctly rotate the block" in {
      val playerAmount = 4
      val firstBlock = 0
      val hoverBlock = new HoverBlock(playerAmount, firstBlock)
      val field = Field(5, 5)

      hoverBlock.rotate(field) shouldEqual false
      hoverBlock.getBlock() shouldEqual Block.createBlock(firstBlock, 1, false)

      hoverBlock.rotate(field) shouldEqual false
      hoverBlock.getBlock() shouldEqual Block.createBlock(firstBlock, 2, false)
    }

    "correctly mirror the block" in {
      val playerAmount = 4
      val firstBlock = 0
      val hoverBlock = new HoverBlock(playerAmount, firstBlock)
      val field = Field(5, 5)

      hoverBlock.mirror(field) shouldEqual false
      hoverBlock.getBlock() shouldEqual Block.createBlock(firstBlock, 0, true)

      hoverBlock.mirror(field) shouldEqual false
      hoverBlock.getBlock() shouldEqual Block.createBlock(firstBlock, 0, false)
    }

    "correctly place the block on the field" in {
      val playerAmount = 4
      val firstBlock = 0
      val hoverBlock = new HoverBlock(playerAmount, firstBlock)
      val field = Field(5, 5)
      val newBlockType = 1

      val newField = hoverBlock.setzen(field, newBlockType)
      newField.getFieldVector shouldEqual field.placeBlock(Block.createBlock(firstBlock, 0, false), 2, 2, 0).getFieldVector
      hoverBlock.getX() shouldEqual 2
      hoverBlock.getY() shouldEqual 2
      hoverBlock.getCurrentPlayer shouldEqual 0
      hoverBlock.getBlock() shouldEqual Block.createBlock(newBlockType, 0, false)
    }
  }
}