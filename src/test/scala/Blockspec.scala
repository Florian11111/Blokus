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
      // ... continue for all base forms
    }

    "createBlock" should {

      "create correct block for each type without rotation or mirroring" in {
        Block.createBlock(0, 0, false) shouldBe Block.baseForm0
        Block.createBlock(1, 0, false) shouldBe Block.baseForm1
        Block.createBlock(2, 0, false) shouldBe Block.baseForm2
        // ... continue for all block types
      }

      "correctly apply rotation for a single block type" in {
        // Example with Block.baseForm1
        Block.createBlock(1, 1, false) shouldBe List((0, 0), (0, 1))
        Block.createBlock(1, 2, false) shouldBe List((0, 0), (-1, 0))
        Block.createBlock(1, 3, false) shouldBe List((0, 0), (0, -1))
        // ... test rotations for other block types
      }

      "correctly apply mirroring for a single block type" in {
        // Example with Block.baseForm1
        Block.createBlock(1, 0, true) shouldBe List((0, 0), (1, 0)).map { case (x, y) => (x, -y) }
        // ... test mirroring for other block types
      }

      "apply both rotation and mirroring correctly for a single block type" in {
        // Example with Block.baseForm1
        Block.createBlock(1, 1, true) shouldBe List((0, 0), (0, -1)).map { case (x, y) => (x, -y) }
        // ... test combined cases for other block types
      }
    }
  }
}
