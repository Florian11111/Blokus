import blokus.models.Block
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BlockSpec extends AnyWordSpec with Matchers {

  "Block object" should {

    "create blocks with correct base forms" in {
      for (i <- Block.blockBaseForms.indices) {
        val block = Block.createBlock(i, 0, mirrored = false)
        block.baseForm shouldBe Block.blockBaseForms(i)
      }
    }

    "correctly apply rotation to blocks" in {
      // Testing rotation on a specific block type
      val blockType = 0
      val rotations = Seq(
        // Expected base forms after each rotation (0, 90, 180, 270 degrees)
        Block.blockBaseForms(blockType),
        // Add the expected rotated forms here for each rotation
      )

      for (rotation <- rotations.indices) {
        val block = Block.createBlock(blockType, rotation, mirrored = false)
        block.baseForm shouldBe rotations(rotation)
      }
    }

    "correctly apply mirroring to blocks" in {
      // Testing mirroring on a specific block type
      val blockType = 1
      val mirroredForm = List((0, 0), (1, 0), (0, -1))// Define the expected mirrored form

      val mirroredBlock = Block.createBlock(blockType, 0, mirrored = true)
      mirroredBlock.baseForm shouldBe mirroredForm
    }

    "calculate corners and edges correctly" in {
      // Testing corners and edges calculation on a specific block type
      val blockType = 0
      val block = Block.createBlock(blockType, 0, mirrored = false)

      val expectedCorners = List((-1, 1), (1, 1), (1, -1), (-1, -1))// Define the expected corners
      val expectedEdges = List((0, 1), (1, 0), (-1, 0), (0, -1))// Define the expected edges

      block.corners shouldBe expectedCorners
      block.edges shouldBe expectedEdges
    }

    "eckenUndKanten should return correct corners and edges" in {
      val baseForm = List((0, 0), (1, 0)) // Example base form
      val (corners, edges) = Block.eckenUndKanten(baseForm)

      val expectedCorners = List((-1, -1), (2, -1), (-1, 1), (2, 1))
      val expectedEdges = List((-1, 0), (2, 0), (0, -1), (1, -1), (0, 1), (1, 1))

      corners shouldBe expectedCorners
      edges shouldBe expectedEdges
    }
  }
}
