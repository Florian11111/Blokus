package de.htwg.se.blokus.view

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox
import de.htwg.se.blokus.controller.GameController

class EndScene(gui: Gui, controller: GameController) {
    def createScene(): Scene = {
        new Scene {
            content = new VBox {
                children = Seq(
                    new Label("Game Over"),
                    new Button("Play Again") {
                        onAction = _ => {
                            gui.switchToStartScene()
                        }
                    }
                )
            }
        }
    }
}
