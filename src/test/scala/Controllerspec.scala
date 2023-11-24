import blokus.controller.{Controller, ControllerEvent}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" should {

    val playerAmount = 4
    val firstBlockType = 1
    val width = 10
    val height = 10
    val controller = new Controller(playerAmount, firstBlockType, width, height)

    "initialize correctly" in {
      controller.getField().size shouldBe height
      controller.getField().head.size shouldBe width
      controller.getBlock() shouldBe controller.hoverBlock.getBlock()
      controller.getCurrentPlayer() shouldBe 0
    }

    "move the hover block correctly" in {
      controller.move(0) shouldBe true // Assuming this is a valid move
      // Test all move directions and invalid move
      // Optionally check if observers are notified on a successful move
    }

    "rotate the hover block correctly" in {
      controller.rotate() shouldBe true // Assuming rotation is valid
      controller.getRotation() shouldBe 1
      // Optionally check if observers are notified on a successful rotation
    }

    "mirror the hover block correctly" in {
      controller.mirror() shouldBe true // Assuming mirroring is valid
      // Test mirroring and check if the mirrored state changes
      // Optionally check if observers are notified on a successful mirroring
    }

    "change the player correctly" in {
      val nextPlayer = controller.changePlayer(1)
      controller.getCurrentPlayer() shouldBe 1
      // Optionally check if observers are notified on player change
    }

    "set a new block type correctly" in {
      val newBlockType = 2
      controller.setzen(newBlockType)
      controller.getBlock() shouldBe controller.hoverBlock.getBlock()
      // Optionally check if observers are notified after setting a new block
    }
  }
}
