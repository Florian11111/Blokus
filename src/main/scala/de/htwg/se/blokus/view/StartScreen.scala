package de.htwg.se.blokus.view

import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.text.Font
import scalafx.scene.paint.Color
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.Pane
import de.htwg.se.blokus.controller.GameController
import javafx.stage.Stage

class StartScene(gui: Gui, controller: GameController) {
    private val inputFields = new ObservableBuffer[Pane]()

    private val playerLabel = new Label("Players: 2") {
        font = Font("Arial", 16)
        style = "-fx-text-fill: white;"
    }
    private val errorLabel = new Label("") {
        font = Font("Arial", 12)
        style = "-fx-text-fill: red;"
    }

    // Erstellen Sie zwei Eingabefelder beim Start
    inputFields += new Pane {
        children = Seq(new scalafx.scene.control.TextField() {
            style = "-fx-text-fill: white; -fx-background-color: black; -fx-alignment: center; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
            promptText = "Name"
        })
        margin = scalafx.geometry.Insets(5) // Fügt einen Rand von 10 Pixeln hinzu
    }
    inputFields += new Pane {
        children = Seq(new scalafx.scene.control.TextField() {
            style = "-fx-text-fill: white; -fx-background-color: black; -fx-alignment: center; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
            promptText = "Name"
        })
        margin = scalafx.geometry.Insets(5) // Fügt einen Rand von 10 Pixeln hinzu
    }
    

    private val vbox = new VBox {
        alignment = Pos.CenterRight
    }
    private val buttons = new HBox {
        alignment = Pos.CenterLeft
    }

    def createScene(): Scene = {
        //stage.setOnCloseRequest(_ => controller.exit())
        val rootPane = new BorderPane {
            style = "-fx-background-color: #191819;"
            top = new VBox {
                alignment = Pos.Center
                children = Seq(
                    new Label("Blokus") {
                        margin = scalafx.geometry.Insets(25, 0, 10, 0)
                        font = Font("Arial", 40)
                        style = "-fx-text-fill: white;"
                    },
                    playerLabel,
                    errorLabel
                )
            }
            center = vbox
            vbox.alignment = Pos.Center
            buttons.children = Seq(
                new Button("+") {
                    margin = scalafx.geometry.Insets(0, 10, 0, 25)
                    style = "-fx-border-color: #b9b8b9; -fx-border-radius: 5; -fx-text-fill: white; -fx-background-color: #292829;"
                    onAction = _ => {
                        if (inputFields.size < 4) {
                            val newField = new Pane {
                                children = Seq(new scalafx.scene.control.TextField() {
                                    style = "-fx-text-fill: white; -fx-background-color: black; -fx-alignment: center; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
                                    promptText = "Name"
                                })
                                margin = scalafx.geometry.Insets(5)
                            }
                            inputFields += newField
                            vbox.children = inputFields ++ Seq(buttons)
                        }
                        playerLabel.text = s"Player: ${inputFields.size}"
                        errorLabel.text = ""
                    }
                    },
                new Button("-") {
                    style = "-fx-border-color: #b9b8b9; -fx-border-radius: 5; -fx-text-fill: white; -fx-background-color: #292829;;"
                    onAction = _ => {
                        if (inputFields.size > 2) {
                            val lastField = inputFields.last
                            inputFields -= lastField
                            vbox.children = inputFields ++ Seq(buttons)
                            playerLabel.text = s"Players: ${inputFields.size}"
                            errorLabel.text = ""
                        }
                    }
                    margin = scalafx.geometry.Insets(0, 25, 0, 0)
                }
            )
            vbox.children = inputFields ++ Seq(buttons)
            bottom = new StackPane {
                alignment = Pos.Center
                children = Seq(
                    new Button("Start Game") {
                        style = "-fx-text-fill: white; -fx-background-color: black; -fx-alignment: center; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
                        onAction = _ => {
                            val names = inputFields.map(_.children(0).asInstanceOf[javafx.scene.control.TextField].getText)
                            if (names.exists(_.isEmpty)) {
                                errorLabel.text = "Error: One or more fields are empty."
                            } else if (names.distinct.size != names.size) {
                                errorLabel.text = "Error: Duplicate names are not allowed."
                            } else {
                                controller.start(names.size, 20, 20)
                                gui.switchToGameScene(names.toList)
                            }
                        }
                    }
                )
                margin = scalafx.geometry.Insets(0, 0, 20, 0)
            }
        }
        new Scene {
            fill = Color.Black 
            root = rootPane
        }
    }
}
