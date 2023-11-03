package blokus.models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BlockTypeTest extends AnyFlatSpec with Matchers {

  "createBlock" should "return original block if no rotation or mirroring is done" in {
    val block = BlockType.createBlock(BlockType.block1, 0, false)
    block should contain theSameElementsAs BlockType.block1.baseForm
  }

  it should "mirror the block correctly" in {
    val mirroredBlock = BlockType.createBlock(BlockType.block2, 0, true)
    mirroredBlock should contain theSameElementsAs List((0, 0), (1, 0))
  }

  it should "rotate the block 90 degrees correctly" in {
    val rotatedBlock = BlockType.createBlock(BlockType.block4, 1, false)
    rotatedBlock should contain theSameElementsAs List((0, 0), (-1, 0), (0, 1))
  }

  it should "rotate the block 180 degrees correctly" in {
    val rotatedBlock = BlockType.createBlock(BlockType.block4, 2, false)
    rotatedBlock should contain theSameElementsAs List((0, 0), (-1, 0), (0, -1))
  }

  it should "rotate the block 270 degrees correctly" in {
    val rotatedBlock = BlockType.createBlock(BlockType.block4, 3, false)
    rotatedBlock should contain theSameElementsAs List((0, 0), (1, 0), (0, -1))
  }

  it should "rotate 90 degrees and mirror the block correctly" in {
    val rotatedAndMirroredBlock = BlockType.createBlock(BlockType.block4, 1, true)
    rotatedAndMirroredBlock should contain theSameElementsAs List((0, 0), (1, 0), (0, 1))
  }
}
