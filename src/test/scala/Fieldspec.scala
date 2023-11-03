package blokus.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "Field" should {

    "be initialized with the correct dimensions" in {
      val testField = Field(5, 5)
      testField.width shouldBe 5
      testField.height shouldBe 5
    }

    "initialize all values to 1" in {
      val testField = Field(5, 5)
      for {
        row <- 0 until 5
        col <- 0 until 5
      } testField.getFieldVector(row)(col) shouldBe 1
    }

    "convert a row of integers to characters correctly" in {
      val testField = Field(5, 5)
      testField.rowToString(Vector(1, 0, 1, 2, 1)) shouldBe "+ # + | + "
    }

    "convert the field vector to a string correctly" in {
      val testField = Field(2, 2)
      testField.createFieldString shouldBe "+ + \n+ + "
    }

    "change the field correctly" in {
      val testField = Field(5, 5)
      val changedField = testField.changeField(2, 2, 0)
      changedField.getFieldVector(2)(2) shouldBe 0
    }

    "handle an invalid changeField request by throwing an assertion error" in {
      val testField = Field(5, 5)
      assertThrows[AssertionError] {
        testField.changeField(6, 6, 0)
      }
    }

    "return the correct width even if the field vector is empty" in {
      val emptyField = new Field(Vector.empty)
      emptyField.width shouldBe 0
    }
  }
}
