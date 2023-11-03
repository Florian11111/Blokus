package blokus.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "Field" should {

<<<<<<< Updated upstream
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
      testField.rowToString(Vector(1, 0, 1, 2, 1)) shouldBe "+ # + ? + "
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
=======
    "initialize with the correct dimensions" in {
      val testField = new Field(5, 5)
      testField.getFieldVector.length shouldBe 5
      testField.getFieldVector(0).length shouldBe 5
    }

    "convert a row of integers to characters correctly" in {
      val testField = new Field(5, 5)
      val row = Vector(0, 1, 2, 1, 0)
      testField.rowToString(row) shouldBe "# + ? + # "
    }

    "convert the vector to a string correctly" in {
      val testField = new Field(5, 5)
      val initialString = ("+ + + + + " + "\n") * 4 + "+ + + + + "
      testField.createFieldString shouldBe initialString
    }

    "change the field correctly" in {
      val testField = new Field(5, 5)
      testField.changeField(2, 2, 0)
      val changedVector = testField.getFieldVector
      changedVector(2)(2) shouldBe 0
    }

    "throw an assertion error for invalid x and y coordinates" in {
        val testField = new Field(5, 5)
        assertThrows[AssertionError] {
>>>>>>> Stashed changes
        testField.changeField(6, 6, 0)
      }
    }

<<<<<<< Updated upstream
    "return the correct width even if the field vector is empty" in {
      val emptyField = new Field(Vector.empty)
      emptyField.width shouldBe 0
=======
    "initialize with all values set to 1" in {
      val testField = new Field(5, 5)
      for {
        row <- testField.getFieldVector
        value <- row
      } value shouldBe 1
    }

    "return the correct row when accessing a specific index" in {
      val testField = new Field(5, 5)
      val row = testField.getFieldVector(2)
      row.length shouldBe 5
      row.forall(_ == 1) shouldBe true
    }

    "update the field correctly using fieldVector.updated" in {
      val testField = new Field(5, 5)
      testField.changeField(2, 2, 0)
      testField.getFieldVector(2)(2) shouldBe 0
>>>>>>> Stashed changes
    }
  }
}
