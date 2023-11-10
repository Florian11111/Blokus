package blokus.view

import blokus.controller.Controller
import blokus.controller.ControllerEvent
import blokus.util.Observer

class Tui(controller: Controller) extends Observer[ControllerEvent] {

    controller.addObserver(this)

    def clearTerminal(): Unit = {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    def mergeFieldAndBlock(playerNumber: Int): Vector[Vector[Int]] = {
        val field = controller.getField()
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

    override def update(event: ControllerEvent): Unit = {
        clearTerminal()
        display()
        println("\nSteuerung:")
        println("w/a/s/d: Block bewegen")
        println("r: Block rotieren")
        println("m: Block spiegeln")
        println("s: Block platzieren")
        println("x: Beenden")

        event match {
        case ControllerEvent.Move =>
            // Handle the move event if needed
        case ControllerEvent.Rotate =>
            // Handle the rotate event if needed
        case ControllerEvent.Mirror =>
            // Handle the mirror event if needed
        case ControllerEvent.Setzen =>
            // Handle the setzen event if needed
        case ControllerEvent.PlayerChange(player) =>
            // Handle the player change event if needed
        }
    }

    def inputLoop(): Unit = {
        try {
        var continue = true
        while (continue) {
            val input = scala.io.StdIn.readLine()
            input match {
            case "x" => continue = false
            case "w" => controller.move(2)
            case "d" => controller.move(1)
            case "s" => controller.move(0)
            case "a" => controller.move(3)
            case "r" => controller.rotate()
            case "m" => controller.mirror()
            case "e" => controller.setzen(2)
            case _ =>
            }
        }
        } finally {
        // Reset any terminal configurations if needed
        }
    }
}
