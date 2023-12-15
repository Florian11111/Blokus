import blokus.controller.{Controller, ControllerEvent}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import blokus.models.Field

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
      controller.getHeight() shouldBe 10
      controller.getWidth() shouldBe 10
    }

    "detect too invalid player number" in {
      an[AssertionError] should be thrownBy new Controller(0, 0, 10, 10)
      an[AssertionError] should be thrownBy new Controller(5, 0, 10, 10)
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
      controller.placeBlock(newBlockType)
      controller.getBlock() shouldBe controller.hoverBlock.getBlock()
      // Optionally check if observers are notified after setting a new block
    }

    "execute undo and redo correctly" in {
      val playerAmount = 4
      val firstBlockType = 1
      val width = 10
      val height = 10
      val controllertest3 = new Controller(playerAmount, firstBlockType, width, height)
      val initialField3 = controllertest3.getField()

      // Perform a move or action to create a command
      controllertest3.move(3)
      controllertest3.move(3)
      controllertest3.move(3)
      controllertest3.move(3)
      controllertest3.move(3)
      controllertest3.move(2)
      controllertest3.move(2)
      controllertest3.move(2)
      controllertest3.move(2)
      controllertest3.move(2)
      controllertest3.move(2)
      controllertest3.placeBlock(1) shouldBe a[scala.util.Success[_]] // Assuming this is a valid move

      // Check if undo and redo work correctly
      controllertest3.undo() shouldBe a[scala.util.Success[_]]
      controllertest3.undo() shouldBe a[scala.util.Failure[_]]
      controllertest3.getField() shouldBe initialField3

      controllertest3.redo() shouldBe a[scala.util.Success[_]]
      // Validate if the action has been redone correctly
      // Optionally check if observers are notified after undo and redo

      controllertest3.redo() shouldBe a[scala.util.Failure[_]]
    }

    "changecurrentblock soll gehen" in {
      controller.changeCurrentBlock(1)
      controller.getBlock() shouldBe List((2, 3), (2, 4))
    }

    "check if canSetzen() returns correct result" in {
      val playerAmount = 4
      val firstBlockType = 2
      val width = 10
      val height = 10
      val test4controller = new Controller(playerAmount, firstBlockType, width, height)

      // Assuming your logic for "canSetzen" depends on the state of hoverBlock and field,
      // you should configure the state to test different cases
      test4controller.move(3)
      test4controller.move(3)
      test4controller.move(3)
      test4controller.move(3)
      test4controller.move(3)
      test4controller.move(2)
      test4controller.move(2)
      test4controller.move(2)
      test4controller.move(2)
      test4controller.move(2)
      test4controller.move(2)
      test4controller.canPlace() shouldBe true
      test4controller.placeBlock(2) // Set a block type
      test4controller.canPlace() shouldBe false

      // Test when it's not possible to setzen
      // For example, when the hoverBlock is in an invalid position
      // You need to configure your controller's state accordingly
      // controller.move(0) // Move to an invalid position
      // controller.canSetzten() shouldBe false
    }

    "change the player correctly with nextPlayer()" in {
      val playerAmount = 2
      val firstBlockType = 1
      val width = 10
      val height = 10
      val test2controller = new Controller(playerAmount, firstBlockType, width, height)

      // Assuming that your initial state starts with player 0

      // Test changing to the next player
      test2controller.nextPlayer() shouldBe 1
      test2controller.getCurrentPlayer() shouldBe 1

      // Test changing to the last player
      test2controller.nextPlayer() shouldBe 0
      test2controller.getCurrentPlayer() shouldBe 0
    }
  }
}
