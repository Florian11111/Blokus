import blokus.models.{Field, HoverBlock, Block}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HoverBlockSpec extends AnyWordSpec with Matchers {

  "A HoverBlock" should {

    val playerAmount = 2
    val firstBlock = 1
    val hoverBlock = new HoverBlock(playerAmount, firstBlock)
    val testField = Field(10, 10)

    "initialize correctly" in {
      hoverBlock.getCurrentPlayer shouldBe 0
      hoverBlock.getCurrentBlock shouldBe firstBlock
      hoverBlock.getX() shouldBe 2
      hoverBlock.getY() shouldBe 2
      hoverBlock.getRotation() shouldBe 0
      hoverBlock.getBlock() should not be empty
    }

    "change the current player correctly" in {
      val newPlayer = hoverBlock.changePlayer()
      newPlayer shouldBe 1
      hoverBlock.getCurrentPlayer shouldBe 1
    }

    "set a new player correctly" in {
      val prevPlayer = hoverBlock.setPlayer(0)
      prevPlayer shouldBe 1
      hoverBlock.getCurrentPlayer shouldBe 0
    }

    "move correctly within the field boundaries" in {
      hoverBlock.move(testField, 0) shouldBe true // Move down
      hoverBlock.getY() shouldBe 3

      hoverBlock.move(testField, 1) shouldBe true // Move right
      hoverBlock.getX() shouldBe 3
    }

    "not move outside the field boundaries" in {
      hoverBlock.currentX = 0
      hoverBlock.currentY = 0
      hoverBlock.move(testField, 2) shouldBe false // Move up
      hoverBlock.move(testField, 3) shouldBe false // Move left
    }

    "rotate the block when possible" in {
      hoverBlock.rotate(testField) shouldBe true
      hoverBlock.getRotation() shouldBe 1
    }

    "not rotate the block when not possible" in {
      // Set up a scenario where rotation is not possible
      hoverBlock.setCurrentBlock(3)
      hoverBlock.currentX = 0
      hoverBlock.currentY = testField.height
      hoverBlock.canRotate(testField) shouldBe false
    }

    "mirror the block when possible" in {
      hoverBlock.setCurrentBlock(1)
      hoverBlock.currentX = 4
      hoverBlock.currentY = 4
      val initialMirroredState = hoverBlock.mirrored
      hoverBlock.mirror(testField) shouldBe true
      hoverBlock.mirrored should not be initialMirroredState
    }

    "not mirror the block when not possible" in {
      hoverBlock.setCurrentBlock(3)
      hoverBlock.currentX = 0
      hoverBlock.currentY = testField.height
      hoverBlock.mirror(testField) shouldBe false
    }

    "place the block correctly on the first place" in {
      hoverBlock.setCurrentBlock(0)
      hoverBlock.currentX = 0
      hoverBlock.currentY = 0
      val newField = hoverBlock.place(testField, 0, firstPlace = true)
      val expectedfield = Vector(
          Vector(0, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
          Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
        )
      newField.getFieldVector shouldBe expectedfield
    }

    "not place the block when it's not a valid placement" in {
      // Set up a scenario where placement is not valid
      // This requires specific knowledge of the field's state and block positioning
      // Example:
      // hoverBlock.currentX = some value
      // hoverBlock.currentY = some value
      // an[Exception] should be thrownBy hoverBlock.place(testField, 2, firstPlace = false)
    }

    "move the block inside the field from the left edge" in {
      hoverBlock.currentX = 0
      hoverBlock.currentY = 10

      hoverBlock.getOutOfCorner(testField.height, testField.width) shouldBe true
      hoverBlock.currentX shouldBe 2
      hoverBlock.currentY shouldBe 10
    }

    "reset its position after placing a block" in {
      hoverBlock.setPlayer(1)
      hoverBlock.setCurrentBlock(0)
      hoverBlock.currentX = 0
      hoverBlock.currentY = 0
      hoverBlock.place(testField, 1, firstPlace = true)
      hoverBlock.getX() shouldBe (testField.width / 2) - 1
      hoverBlock.getY() shouldBe (testField.height / 2) - 1
      hoverBlock.getRotation() shouldBe 0
      hoverBlock.mirrored shouldBe false
      hoverBlock.getCurrentBlock shouldBe 1
    }

    "handle singleton pattern correctly" in {
      val instance1 = HoverBlock.getInstance(playerAmount, firstBlock)
      val instance2 = HoverBlock.getInstance(playerAmount, firstBlock)
      instance1 shouldBe theSameInstanceAs(instance2)
    }
  }
}
