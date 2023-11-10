package blokus.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "A Field" should {
    "initialize with the correct dimensions" in {
      val field = Field(3, 4)
      field.width should be(3)
      field.height should be(4)
    }

    "correctly change the field contents" in {
      val field = Field(3, 3)
      val updatedField = field.changeField(1, 1, 2)
      updatedField.getFieldVector shouldEqual Vector(
        Vector(1, 1, 1),
        Vector(1, 2, 1),
        Vector(1, 1, 1)
      )
    }

    "correctly place a block on the field" in {
      val field = Field(4, 4)
      val block = List((0, 0), (0, 1), (1, 0))
      val updatedField = field.placeBlock(block, 1, 1, 2)
      updatedField.getFieldVector shouldEqual Vector(
        Vector(1, 1, 1, 1),
        Vector(1, 2, 2, 1),
        Vector(1, 2, 1, 1),
        Vector(1, 1, 1, 1)
      )
    }

    "throw an exception when changing or placing a block at an invalid position" in {
      val field = Field(3, 3)
      val invalidChange = intercept[IllegalArgumentException] {
        field.changeField(4, 2, 2)
      }
      invalidChange.getMessage should include("Ungültige Position für Änderung des Felds.")

      val block = List((0, 0), (0, 1), (1, 0), (3, 3))
      val invalidPlacement = intercept[IllegalArgumentException] {
        field.placeBlock(block, 0, 0, 2)
      }
      invalidPlacement.getMessage should include("Ungültige Position für Platzierung des Blocks.")
    }

    "correctly convert the field to a string" in {
      val field = Field(2, 2)
      val fieldString = field.createFieldString
      fieldString shouldEqual "+ + \n+ + \n"
    }
  }
}
