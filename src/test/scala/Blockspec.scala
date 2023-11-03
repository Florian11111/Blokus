package blokus.models
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BlockTypeSpec extends AnyFlatSpec with Matchers {

  "BlockType" should "correctly rotate the block 90 degrees" in {
    val result = BlockType.createBlock(BlockType.blockA, 1, false)
    result should contain theSameElementsAs List((0, 0), (-1, 0), (1, 0), (-1, 1))
  }

  it should "correctly rotate the block 180 degrees" in {
    val result = BlockType.createBlock(BlockType.blockA, 2, false)
    result should contain theSameElementsAs List((0, 0), (0, 1), (0, -1), (-1, -1))
  }

  it should "correctly rotate the block 270 degrees" in {
    val result = BlockType.createBlock(BlockType.blockA, 3, false)
    result should contain theSameElementsAs List((0, 0), (1, 0), (-1, 0), (-1, -1))
  }

  it should "correctly rotate the block 360 degrees (which should be same as original)" in {
    val result = BlockType.createBlock(BlockType.blockA, 4, false)
    result should contain theSameElementsAs BlockType.blockA.baseForm
  }

  it should "correctly mirror the block" in {
    val result = BlockType.createBlock(BlockType.blockA, 0, true)
    result should contain theSameElementsAs List((0, 0), (0, 1), (0, -1), (1, -1))
  }

  it should "rotate and then mirror the block" in {
    val result = BlockType.createBlock(BlockType.blockA, 1, true)
    result should contain theSameElementsAs List((0, 0), (-1, 0), (1, 0), (1, -1))
  }

  "BlockB" should "be correctly represented in its base form" in {
    BlockType.blockB.baseForm should contain theSameElementsAs List((0, 0), (0, -1), (1, 0), (1, -1))
  }
}

