package de.htwg.se.blokus.view

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blokus.controller.*
import de.htwg.se.blokus.controller.controllerInvImpl.Controller

class GuiSpec extends AnyWordSpec with Matchers {
    "A Gui" when {
        val controller = new Controller
        val gui = new Gui(controller, 100, 100)
        "initialized" should {
            "start with empty params" in {
                val controller2 = new Controller
                val gui2 = new Gui(controller, 100, 100)
                controller.getObservers() should contain (gui)
            }
        }
        "calling setNames" should {
            "start with empty namelist" in {
                gui.setNames(List("Test1", "Test2")) shouldBe List()
            }
            "update the list accordingly" in {
                gui.setNames(List("Test1", "Test2", "Test3")) shouldBe List("Test1", "Test2")
            }
        }
    }
}
