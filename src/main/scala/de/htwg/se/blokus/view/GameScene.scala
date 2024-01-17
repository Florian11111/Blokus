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
import scalafx.stage.FileChooser
import javafx.stage.Stage
import javafx.geometry.Insets
import scala.compiletime.ops.boolean

class GameScene(gui: Gui,
    controller: GameController,
    windowsWidth: Int,
    windowsHeight: Int,
    namesList: List[String],
    highPerformanceMode: Boolean,
    info: Boolean) extends Observer[Event] {

    controller.addObserver(this)

    // True if the pot should be shown
    private val showPotPos = true

    private var names = namesList
    private var oldField = controller.getField().map(_.map(_ => -2)).transpose

    private var boardPane: GridPane = _
    private var infoHeader: Label = _
    private var infoText: Label = _
    private var currentPlayerLabel: Label = _
    private var buttonBox: GridPane = _

    private var buttons: Array[Array[Button]] = Array.ofDim[Button](controller.getWidth(), controller.getHeight())
    private var stageWidth: Double = windowsWidth + 20
    private var stageHeight: Double = windowsHeight + 20
    private var hoverX: Int = -1
    private var hoverY: Int = -1

    private val imageBlue = loadBlockImage("blockBlue")
    private val imageRed = loadBlockImage("blockRed")
    private val imageGreen = loadBlockImage("blockGreen")
    private val imageYellow = loadBlockImage("blockYellow")
    private val imageDarkBlue = loadBlockImage("blockDarkBlue")
    private val imageDarkRed = loadBlockImage("blockDarkRed")
    private val imageDarkGreen = loadBlockImage("blockDarkGreen")
    private val imageDarkYellow = loadBlockImage("blockDarkYellow")
    private val imageVeryDarkYellow = loadBlockImage("blockVeryDarkYellow")
    private val imageVeryDarkBlue = loadBlockImage("blockVeryDarkBlue")
    private val imageVeryDarkRed = loadBlockImage("blockVeryDarkRed")
    private val imageVeryDarkGreen = loadBlockImage("blockVeryDarkGreen")
    private val imageDarkGrey = loadBlockImage("blockDarkGrey")
    private val imageGrey = loadBlockImage("blockGrey")
    private val imageLightGrey = loadBlockImage("blockLightGrey")
    private val imageCyan = loadBlockImage("blockCyan")
    private val imageBlack = loadBlockImage("blockBlack")

    def loadBlockImage(name: String): Image = {
        val file = new java.io.File("./src/main/resources/singleBlocks/" + name + ".png")
        new Image(file.toURI().toString())
    }

    def createScene(): Scene = {
        val undobutton = new Button(s"Undo")
        styleButton(undobutton)
        undobutton.margin = scalafx.geometry.Insets(5, 5, 5, 5)
        undobutton.onAction = _ => controller.undo()

        val savebutton = new Button(s"Save")
        styleButton(savebutton)
        savebutton.margin = scalafx.geometry.Insets(5, 5, 5, 5)
        savebutton.onAction = _ => saveGame()

        val loadbutton = new Button(s"Load")
        styleButton(loadbutton)
        loadbutton.margin = scalafx.geometry.Insets(5, 5, 5, 5)
        loadbutton.onAction = _ => loadGame()

        val box = new HBox()
        box.margin = scalafx.geometry.Insets(10, 0, 0, 0)
        box.children.addAll(undobutton, savebutton, loadbutton)
        box.alignment = scalafx.geometry.Pos.Center

        val scene = new Scene {
            root = new VBox {
                // add background coler to VBox
                style = "-fx-background-color: #191819;"
                spacing = 10
                alignment = scalafx.geometry.Pos.TopCenter
                // Initialisiere currentPlayerLabel hier
                currentPlayerLabel = new Label("Current Player: ")
                children.add(currentPlayerLabel)

                children.add(new HBox {
                    spacing = 10
                    // add margin to HBox
                    margin = scalafx.geometry.Insets(0, 0, 0, 10)
                    children.add(new VBox {
                        boardPane = new GridPane {}
                        children.add(boardPane)
                        children.add(box)
                    })

                    children.add(new VBox {
                        buttonBox = new GridPane {
                            spacing = 10
                            alignment = scalafx.geometry.Pos.Center
                        }
                        children.add(buttonBox)
                        if (info) {
                            children.add(new VBox {
                                // add margin
                                spacing = 2
                                alignment = scalafx.geometry.Pos.Center
                                style = "-fx-background-color: #151515; -fx-border-color: #333333; -fx-border-radius: 5;"
                                infoHeader = new Label("Controlls: ")
                                infoHeader.style = "-fx-text-fill: #999999; -fx-font-size: 16px; -fx-font-weight: bold;"
                                children.add(infoHeader)

                                infoText = new Label("w/a/s/d \t=> \tMove Block\n" +
                                    "r \t\t=> \tRotate\n" +
                                    "m \t\t=> \tMirror\n" +
                                    "e \t\t=> \tPlace Block\n" +
                                    "u \t\t=> \tUndo\n" +
                                    "q \t\t=> \tNew Random Block\n" +
                                    "x \t\t=> \tExit")
                                infoText.style = "-fx-text-fill: white; -fx-font-size: 12px;"
                                VBox.setMargin(infoText, new Insets(0, 0, 10, 0))
                                children.add(infoText)
                            })
                        }
                        alignment = scalafx.geometry.Pos.Center
                    })
                })
                updateHoleBoard()
            }
            onScroll = (event: ScrollEvent) => {
                if (event.deltaY > 0) {
                    controller.rotate()
                }
            }
            onKeyPressed = (event: KeyEvent) => {
                if (event.code == KeyCode.M) {
                    controller.mirror()
                } else if (event.code == KeyCode.R) {
                    controller.rotate()
                } else if (event.code == KeyCode.W) {
                    controller.move(0, -1)
                } else if (event.code == KeyCode.A) {
                    controller.move(-1, 0)
                } else if (event.code == KeyCode.S) {
                    controller.move(0, 1)
                } else if (event.code == KeyCode.D) {
                    controller.move(1, 0)
                } else if (event.code == KeyCode.Q) {
                    controller.setNextBLock()
                } else if (event.code == KeyCode.E) {
                    controller.placeBlock()
                } else if (event.code == KeyCode.U) {
                    controller.undo()
                } else if (event.code == KeyCode.ESCAPE) {
                    controller.exit()
                }
            }
        }
        updateLabels()
        updateBoard()
        scene
    }

    def styleButton(button: Button): Unit = {
        button.style = "-fx-text-fill: white; -fx-background-color: black; " +
            "-fx-alignment: center; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
    }

    def loadGame(): Unit = {
        // open file chooser and save the path
        val fileChooser = new FileChooser()
        fileChooser.title = "Open Game File (.json / .xml)"
        fileChooser.initialDirectory = new java.io.File("./saves")
        fileChooser.extensionFilters.addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json"),
            new FileChooser.ExtensionFilter("XML", "*.xml")
        )
        val selectedFile = fileChooser.showOpenDialog(null)
        if (selectedFile != null) {
            names = List("Player1", "Player2", "Player3", "Player4")
            gui.setNames(names)
            controller.load(selectedFile.getAbsolutePath())
        }
    }

    def saveGame(): Unit = {
        // open file chooser and save the path
        val fileChooser = new FileChooser()
        fileChooser.title = "Save Game File (.json / .xml)"
        fileChooser.initialDirectory = new java.io.File("./saves")
        fileChooser.extensionFilters.addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json"),
            new FileChooser.ExtensionFilter("XML", "*.xml")
        )
        val selectedFile = fileChooser.showSaveDialog(null)
        if (selectedFile != null) {
            controller.save(selectedFile.getAbsolutePath())
        }
    }

    override def update(event: Event): Unit = {
        Platform.runLater {
            event match {
                case PlaceBlockEvent => {
                    updateHoleBoard()
                    updateLabels()
                }
                case UpdateEvent => updateBoard()
                case EndGameEvent => {
                    controller.removeObserver(this)
                }
                case _ =>
            }
        }
    }

    def mergeFieldAndBlock(): Vector[Vector[Int]] = {
        val field = controller.getField()
        val block = controller.getBlock()
        val canPlace = if (controller.canPlace()) 0 else 10
        var merged = field

        if (showPotPos) {
            val corner = controller.getPosPositions(controller.getCurrentPlayer())
            merged = merged.indices.map { y =>
                if (corner.exists(_._2 == y)) {
                    merged(y).indices.map { x =>
                        if (corner.contains((x, y))) 70 else merged(y)(x)
                    }.toVector
                } else {
                    merged(y)
                }
            }.toVector
        }

        for ((x, y) <- block) {
            if (x >= 0 && x < controller.getWidth() && y >= 0 && y < controller.getHeight()){
                val fieldValue = merged(y)(x)
                merged = merged.updated(y, merged(y).updated(x, if (fieldValue != -1) 11 else 20 + controller.getCurrentPlayer() + canPlace))
            }
        }
        merged.transpose
    }

    def updateHoleBoard(): Unit = {
        // clear old board
        boardPane.children.clear()
        buttons = Array.ofDim[Button](controller.getWidth(), controller.getHeight())

        for {
            (row, rowIndex) <- buttons.zipWithIndex
            (columnIndex) <- row.indices
        } {
            val size = min(((windowsWidth - 280) / controller.getWidth()), ((windowsHeight - 120) / controller.getHeight())).doubleValue()
            val button = new Button {
                focusTraversable = false
                onAction = _ => handleButtonAction(rowIndex, columnIndex)
                minWidth = size.doubleValue()
                maxWidth = size
                minHeight = size
                maxHeight = size
                onMouseEntered = _ => handleMouseHover(rowIndex, columnIndex)
                if (!highPerformanceMode) {
                    style = "-fx-background-color: transparent; -fx-border-color: transparent;"
                }
            }
            boardPane.add(button, rowIndex, columnIndex)
            buttons(rowIndex)(columnIndex) = button
        }
        oldField = controller.getField().map(_.map(_ => -2)).transpose
        updateBoard()
    }

    def updateBoard(): Unit = {
        val mergedFieldAndBlock = mergeFieldAndBlock()
        for {
            (row, columnIndex) <- mergedFieldAndBlock.zipWithIndex
            (value, rowIndex) <- row.zipWithIndex
        } {
            if (mergedFieldAndBlock(columnIndex)(rowIndex) != oldField(columnIndex)(rowIndex)) {
                if (!highPerformanceMode) {
                    buttons(columnIndex)(rowIndex).graphic = getFillColorImage(value)
                } else {
                    buttons(columnIndex)(rowIndex).style = "-fx-background-color: " + getFillColor(value) + "; -fx-border-color: transparent;"
                }
            }
        }
        oldField = mergedFieldAndBlock
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

    def getFillColorImage(value: Int): ImageView = {
        var image = value match {
            case -1 => new ImageView(imageDarkGrey)
            case 0 => new ImageView(imageRed)
            case 1 => new ImageView(imageGreen)
            case 2 => new ImageView(imageBlue)
            case 3 => new ImageView(imageYellow)
            case 10 => new ImageView(imageGrey)
            case 11 => new ImageView(imageLightGrey)
            case 20 => new ImageView(imageDarkRed)
            case 21 => new ImageView(imageDarkGreen)
            case 22 => new ImageView(imageDarkBlue)
            case 23 => new ImageView(imageDarkYellow)
            case 30 => new ImageView(imageVeryDarkRed)
            case 31 => new ImageView(imageVeryDarkGreen)
            case 32 => new ImageView(imageVeryDarkBlue)
            case 33 => new ImageView(imageVeryDarkYellow)
            case 70 => new ImageView(imageCyan)
            case _ => new ImageView(imageBlack)
        }
        var size = 0.0
        if (controller.getWidth() > 0 && controller.getHeight() > 0) {
            size = min(((windowsWidth - 280) / controller.getWidth()), ((windowsHeight - 120) / controller.getHeight())).doubleValue()
        }
        image.fitHeight = size
        image.fitWidth = size
        image
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
            case 30 => "#4B0000" // Sehr dunkelrot
            case 31 => "#003200" // Sehr dunkelgrün
            case 32 => "#00004B" // Sehr dunkelblau
            case 33 => "#4B470C" // Sehr dunkelgelb
            case 70 => "#00FFFF" // Dunkelgelb
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

    def getImageForButton(index: Int): ImageView = {
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
                // on hover change border
                style = "-fx-border-color: transparent; -fx-background-color: transparent;"
                onMouseEntered = _ => style = "-fx-border-color: #4CAF50; -fx-background-color: transparent;"
                onMouseExited = _ => style = "-fx-border-color: transparent; -fx-background-color: transparent;"
                prefWidth = 60
                prefHeight = 50
                graphic = getImageForButton(index)
            }
            val columnIndex = index % 3
            val rowIndex = index / 3
            buttonBox.add(button, columnIndex, rowIndex)
        }
    }
}