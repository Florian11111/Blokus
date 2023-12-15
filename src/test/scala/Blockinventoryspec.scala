import blokus.models.BlockInventory
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter

class BlockInventorySpec extends AnyWordSpec with Matchers with BeforeAndAfter {

  var blockInventory: BlockInventory = _

  before {
    blockInventory = new BlockInventory(playerAmount = 2)
  }

  "A BlockInventory" should {

    "initialize correctly" in {
      blockInventory.getBlocks(0) should have size 21
      blockInventory.getBlocks(1) should have size 21
      blockInventory.firstBlock(0) shouldBe true
      blockInventory.firstBlock(1) shouldBe true
    }

    "give a random block correctly" in {
      val randomBlock = blockInventory.getRandomBlock(0)
      randomBlock shouldBe defined
      randomBlock.get should (be >= 0 and be < 21)
    }

    "indicate if a block is available" in {
      blockInventory.isAvailable(0, 0) shouldBe true
      blockInventory.useBlock(0, 0)
      blockInventory.isAvailable(0, 0) shouldBe false
    }

    "decrease block count correctly when used" in {
      val initialBlocks = blockInventory.getBlocks(0)
      blockInventory.useBlock(0, 0)
      blockInventory.getBlocks(0) should not equal initialBlocks
    }

    "throw an exception when using an unavailable block" in {
      blockInventory.useBlock(0, 0)
      an[RuntimeException] should be thrownBy blockInventory.useBlock(0, 0)
    }

    "handle first block correctly" in {
      blockInventory.firstBlock(0) shouldBe true
      blockInventory.useBlock(0, 0)
      blockInventory.firstBlock(0) shouldBe false
    }

    "create a deep copy correctly" in {
      val copy = blockInventory.deepCopy
      copy should not be theSameInstanceAs(blockInventory)
      copy.getBlocks(0) shouldBe blockInventory.getBlocks(0)
    }

    "handle invalid player numbers gracefully" in {
      an[IllegalArgumentException] should be thrownBy blockInventory.useBlock(-1, 0)
      an[IllegalArgumentException] should be thrownBy blockInventory.getRandomBlock(-1)
      an[IllegalArgumentException] should be thrownBy blockInventory.getBlocks(-1)
    }

    "handle empty inventory" in {
      blockInventory.inventories = Array.fill(2 + 1, 0)(0)
      an[RuntimeException] should be thrownBy blockInventory.getRandomBlock(0)
    }
  }
}
