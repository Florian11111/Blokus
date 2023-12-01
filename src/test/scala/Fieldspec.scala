import blokus.models.Field
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "Field" should {

    "calculate width correctly" in {
      val field = Field(5, 4)
      field.width shouldBe 5
    }

    "be initialized with correct dimensions" in {
      val field = Field(5, 4)
      field.width shouldBe 5
      field.height shouldBe 4
      field.getFieldVector shouldBe Vector.fill(4, 5)(-1)
    }

    "validate positions correctly" in {
      val field = Field(5, 5)
      field.isValidPosition(List((0, 0)), 2, 2) shouldBe true
      field.isValidPosition(List((0, 0), (1, 0)), 4, 4) shouldBe false // block goes out of bounds
      field.isValidPosition(List((0, 0), (-1, -1)), 0, 0) shouldBe false // block goes out of bounds
      // ... more cases
    }

    "place block correctly" in {
      val field = Field(5, 5)
      val newField = field.placeBlock(List((0, 0)), 2, 2, 0)
      newField.getFieldVector(2)(2) shouldBe 0
      // ... more cases, including edge cases
    }

    "throw exception for invalid block placement" in {
      val field = Field(5, 5)
      a[IllegalArgumentException] shouldBe thrownBy {
        field.placeBlock(List((0, 0), (1, 0)), 4, 4, 0) // block goes out of bounds
      }
      // ... more cases for invalid placements
    }

    "update field correctly when placing a block" in {
      val fielde = Field(5, 5)
      val newFielde = fielde.placeBlock(List((0, 0), (1, 0)), 2, 2, 0)

      newFielde.getFieldVector shouldBe Vector(
        Vector(-1, -1, -1, -1, -1),
        Vector(-1, -1, -1, -1, -1),
        Vector(-1, -1,  0,  0, -1),
        Vector(-1, -1,  0,  0, -1),
        Vector(-1, -1, -1, -1, -1)
      )
    }

    "calculate width based on fieldVector" in {
      val field = Field(5, 4)
      field.width shouldBe 5

      val emptyField = Field(0, 0)
      emptyField.width shouldBe 0
    }
  }
  "Field object" should {
    "create a new Field instance with the given dimensions using apply method" in {
      val field = Field.apply(5, 5)
      field.width shouldBe 5
      field.height shouldBe 5
      // Additional assertions as needed
    }
  }

  "creating a copy" should {
    "produce an identical copy of the field" in {
      val field = Field.getInstance(4, 5)
      val copiedField = field.copy()

      assert(field.width == copiedField.width)
      assert(field.height == copiedField.height)
      assert(field.getFieldVector == copiedField.getFieldVector)
    }
  }
}