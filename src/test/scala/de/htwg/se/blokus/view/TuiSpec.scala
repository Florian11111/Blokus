package de.htwg.se.blokus.view

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blokus.controller.*
import de.htwg.se.blokus.controller.controllerInvImpl.Controller


class TuiSpec extends AnyWordSpec with Matchers {
    "A Tui" when {
        val controller = new Controller
        val tui = new Tui(controller)
        controller.start(2,10,10)
        "initialized" should {
            val controller2 = new Controller
            val tui2 = new Tui(controller2)
            "start with empty params" in {
                tui2.gameisStarted shouldBe false
                tui2.isGameOver shouldBe false
            }
            "start the controller" in {
                controller2.start(2,10,10)
                tui2.gameisStarted shouldBe true
            }
            "end the game" in {
                controller2.notifyObservers(EndGameEvent)
                tui2.isGameOver shouldBe true
            }
        }
        "calling mergeFieldAndBlock" should {
            "merge a field and block correctly" in {
                controller.setXandY(0,9)
                controller.changeBlock(2)
                tui.mergeFieldAndBlock() shouldBe Vector(Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1), Vector(10, 10, -1, -1, -1, -1, -1, -1, -1, -1))
            }
        }
        "calling rowToString" should {
            "convert a row to a string correctly" in {
                tui.rowToString(Vector(10, 10, -1, 0, 1, 2, 3, 11, 81, -1)) shouldBe "# # + 1 2 3 4 ? ? + "
            }
        }
    }
}
