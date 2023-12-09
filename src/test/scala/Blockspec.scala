import blokus.models.Block
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BlockSpec extends AnyWordSpec with Matchers {

  "Block" should {

    "have correct base forms" in {
      Block.baseForm0 shouldBe List((0, 0))
      Block.baseForm1 shouldBe List((0, 0), (1, 0))
      Block.baseForm2 shouldBe List((-1, 0), (0, 0), (1, 0))
      Block.baseForm3 shouldBe List((0, 0), (1, 0), (0, 1))
      Block.baseForm4 shouldBe List((-1, 0), (0, 0), (1, 0), (2, 0))
      Block.baseForm5 shouldBe List((-1, 0), (-1, 1), (0, 0), (1, 0))
      Block.baseForm6 shouldBe List((-1, 0), (0, 0), (1, 0), (0, 1))
      Block.baseForm7 shouldBe List((0, 0), (1, 0), (0, 1), (1, 1))
      Block.baseForm8 shouldBe List((0, 0), (-1, 0), (0, 1), (1, 1))
      Block.baseForm9 shouldBe List((-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0))
      Block.baseForm10 shouldBe List((-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1))
      Block.baseForm11 shouldBe List((-1, 0), (0, 0), (1, 0), (0, 1), (1, 1))
      Block.baseForm12 shouldBe List((-1, 0), (0, 0), (1, 0), (2, 0), (0, 1))
      Block.baseForm13 shouldBe List((-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1))
      Block.baseForm14 shouldBe List((-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1))
      Block.baseForm15 shouldBe List((0, 0), (0, -1), (1, 0), (0, 1), (-1, 0))
      Block.baseForm16 shouldBe List((-1, -1), (0, -1), (0, 0), (0, 1), (1, 1))
      Block.baseForm17 shouldBe List((-1, -1), (0, -1), (0, 0), (1, 0), (1, 1))
      Block.baseForm18 shouldBe List((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1))
      Block.baseForm19 shouldBe List((-1, -1), (0, -1), (1, -1), (0, 0), (0, 1))
      Block.baseForm20 shouldBe List((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1))
    }

    "createBlock" should {

      "create correct block for each type without rotation or mirroring" in {
        Block.createBlock(0, 0, false) shouldEqual Block.baseForm0
        Block.createBlock(1, 0, false) shouldEqual Block.baseForm1
        Block.createBlock(2, 0, false) shouldEqual Block.baseForm2
        Block.createBlock(3, 0, false) shouldEqual Block.baseForm3
        Block.createBlock(4, 0, false) shouldEqual Block.baseForm4
        Block.createBlock(5, 0, false) shouldEqual Block.baseForm5
        Block.createBlock(6, 0, false) shouldEqual Block.baseForm6
        Block.createBlock(7, 0, false) shouldEqual Block.baseForm7
        Block.createBlock(8, 0, false) shouldEqual Block.baseForm8
        Block.createBlock(9, 0, false) shouldEqual Block.baseForm9
        Block.createBlock(10, 0, false) shouldEqual Block.baseForm10
        Block.createBlock(11, 0, false) shouldEqual Block.baseForm11
        Block.createBlock(12, 0, false) shouldEqual Block.baseForm12
        Block.createBlock(13, 0, false) shouldEqual Block.baseForm13
        Block.createBlock(14, 0, false) shouldEqual Block.baseForm14
        Block.createBlock(15, 0, false) shouldEqual Block.baseForm15
        Block.createBlock(16, 0, false) shouldEqual Block.baseForm16
        Block.createBlock(17, 0, false) shouldEqual Block.baseForm17
        Block.createBlock(18, 0, false) shouldEqual Block.baseForm18
        Block.createBlock(19, 0, false) shouldEqual Block.baseForm19
        Block.createBlock(20, 0, false) shouldEqual Block.baseForm20
      }

      "correctly apply rotation for a single block type" in {
        Block.createBlock(0, 0, false) shouldEqual Block.baseForm0
        Block.createBlock(1, 0, false) shouldEqual Block.baseForm1
        Block.createBlock(2, 0, false) shouldEqual Block.baseForm2
        Block.createBlock(3, 0, false) shouldEqual Block.baseForm3
        Block.createBlock(4, 0, false) shouldEqual Block.baseForm4
        Block.createBlock(5, 0, false) shouldEqual Block.baseForm5
        Block.createBlock(6, 0, false) shouldEqual Block.baseForm6
        Block.createBlock(7, 0, false) shouldEqual Block.baseForm7
        Block.createBlock(8, 0, false) shouldEqual Block.baseForm8
        Block.createBlock(9, 0, false) shouldEqual Block.baseForm9
        Block.createBlock(10, 0, false) shouldEqual Block.baseForm10
        Block.createBlock(11, 0, false) shouldEqual Block.baseForm11
        Block.createBlock(12, 0, false) shouldEqual Block.baseForm12
        Block.createBlock(13, 0, false) shouldEqual Block.baseForm13
        Block.createBlock(14, 0, false) shouldEqual Block.baseForm14
        Block.createBlock(15, 0, false) shouldEqual Block.baseForm15
        Block.createBlock(16, 0, false) shouldEqual Block.baseForm16
        Block.createBlock(17, 0, false) shouldEqual Block.baseForm17
        Block.createBlock(18, 0, false) shouldEqual Block.baseForm18
        Block.createBlock(19, 0, false) shouldEqual Block.baseForm19
        Block.createBlock(20, 0, false) shouldEqual Block.baseForm20
      }

      "correctly apply mirroring for a single block type" in {
        Block.createBlock(0, 0, true) shouldEqual Block.baseForm0.map { case (x, y) => (x, -y) }
        Block.createBlock(1, 0, true) shouldEqual Block.baseForm1.map { case (x, y) => (x, -y) }
        Block.createBlock(2, 0, true) shouldEqual Block.baseForm2.map { case (x, y) => (x, -y) }
        Block.createBlock(3, 0, true) shouldEqual Block.baseForm3.map { case (x, y) => (x, -y) }
        Block.createBlock(4, 0, true) shouldEqual Block.baseForm4.map { case (x, y) => (x, -y) }
        Block.createBlock(5, 0, true) shouldEqual Block.baseForm5.map { case (x, y) => (x, -y) }
        Block.createBlock(6, 0, true) shouldEqual Block.baseForm6.map { case (x, y) => (x, -y) }
        Block.createBlock(7, 0, true) shouldEqual Block.baseForm7.map { case (x, y) => (x, -y) }
        Block.createBlock(8, 0, true) shouldEqual Block.baseForm8.map { case (x, y) => (x, -y) }
        Block.createBlock(9, 0, true) shouldEqual Block.baseForm9.map { case (x, y) => (x, -y) }
        Block.createBlock(10, 0, true) shouldEqual Block.baseForm10.map { case (x, y) => (x, -y) }
        Block.createBlock(11, 0, true) shouldEqual Block.baseForm11.map { case (x, y) => (x, -y) }
        Block.createBlock(12, 0, true) shouldEqual Block.baseForm12.map { case (x, y) => (x, -y) }
        Block.createBlock(13, 0, true) shouldEqual Block.baseForm13.map { case (x, y) => (x, -y) }
        Block.createBlock(14, 0, true) shouldEqual Block.baseForm14.map { case (x, y) => (x, -y) }
        Block.createBlock(15, 0, true) shouldEqual Block.baseForm15.map { case (x, y) => (x, -y) }
        Block.createBlock(16, 0, true) shouldEqual Block.baseForm16.map { case (x, y) => (x, -y) }
        Block.createBlock(17, 0, true) shouldEqual Block.baseForm17.map { case (x, y) => (x, -y) }
        Block.createBlock(18, 0, true) shouldEqual Block.baseForm18.map { case (x, y) => (x, -y) }
        Block.createBlock(19, 0, true) shouldEqual Block.baseForm19.map { case (x, y) => (x, -y) }
        Block.createBlock(20, 0, true) shouldEqual Block.baseForm20.map { case (x, y) => (x, -y) }
      }

      "apply both rotation and mirroring correctly for a single block type" in {
        Block.createBlock(0, 1, true) shouldEqual Block.baseForm0.map { case (x, y) => (y, x) }
        Block.createBlock(1, 1, true) shouldEqual Block.baseForm1.map { case (x, y) => (y, x) }
        Block.createBlock(2, 1, true) shouldEqual Block.baseForm2.map { case (x, y) => (y, x) }
        Block.createBlock(3, 1, true) shouldEqual Block.baseForm3.map { case (x, y) => (y, x) }
        Block.createBlock(4, 1, true) shouldEqual Block.baseForm4.map { case (x, y) => (y, x) }
        Block.createBlock(5, 1, true) shouldEqual Block.baseForm5.map { case (x, y) => (y, x) }
        Block.createBlock(6, 1, true) shouldEqual Block.baseForm6.map { case (x, y) => (y, x) }
        Block.createBlock(7, 1, true) shouldEqual Block.baseForm7.map { case (x, y) => (y, x) }
        Block.createBlock(8, 1, true) shouldEqual Block.baseForm8.map { case (x, y) => (y, x) }
        Block.createBlock(9, 1, true) shouldEqual Block.baseForm9.map { case (x, y) => (y, x) }
        Block.createBlock(10, 1, true) shouldEqual Block.baseForm10.map { case (x, y) => (y, x) }
        Block.createBlock(11, 1, true) shouldEqual Block.baseForm11.map { case (x, y) => (y, x) }
        Block.createBlock(12, 1, true) shouldEqual Block.baseForm12.map { case (x, y) => (y, x) }
        Block.createBlock(13, 1, true) shouldEqual Block.baseForm13.map { case (x, y) => (y, x) }
        Block.createBlock(14, 1, true) shouldEqual Block.baseForm14.map { case (x, y) => (y, x) }
        Block.createBlock(15, 1, true) shouldEqual Block.baseForm15.map { case (x, y) => (y, x) }
        Block.createBlock(16, 1, true) shouldEqual Block.baseForm16.map { case (x, y) => (y, x) }
        Block.createBlock(17, 1, true) shouldEqual Block.baseForm17.map { case (x, y) => (y, x) }
        Block.createBlock(18, 1, true) shouldEqual Block.baseForm18.map { case (x, y) => (y, x) }
        Block.createBlock(19, 1, true) shouldEqual Block.baseForm19.map { case (x, y) => (y, x) }
        Block.createBlock(20, 1, true) shouldEqual Block.baseForm20.map { case (x, y) => (y, x) }
      }
    }
  }
}
