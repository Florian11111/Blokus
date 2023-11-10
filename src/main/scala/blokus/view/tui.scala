package blokus.view

import blokus.controller.Controller
import scala.io.StdIn
import scala.annotation.switch

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

    def mergeFieldAndBlock(playerNumber: Int): Vector[Vector[Int]] = {
        val field = controller.getfield()
        val block = controller.getBlock()
        val merged = field.zipWithIndex.map { case (row, rowIndex) =>
            row.zipWithIndex.map { case (cell, columnIndex) =>
            val blockCell = block.find { case (x, y) =>
                x == columnIndex && y == rowIndex
            }
            blockCell.map(_ => playerNumber).getOrElse(cell)
            }
        }
        merged
    }


    def display(): Unit = {
        println(mergeFieldAndBlock(6).map(rowToString).mkString("\n"))
    }

    def rowToString(row: Vector[Int]): String = {
        row.map {
            case 0 => "# "
            case 1 => "+ "
            case 2 => "| "
            case 6 => "X "
            case _ => "? "
        }.mkString
    }
    def handleInput(input: Char) = {
        if (!Char.equals(None)) {
            input match {
                case 'w' => controller.move(2)
                case 'd' => controller.move(1)
                case 's' => controller.move(0)
                case 'a' => controller.move(3)
                case 'r' => controller.rotate() 
                case 'm' => controller.mirror()
                case 'e' => controller.setzen(2)
            }
        }
    }

    def inputLoop(): Unit = {
        configureTerminalForImmediateInput()
        try {
        var continue = true
        while (continue) {
            clearTerminal()
            display() 
            println("\nSteuerung:")
            println("w/a/s/d: Block bewegen")
            println("r: Block rotieren")
            println("m: Block spiegeln")
            println("s: Block platzieren")
            println("x: Beenden")

            val input = captureKeyPress()
            input match {
                case 'x' => continue = false
                case _ => handleInput(input)
            }
        }
        } finally {
            //resetTerminalToNormal()
        }
    }
}
