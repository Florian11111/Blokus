package blokus.view

import blokus.models.Field
import blokus.controller.Controller
import scala.io.StdIn

class Tui(controller: Controller) {
  def configureTerminalForImmediateInput(): Unit = {
    if (System.getProperty("os.name").toLowerCase.contains("win")) {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
    } else {
      val cmd = "stty -icanon min 1 -echo"
      val pb = new ProcessBuilder("sh", "-c", cmd)
      pb.inheritIO().start().waitFor()
    }
  }

  def clearTerminal(): Unit = {
    print("\u001b[H\u001b[2J")
    System.out.flush()
  }

  def captureKeyPress(): Char = {
    StdIn.readChar()
  }

  def resetTerminalToNormal(): Unit = {
    val cmd = "stty echo icanon"
    val pb = new ProcessBuilder("sh", "-c", cmd)
    pb.inheritIO().start().waitFor()
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
