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
import scalafx.scene.image.ImageView
import javafx.scene.image.Image
import javafx.scene.effect.ImageInput
import javafx.stage.Stage
	

class GameScene(gui: Gui, controller: GameController, windowsWidth: Int, windowsHeight: Int, names: List[String], val stage: Stage) extends Observer[Event] {

    controller.addObserver(this)
    private var boardPane: GridPane = _
    private var currentPlayerLabel: Label = _
    private var buttonBox: GridPane = _

    private var stageWidth: Double = windowsWidth + 20
    private var stageHeight: Double = windowsHeight + 20
    private var hoverX: Int = -1
    private var hoverY: Int = -1

    def createScene(): Scene = {
        val scene = new Scene {
            fill = Color.web("#191819", 1)
            content = new VBox {
                spacing = 10
                alignment = scalafx.geometry.Pos.Center

                // Initialisiere currentPlayerLabel hier
                currentPlayerLabel = new Label("Current Player: ")
                children.add(currentPlayerLabel)

                children.add(new HBox {
                    spacing = 10
                    // add margin to HBox
                    margin = scalafx.geometry.Insets(0, 0, 0, 10)
                    alignment = scalafx.geometry.Pos.Center

                    boardPane = new GridPane {}
                    children.add(boardPane)

                    buttonBox = new GridPane {
                        // Replace spaces with tabs for indentation
                        spacing = 10
                        alignment = scalafx.geometry.Pos.Center
                    }
                    children.add(buttonBox)
                })

                val undobutton = new Button(s"Undo")
                styleButton(undobutton)
                undobutton.onAction = _ => controller.undo()
                children.add(undobutton)
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
        updateLabels()
        updateBoard()
        scene
    }

    def styleButton(button: Button): Unit = {
        button.style = "-fx-text-fill: white; -fx-background-color: black; " +
            "-fx-alignment: center; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
    }


    override def update(event: Event): Unit = {
        updateBoard()
        updateLabels()
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

    def updateBoard(): Unit = {
        val mergedFieldAndBlock = mergeFieldAndBlock()
        boardPane.children.clear()
        for {
            (row, rowIndex) <- mergedFieldAndBlock.zipWithIndex
            (value, columnIndex) <- row.zipWithIndex
        } {
            val button = new Button {
                focusTraversable = false
                val size = min(((windowsWidth - 250) / controller.getWidth()), ((windowsHeight - 120) / controller.getHeight())).doubleValue()
                onAction = _ => handleButtonAction(rowIndex, columnIndex)
                minWidth = size.doubleValue()
                maxWidth = size
                minHeight = size
                maxHeight = size
                style = s"-fx-background-color: ${getFillColor(value)}"
                onMouseEntered = _ => handleMouseHover(columnIndex, rowIndex)
            }
            boardPane.add(button, columnIndex, rowIndex)
        }
    }
    def handleMouseHover(x: Int, y: Int): Unit = {
        if (x == hoverX && y == hoverY)
            return // do nothing
        hoverX = x
        hoverY = y
        controller.setXandY(x, y)
    }

    def handleButtonAction(x: Int, y: Int): Unit = {
        if (controller.canPlace()) {
            controller.placeBlock()
        } else {
            // TODO: error label
            println("Kann nicht an dieser Stelle Platziert werden!")
        }
    }

    def getFillColor(value: Int): String = {
        value match {
            case -1 => "#101010" // Dunkelgrau
            case 0 => "#FF0000" // Rot
            case 1 => "#00B000" // Grün
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
        updateBlockList()
        currentPlayerLabel.text = s"Current Player: ${names(controller.getCurrentPlayer())}"
        currentPlayerLabel.style = "-fx-text-fill: white; -fx-font-size: 20px;"
    }

    def blockchange(blockNumber: Int): Unit = {
        if (blockNumber >= 0 && blockNumber <= 20) {
            val blocks = controller.getBlocks()
            if (blocks(blockNumber) > 0) {
                controller.changeCurrentBlock(blockNumber)
            } else {
                // TODO error label
                print("Block not avalibel!")
            }
        } else {
            throw new IllegalArgumentException("Blocks go from index 0 to 20!")
        }
    }

    def getImage(index: Int): ImageView = {
        val path = controller.getCurrentPlayer() match {
            case 0 => "./src/main/resources/red/"
            case 1 => "./src/main/resources/green/"
            case 2 => "./src/main/resources/blue/"
            case 3 => "./src/main/resources/yellow/"
            case _ => throw new IllegalArgumentException("Player index must be between 0 and 3!")
        }
        val file = new java.io.File(path + "block" + index + ".png")
        val image = new Image(file.toURI().toString())
        val imageView = new ImageView(image)
        imageView.fitHeight = image.getHeight / 2
        imageView.fitWidth = image.getWidth / 2
        imageView
    }
    

    def updateBlockList(): Unit = {
        buttonBox.children.clear()  // Lösche alle alten Buttons

        val blocks = controller.getBlocks()
        blocks.zipWithIndex.filter { case (block, _) => block != 0 }.foreach { case (block, index) =>
            val button = new Button {
                onAction = _ => blockchange(index)
                prefWidth = 60
                prefHeight = 50
                graphic = getImage(index)
                style = "-fx-border-color: transparent; -fx-background-color: transparent;"
            }
            val columnIndex = index % 3
            val rowIndex = index / 3
            buttonBox.add(button, columnIndex, rowIndex)
        }
    }
}