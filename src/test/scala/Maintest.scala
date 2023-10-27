import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class Maintest extends AnyWordSpec with Matchers {

  "Main" should {

    "convert a row of integers to characters correctly" in {
      val row = Array(0, 1, 2, 1)
      Main.rowToString(row) shouldBe "# + ? + "
    }

    "convert the array to a string correctly" in {
      val field = Array(
        Array(0, 1),
        Array(1, 2)
      )
      Main.createFieldString(field) shouldBe "# + \n+ ? "
    }

    "change the field correctly" in {
      val field = Array.ofDim[Int](Main.height, Main.width).map(_ => Array.fill(Main.width)(1))
      val changedField = Main.changeField(field, 5, 4, 0)
      changedField(5)(4) shouldBe 0
    }

    "throw an assertion error for invalid x and y coordinates" in {
      val field = Array.ofDim[Int](Main.height, Main.width).map(_ => Array.fill(Main.width)(1))
      assertThrows[AssertionError] {
        Main.changeField(field, Main.width, Main.height, 0)
      }
    }
  }
}
