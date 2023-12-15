import blokus.models.Field
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "A Field" should {

    "be correctly initialized" in {
      val field = Field(5, 5)
      field.width shouldBe 5
      field.height shouldBe 5
      field.getFieldVector.foreach(row => row.foreach(cell => cell shouldBe -1))
    }

    "validate positions correctly" when {
      "given a valid position" in {
        val field = Field(5, 5)
        field.isValidPosition(List((0, 0), (1, 0)), 0, 0) shouldBe true
      }
      "given an invalid position" in {
        val field = Field(5, 5)
        field.isValidPosition(List((0, 0), (5, 0)), 0, 0) shouldBe false
      }
    }

    "identify corners correctly" in {
      val field = Field(5, 5)
      field.isCorner(0, 0) shouldBe true
      field.isCorner(4, 0) shouldBe true
      field.isCorner(0, 4) shouldBe true
      field.isCorner(4, 4) shouldBe true
      field.isCorner(1, 1) shouldBe false
    }

    "handle first placement logic" in {
      val field = Field(5, 5)
      field.isLogicFirstPlace(List((0, 0)), 0, 0) shouldBe true
      field.isLogicFirstPlace(List((1, 1)), 0, 0) shouldBe false
    }

    "handle regular placement logic" in {
      val field = Field(5, 5).placeBlock(List((0, 0)), List((1, 1)), List((1,0), (0,1)), 0, 0, 1, firstPlace = true)
      field.isLogicPlace(List((0, 0)), List((1, 1), (-1, -1)), List((1,0), (0,1), (-1, 0), (0, -1)), 1, 1, 1) shouldBe true
      field.isLogicPlace(List((0, 0)), List((1, 1)), List((1,0), (0,1)), 2, 2, 1) shouldBe false
    }

    "place a block correctly" in {
      val field = Field(5, 5)
      val newField = field.placeBlock(List((0, 0)), List((1, 1)), List(), 0, 0, 1, firstPlace = true)
      newField.getFieldVector.head.head shouldBe 1
    }

    "throw an exception for invalid block placement" in {
      val field = Field(5, 5)
      an[IllegalArgumentException] should be thrownBy {
        field.placeBlock(List((5, 0)), List((1, 1)), List(), 0, 0, 1, firstPlace = true)
      }
    }

    "copy itself correctly" in {
      val field = Field(5, 5)
      val copiedField = field.copy()
      copiedField should not be theSameInstanceAs(field)
      copiedField.getFieldVector shouldBe field.getFieldVector
    }

    "handle singleton pattern correctly" in {
      val field1 = Field.getInstance(5, 5)
      val field2 = Field.getInstance(5, 5)
      field1 shouldBe theSameInstanceAs(field2)

      val field3 = Field(6, 6)
      field3 should not be theSameInstanceAs(field1)
    }
  }
}
