package de.htwg.se.blokus.view
import de.htwg.se.blokus.controller.GameController
import de.htwg.se.blokus.util.Observer
import de.htwg.se.blokus.controller.Event

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.stage.Stage

class Gui(controller: GameController, windowsWidth: Int, windowsHeight: Int) extends JFXApp3 with Observer[Event] {
    
    private var myStage: Stage = _

    controller.addObserver(this)
    def update(event: Event): Unit = {
            // implementation of the update method
    }
    
    override def start(): Unit = {
        myStage = new JFXApp3.PrimaryStage {
            title.value = "Blokus Game"
            width = windowsWidth + 20
            height = windowsHeight + 20
            scene = new StartScene(Gui.this).createScene()
        }
    }

    def switchToStartScene(): Unit = {
        myStage.scene = new StartScene(this).createScene()
    }

    def switchToGameScene(names: List[String], playerAmount: Int): Unit = {
        myStage.scene = new GameScene(controller, windowsWidth, windowsHeight, names, playerAmount).createScene()
    }

    def switchToEndScene(): Unit = {
        myStage.scene = new EndScene(this).createScene()
    }
}