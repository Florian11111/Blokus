package de.htwg.se.blokus.view

import de.htwg.se.blokus.controller.controllerInvImpl.Controller
import de.htwg.se.blokus.controller.*
import de.htwg.se.blokus.controller.GameController

import de.htwg.se.blokus.util.Observer
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{GridPane, HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.application.Platform
import scala.annotation.targetName
import scalafx.scene.input.ScrollEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
	

class GameScene(controller: GameController, windowsWidth: Int, windowsHeight: Int, names: List[String], playerAmount: Int) extends JFXApp3 with Observer[Event] {
    
    controller.addObserver(this)

    private var boardPane: GridPane = _
    private var currentPlayerLabel: Label = _
    private var blocksLabel: Label = _
    private var numberTextField: scalafx.scene.control.TextField = _  // Beachten Sie den vollqualifizierten Import für TextField

    private var stageWidth: Double = windowsWidth + 20
    private var stageHeight: Double = windowsHeight + 20
    private var hoverX: Int = -1
    private var hoverY: Int = -1

    override def start(): Unit = {
        stage = new JFXApp3.PrimaryStage {
            title.value = "Blokus Game"
            width = stageWidth
            height = stageHeight
            scene = createScene()
            updateBoard()
            updateLabels()
        }
    }

    def createScene(): Scene = {
        new Scene {
            fill = Color.web("#333333",1)
            content = new VBox {
				spacing = 10
				alignment = scalafx.geometry.Pos.Center

				// Initialisiere currentPlayerLabel hier
				currentPlayerLabel = new Label("Current Player: ")
				children.add(currentPlayerLabel)

				blocksLabel = new Label(controller.getBlocks().toString)
				children.add(blocksLabel)

				boardPane = new GridPane {}

				children.add(boardPane)

				// Erstelle eine HBox für die Buttons
				val buttonBox2 = new HBox {
					spacing = 10
					alignment = scalafx.geometry.Pos.Center
				}

				val button8 = new Button(s"Undo")
				styleButton(button8)
				button8.onAction = _ => handleButtonAction(7)
				buttonBox2.children.add(button8)

				val button9 = new Button(s"BlockWechseln")
				styleButton(button9)
				button9.onAction = _ => handleButtonAction(8)
				buttonBox2.children.add(button9)

				numberTextField = new TextField {
					promptText = "Block wechsel"
				}
				buttonBox2.children.add(numberTextField)
				children.add(buttonBox2)
			}
            onScroll = (event: ScrollEvent) => {
                if (event.deltaY > 0) {
                    controller.rotate()
                }
            }
            onKeyPressed = (event: KeyEvent) => {
                if (event.code == KeyCode.M) {
                    controller.mirror()
                }
            }
        }
    }

    def styleButton(button: Button): Unit = {
        button.style = "-fx-font-size: 10pt;" +
        "-fx-background-color: #a6a6a6, linear-gradient(#aaaaaa, #808080)," +
        "linear-gradient(#aaaaaa 0%, #808080 50%, #666666 51%, #757575 100%);" +
        "-fx-background-radius: 6, 5;" +
        "-fx-background-insets: 0, 1;" +
        "-fx-text-fill: black;" +
        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 1);"
    }


    def createButtonBox(buttonLabels: Seq[String]): HBox = {
        new HBox {
        spacing = 10
        alignment = scalafx.geometry.Pos.Center

        for ((label, index) <- buttonLabels.zipWithIndex) {
            val button = new Button(label)
            button.onAction = _ => handleButtonAction(index)
            button.styleClass = Seq("rounded-button")
            children.add(button)
        }
        }
    }

    // Beispiel-Funktion, die von den Buttons aufgerufen wird
    def handleButtonAction(buttonNumber: Int): Unit = {
        buttonNumber match {
            case 0 => controller.move(2)
            case 1 => controller.move(0)
            case 2 => controller.move(3)
            case 3 => controller.move(1)
            case 4 => controller.mirror()
            case 5 => controller.rotate()
            case 6 => {
                if (controller.canPlace()) {
                    controller.placeBlock()
                } else {
                    println("Kann nicht an dieser Stelle Platziert werden!")
                }
            }
            case 7 => controller.undo()
            case 8 => {
                val inputText = numberTextField.text.value
                if (inputText.matches("""\d+""")) {
                    val blockIndex = inputText.toInt
                    if (blockIndex >= 0 && blockIndex <= 20) {
                        val blocks = controller.getBlocks()
                        if (blocks(blockIndex) > 0) {
                            controller.changeCurrentBlock(blockIndex)
                        } else {
                            print("Block nicht mehr verfuegbar!")
                        }
                    } else {
                        print("Wahle Block Zwischen 0 und 20!")
                    }
                } else {
                    print("Bitte gebe eine Zahl an!")
                }
            }
            case _ =>
        }
    }


    override def update(event: Event): Unit = {
        Platform.runLater(() => {
            updateBoard()
            updateLabels()
        })
    }

    def mergeFieldAndBlock(): Vector[Vector[Int]] = {
        val field = controller.getField()
        val block = controller.getBlock()

        val merged = field.zipWithIndex.map { case (row, rowIndex) =>
            row.zipWithIndex.map { case (fieldValue, columnIndex) =>
                block.find { case (x, y) =>
                    x == columnIndex && y == rowIndex
                }.map(_ => if (fieldValue != -1) 11 else 20 + controller.getCurrentPlayer()).getOrElse(fieldValue)
            }
        }
        merged
    }

/*
    def updateBoard(): Unit = {
        val mergedFieldAndBlock = mergeFieldAndBlock()
        print(mergedFieldAndBlock)
        boardPane.children.clear()
        for {
            (row, rowIndex) <- mergedFieldAndBlock.zipWithIndex
            (value, columnIndex) <- row.zipWithIndex
        } {
            val rectangle = new Rectangle {
                width = (windowsWidth / controller.getWidth()).toInt
                height = (windowsWidth / controller.getWidth()).toInt
                fill = getFillColor(value)
            }
            boardPane.add(rectangle, columnIndex, rowIndex)
        }
    }
*/

    def updateBoard(): Unit = {
        val mergedFieldAndBlock = mergeFieldAndBlock()
        boardPane.children.clear()
        for {
            (row, columnIndex) <- mergedFieldAndBlock.zipWithIndex
            (value, rowIndex) <- row.zipWithIndex
        } {
            val button = new Button {
                //  text = s"$rowIndex, $columnIndex"
                onAction = _ => handleButtonAction(rowIndex, columnIndex)
                prefWidth = (windowsWidth / controller.getWidth()).toInt
                prefHeight = (windowsWidth / controller.getWidth()).toInt
                style = s"-fx-background-color: ${getFillColor(value)}"
                onMouseEntered = _ => handleMouseHover(rowIndex, columnIndex)
            }
            boardPane.add(button, columnIndex, rowIndex)
        }
    }
    def handleMouseHover(x: Int, y: Int): Unit = {
        if (x == hoverX && y == hoverY)
            return // do nothing
        hoverX = x
        hoverY = y
        println(s"Mouse hovered over $x, $y")
        controller.setXandY(x, y)
    }

    def handleButtonAction(x: Int, y: Int): Unit = {
        controller.placeBlock()
        println(s"Button at $x, $y was pressed.")
    }

    def getFillColor(value: Int): String = {
        value match {
            case -1 => "#FFFFFF" // Weiß
            case 0 => "#FF0000" // Rot
            case 1 => "#008000" // Grün
            case 2 => "#0000FF" // Blau
            case 3 => "#FFFF00" // Gelb
            case 10 => "#808080" // Grau
            case 11 => "#D3D3D3" // Hellgrau
            case 20 => "#8B0000" // Dunkelrot
            case 21 => "#006400" // Dunkelgrün
            case 22 => "#00008B" // Dunkelblau
            case 23 => "#9B870C" // Dunkelgelb
            case _ => "#000000" // Schwarz
        }
    }

    def updateLabels(): Unit = {
        val currentPlayerLabelText = s"Current Player: ${controller.getCurrentPlayer() + 1}"
        val blocksLabelText = s"Blocks: ${controller.getBlocks()}"

        currentPlayerLabel.text = currentPlayerLabelText
        blocksLabel.text = blocksLabelText

        // Ändere die Textfarbe und den Hintergrund der Labels
        currentPlayerLabel.style = "-fx-text-fill: white;" // oder eine andere gewünschte Farbe
        blocksLabel.style = "-fx-text-fill: white;" // oder eine andere gewünschte Farbe
    }
}