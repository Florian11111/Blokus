package blokus.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "Field" should {

    val testField = new Field(5, 5)

    "convert a row of integers to characters correctly" in {
      val row = Array(0, 1, 2, 1, 0)
      testField.rowToString(row) shouldBe "# + ? + # "
    }

    "convert the array to a string correctly" in {
    val initialString = ("+ + + + + " + "\n") * (testField.height - 1) + "+ + + + + "
    testField.createFieldString shouldBe initialString
    }


    "change the field correctly" in {
      testField.changeField(2, 2, 0)
      val changedArray = testField.getFieldArray
      changedArray(2)(2) shouldBe 0
    }

    "throw an assertion error for invalid x and y coordinates" in {
      assertThrows[AssertionError] {
        testField.changeField(6, 6, 0)
      }
    }
  }
}
