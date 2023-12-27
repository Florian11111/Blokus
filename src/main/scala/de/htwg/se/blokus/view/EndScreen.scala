package de.htwg.se.blokus.view

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox

class EndScene(gui: Gui) {
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
