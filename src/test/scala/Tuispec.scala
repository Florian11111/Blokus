package blokus.view

import blokus.controller.{Controller, ControllerEvent}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import blokus.util.Observer

// Test Observer to capture state changes and outputs
class TestObserver extends Observer[ControllerEvent] {
  var latestOutput: String = ""
  var latestEvent: ControllerEvent = _

  override def update(event: ControllerEvent): Unit = {
    latestEvent = event
    // Here, you would capture the output of Tui.display or other relevant methods
    // For example, you might adapt Tui to allow capturing its printed output
  }
}

class TuiSpec extends AnyWordSpec {
  "A Tui" when {
    "initialized with a controller" should {
      val controller = new Controller(4, 1, 10, 10) // Setup the controller
      val tui = new Tui(controller) // Instantiate Tui with the controller
      val testObserver = new TestObserver() // Test observer to capture Tui output
      controller.addObserver(testObserver) // Add test observer to controller

      "display the initial state" in {
        // Assuming Tui.display() updates some internal state or returns a string
        // TestObserver captures this state or string
        // Assert that the initial state or string is as expected
      }

      "update display on controller event" in {
        // Simulate a controller event, such as a move or rotate
        controller.move(1) // Example: move the block
        // Assert that the Tui's state or output has updated correctly
      }

      "handle user input correctly" in {
        // Simulate user inputs such as 'w', 'a', 's', 'd', 'r', 'm'
        // You can do this by calling the methods on the controller that these inputs would trigger
        // For each input, check that the Tui's state or output updates as expected
      }
    }
  }
}
