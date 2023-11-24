package blokus.view

import blokus.controller.Controller
import blokus.controller.ControllerEvent
import blokus.util.Observer

class Tui(controller: Controller) extends Observer[ControllerEvent] {
    controller.addObserver(this)
    display()
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
        print(controller.getBlock())
        println("\nSteuerung:")
        println("w/a/s/d: Block bewegen")
        println("r: Block rotieren")
        println("m: Block spiegeln")
        println("s: Block platzieren")
        println("u: Undo")
        println("x: Beenden")

        event match {
            case ControllerEvent.Update =>
            case ControllerEvent.PlayerChange(player) =>
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
                    case "e" => {
                        if (controller.canSetzten()) {
                            controller.setzen(5)
                            controller.nextPlayer()
                        } else {
                            println("Kann nicht an dieser Stelle Platziert werden!")
                        }
                    }
                    case _ =>
                }
            }
        } finally {
            // Reset any terminal configurations if needed
        }
    }
}
