package blokus.view

import blokus.controller.Controller
import blokus.controller.ControllerEvent
import blokus.util.Observer

trait TuiState {
    def handleInput(input: String, controller: Controller): Unit
    def printInfo(controller: Controller): Unit
}

class Tui(controller: Controller) extends Observer[ControllerEvent] {
    private var currentState: TuiState = new DefaultState

    controller.addObserver(this)
    display()
    currentState.printInfo(controller)
    def setCurrentState(state: TuiState): Unit = {
        currentState = state
    }

    def clearTerminal(): Unit = {
        val os = System.getProperty("os.name").toLowerCase()
        if (os.contains("win")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
        } else {
            print("\u001b[H\u001b[2J")
            System.out.flush()
        }
    }

    def mergeFieldAndBlock(): Vector[Vector[Int]] = {
        val field = controller.getField()
        val block = controller.getBlock()

        val merged = field.zipWithIndex.map { case (row, rowIndex) =>
            row.zipWithIndex.map { case (fieldValue, columnIndex) =>
            block.find { case (x, y) =>
                x == columnIndex && y == rowIndex
            }.map(_ => if (fieldValue != -1) 11 else 10).getOrElse(fieldValue)
            }
        }
        merged
    }

    def display(): Unit = {
        println(mergeFieldAndBlock().map(rowToString).mkString("\n"))
    }

    def rowToString(row: Vector[Int]): String = {
        row.map {
            case -1 => "+ "
            case 0 => "1 "
            case 1 => "2 "
            case 2 => "3 "
            case 3 => "4 "
            case 10 => "# "
            case 11 => "? "
            case _ => "? "
        }.mkString
    }

    override def update(event: ControllerEvent): Unit = {
        clearTerminal()
        display()
        currentState.printInfo(controller)
        event match {
            case ControllerEvent.Update =>
            case ControllerEvent.PlayerChange(player) =>
        }
    }

    def inputLoop(): Unit = {
        try {
        var continue = true
        while (continue) {
            if (controller.isOver()) {
                setCurrentState(new OverState)
            }
            val input = scala.io.StdIn.readLine()
            currentState.handleInput(input, controller)
        }
        } finally {
        // Reset any terminal configurations if needed
        }
    }

    private class DefaultState extends TuiState {

        override def printInfo(controller: Controller): Unit = {
            printf("Spieler %d\n", controller.getcurrentPlayer() + 1)
            println("\nSteuerung: ")
            println("w/a/s/d: Block bewegen")
            println("r: Block rotieren")
            println("m: Block spiegeln")
            println("e: Block platzieren")
            println("u: Undo")
            println("x: Beenden")
        }

        override def handleInput(input: String, controller: Controller): Unit = {
        input match {
            case "x" => sys.exit(0)
            case "w" => controller.move(2)
            case "d" => controller.move(1)
            case "s" => controller.move(0)
            case "a" => controller.move(3)
            case "r" => controller.rotate()
            case "m" => controller.mirror()
            case "u" => controller.undo()
            case "e" => {
            if (controller.canSetzten()) {
                controller.setzen(1)
                controller.nextPlayer()
            } else {
                println("Kann nicht an dieser Stelle Platziert werden!")
            }
            }
            case _ =>
        }
        }
    }

    private class OverState extends TuiState {
        override def printInfo(controller: Controller): Unit = {}
        override def handleInput(input: String, controller: Controller): Unit = {
        println("\nSpiel vorbei: ")
        println("e: Exit")
        println("n: Neues Spiel")
        input match {
            case "n" => print("Neues Spiel")
            case "e" => print("Speil vorbei")
            case _ =>
        }
        }
    }
}
