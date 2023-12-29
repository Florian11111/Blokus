package de.htwg.se.blokus.view

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{BorderPane, VBox} 
import de.htwg.se.blokus.controller.GameController
import javafx.scene.layout.BorderPane
import scalafx.geometry.Pos
import scalafx.scene.text.Font
import scalafx.scene.layout.HBox
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Pane
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color

class EndScene(gui: Gui, controller: GameController, names: List[String]) {
    private val inputFields = new ObservableBuffer[Pane]()
    val amountBlocks = controller.getBlockAmount()
    val playerScores = names.zip(amountBlocks).sortBy(-_._2).take(controller.getPlayerAmount())
    
    val maxBlocksIndex = amountBlocks.zipWithIndex.maxBy(_._1)._2
    val maxScore = amountBlocks(maxBlocksIndex)
    val winners = names.zip(amountBlocks).filter(_._2 == maxScore).map(_._1).mkString(", ")


    private val winnerLable = new Label(s"Winner: $winners \n Placed blocks: ${amountBlocks(maxBlocksIndex)}") {
        font = Font("Arial", 16)
        style = "-fx-text-fill: white;"
    }

    private val vbox = new VBox {
        alignment = Pos.CenterRight
    }

    def createScene(): Scene = {
        //stage.setOnCloseRequest(_ => controller.exit())
        import scalafx.scene.layout.BorderPane

        val rootPane = new BorderPane {
            style = "-fx-background-color: #191819;"
            top = new VBox {
                alignment = Pos.Center
                children = Seq(
                    new Label("Game Over") {
                        margin = scalafx.geometry.Insets(25, 0, 10, 0)
                        font = Font("Arial", 40)
                        style = "-fx-text-fill: white;"
                    },
                    winnerLable
                )
            }
            center = vbox
            vbox.alignment = Pos.Center
            vbox.children = playerScores.map { case (name, score) =>
                new Label(s"$name: $score") {
                    font = Font("Arial", 16)
                    style = "-fx-text-fill: white;"
                }
            }
            bottom = new StackPane {
                alignment = Pos.Center
                children = Seq(
                    new Button("Play Again") {
                        style = "-fx-text-fill: white; -fx-background-color: black; -fx-alignment: center; -fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-radius: 5;"
                        onAction = _ => {
                            gui.switchToStartScene()
                        }
                    }
                )
                margin = scalafx.geometry.Insets(0, 0, 20, 0)
            }
        }
        new Scene {
            fill = Color.BLACK
            root = rootPane
        }
    }
}
