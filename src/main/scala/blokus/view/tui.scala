package blokus.view

import blokus.models.Field
import blokus.controller.Controller
import scala.io.StdIn

class Tui(controller: Controller) {
  def configureTerminalForImmediateInput(): Unit = {
    // Implementation remains the same
  }

  def clearTerminal(): Unit = {
    print("\u001b[H\u001b[2J")
    System.out.flush()
  }

  def captureKeyPress(): Char = {
    StdIn.readChar()
  }

  def resetTerminalToNormal(): Unit = {
    // Implementation remains the same
  }

  def inputLoop(): Unit = {
    configureTerminalForImmediateInput()
    try {
      var continue = true
      while (continue) {
        clearTerminal()
        controller.display() // bullshit

        println("Steuerung:")
        println("w/a/s/d: Block bewegen")
        println("q: Block rotieren")
        println("e: Block spiegeln")
        println("Enter: Block platzieren")
        println("x: Beenden")

        val input = captureKeyPress()
        input match {
          case 'x' =>
            continue = false
          case _ =>
            controller.handleInput(input) //bullshit, muss hier sein.
        }
      }
    } finally {
      resetTerminalToNormal()
    }
  }
}
