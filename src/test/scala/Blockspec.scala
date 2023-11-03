package blokus.models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import javax.swing.text.html.BlockView

class BlockTypeTest extends AnyFlatSpec with Matchers {

  "createBlock" should "return original block if no rotation or mirroring is done" in {
    val block = Block.createBlock(Block.block1, 0, false)
    block should contain theSameElementsAs Block.block1.baseForm
  }

  it should "mirror the block correctly" in {
    val mirroredBlock = Block.createBlock(Block.block2, 0, true)
    mirroredBlock should contain theSameElementsAs List((0, 0), (1, 0))
  }

  it should "rotate the block 90 degrees correctly" in {
    val rotatedBlock = Block.createBlock(Block.block4, 1, false)
    rotatedBlock should contain theSameElementsAs List((0, 0), (-1, 0), (0, 1))
  }

  it should "rotate the block 180 degrees correctly" in {
    val rotatedBlock = Block.createBlock(Block.block4, 2, false)
    rotatedBlock should contain theSameElementsAs List((0, 0), (-1, 0), (0, -1))
  }

  it should "rotate the block 270 degrees correctly" in {
    val rotatedBlock = Block.createBlock(Block.block4, 3, false)
    rotatedBlock should contain theSameElementsAs List((0, 0), (1, 0), (0, -1))
  }

  it should "rotate 90 degrees and mirror the block correctly" in {
    val rotatedAndMirroredBlock = Block.createBlock(Block.block4, 1, true)
    rotatedAndMirroredBlock should contain theSameElementsAs List((0, 0), (1, 0), (0, 1))
  }
}
