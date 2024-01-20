package de.htwg.se.blokus.models.FieldImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock
import de.htwg.se.blokus.models.fieldImpl.Field

class FieldSpec extends AnyWordSpec with Matchers {

  "A Field" when {
    "created" should {
      "initialize the width and height correctly" in {
        val width = 5
        val height = 5

        val field = Field(width, height)

        field.width shouldEqual width
        field.height shouldEqual height
      }

      "initialize the fieldVector correctly with -1 values" in {
        val width = 5
        val height = 5

        val field = Field(width, height)

        field.getFieldVector shouldEqual Vector.fill(height, width)(-1)
      }
    }

    "setFieldVector" should {
      "set the new field vector correctly" in {
        val width = 5
        val height = 5

        var field = Field(width, height)
        val field6 = Field(6, 6)
        field = field.setFieldVector(Vector(Vector(-1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1)))

        field.getFieldVector shouldBe field6.getFieldVector
      }
    }

    "isCorner" should {
      "return true for the corner coordinates" in {
        val field = Field(5, 5)

        field.isCorner(0, 0) shouldEqual true
        field.isCorner(4, 0) shouldEqual true
        field.isCorner(0, 4) shouldEqual true
        field.isCorner(4, 4) shouldEqual true
      }

      "return false for non-corner coordinates" in {
        val field = Field(5, 5)

        field.isCorner(2, 2) shouldEqual false
        field.isCorner(3, 1) shouldEqual false
        field.isCorner(0, 1) shouldEqual false
        field.isCorner(3, 4) shouldEqual false
      }
    }

    "getBlockAmount" should {
      "return the correct number of blocks of a given type" in {
        var field = Field(5, 5)

        val hoverBlock = HoverBlock(0, 0, 0, 0, 0, 0, false)
        val hoverBlock3 = HoverBlock(4, 4, 1, 0, 0, 0, false)

        field = field.placeBlock(hoverBlock, firstPlace = true)
        field = field.placeBlock(hoverBlock3, firstPlace = true)

        field.getBlockAmount(0) shouldEqual 2
      }

      "return 0 if no blocks of the given type are on the field" in {
        val field = Field(5, 5)

        field.getBlockAmount(1) shouldEqual 0
      }
    }

    "isInBounds" should {
      "return true for coordinates within the field" in {
        val field = Field(5, 5)

        field.isInBounds(2, 2) shouldEqual true
      }

      "return false for coordinates outside the field" in {
        val field = Field(5, 5)

        field.isInBounds(-1, 2) shouldEqual false
        field.isInBounds(2, -1) shouldEqual false
        field.isInBounds(5, 2) shouldEqual false
        field.isInBounds(2, 5) shouldEqual false
      }
    }

    "isInsideField" should {
      "return true for a valid hoverBlock placement" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(2, 2, 0, 0, 0, 0, false)

        field.isInsideField(hoverBlock) shouldEqual true
      }

      "return false for an invalid hoverBlock placement" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(5, 2, 0, 0, 0, 0, false)

        field.isInsideField(hoverBlock) shouldEqual false
      }
    }

    "cornerCheck" should {
      "return true for a valid hoverBlock placement in a corner" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(0, 4, 0, 0, 0, 0, false)

        field.isInCorner(hoverBlock) shouldEqual true
      }

      "return false for an invalid hoverBlock placement" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(4, 2, 0, 0, 0, 0, false)

        field.isInCorner(hoverBlock) shouldEqual false
      }
    }

    "isNoBlocksOnTop" should {
      "return true for a valid hoverBlock placement with no blocks on top" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(2, 2, 0, 0, 0, 0, false)

        field.isNoBlocksOnTop(hoverBlock) shouldEqual true
      }

      "return false for an invalid hoverBlock placement with blocks on top" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(2, 2, 0, 0, 0, 0, false)

        val hoverBlock2 = HoverBlock(2, 2, 0, 0, 0, 0, false)

        val exception = intercept[IllegalArgumentException] {
            field.placeBlock(hoverBlock, firstPlace = true)
        }
        exception shouldBe an[IllegalArgumentException]
      }
    }

    "isInCorner" should {
      "return true for a valid hoverBlock placement in a corner" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(4, 4, 0, 0, 0, 0, false)

        field.isInCorner(hoverBlock) shouldEqual true
      }

      "return false for an invalid hoverBlock placement" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(4, 2, 0, 0, 0, 0, false)

        field.isInCorner(hoverBlock) shouldEqual false
      }
    }

    "isGameRuleConfirm" should {
      "return true for a valid hoverBlock placement following game rules" in {
        var field = Field(5, 5)
        val hoverBlock = HoverBlock(0, 0, 0, 0, 0, 0, false)
        val hoverBlock2 = HoverBlock(1, 1, 0, 0, 1, 0, false)

        field = field.placeBlock(hoverBlock, true)

        field.isGameRuleConfirm(hoverBlock2) shouldEqual true
      }

      "return an IllegalArgumentException for an invalid hoverBlock placement not following game rules" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(0, 0, 0, 3, 0, 0, false)
        field.placeBlock(hoverBlock, firstPlace = true)

        val hoverBlock2 = HoverBlock(3, 3, 0, 1, 0, 0, false)

        field.isGameRuleConfirm(hoverBlock2) shouldEqual false
      }
    }

    "placeBlock" should {
      "return a new field with the hoverBlock placed on it" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(0, 0, 0, 3, 0, 0, false)

        val newField = field.placeBlock(hoverBlock, firstPlace = true)

        newField.getFieldVector(0)(0) shouldEqual hoverBlock.getPlayer
      }

      "throw an exception for an invalid hoverBlock placement" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(4, 2, 0, 0, 0, 0, false)

        an[IllegalArgumentException] should be thrownBy field.placeBlock(hoverBlock, firstPlace = true)
      }
    }

    "copy" should {
      "return a deep copy of the field" in {
        val field = Field(5, 5)
        val hoverBlock = HoverBlock(4, 4, 0, 0, 0, 0, false)
        field.placeBlock(hoverBlock, firstPlace = true)

        val copiedField = field.copy()

        copiedField.getFieldVector shouldEqual field.getFieldVector
      }
    }

    "getInstance" should {
      "return the same instance when getInstance is called multiple times" in {
      val width = 10
      val height = 10

      val field1 = Field.getInstance(width, height)
      val field2 = Field.getInstance(width, height)

      field1 should be theSameInstanceAs field2
      }
    }
  }
}