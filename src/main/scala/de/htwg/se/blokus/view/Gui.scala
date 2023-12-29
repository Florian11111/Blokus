package de.htwg.se.blokus.view
import de.htwg.se.blokus.controller.GameController
import de.htwg.se.blokus.util.Observer
import de.htwg.se.blokus.controller.Event
import de.htwg.se.blokus.controller.StartGameEvent
import de.htwg.se.blokus.controller.EndGameEvent

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.application.Platform
import de.htwg.se.blokus.controller.ExitEvent
class Gui(controller: GameController, windowsWidth: Int, windowsHeight: Int) extends JFXApp3 with Observer[Event] {
    
    private var myStage: Stage = _
    private var gameSceneSwitched: Boolean = false

    controller.addObserver(this)

    def update(event: Event): Unit = {
        event match {
            case StartGameEvent => 
                Platform.runLater {
                    if (!gameSceneSwitched) {
                        switchToGameScene(List("Player1", "Player2", "Player3", "Player4"))
                        gameSceneSwitched = true
                    }
                }
            case EndGameEvent => 
                Platform.runLater {
                    switchToEndScene()
                }
            case ExitEvent => 
                Platform.runLater {
                    myStage.close()
                }
            case _ =>
        }
    }
    
    override def start(): Unit = {
        myStage = new JFXApp3.PrimaryStage {
            title.value = "Blokus Game"
            width = windowsWidth + 20
            height = windowsHeight + 20
            scene = new StartScene(Gui.this, controller).createScene()
        }
        myStage.setOnCloseRequest(_ => controller.exit())
    }

    def switchToStartScene(): Unit = {
        gameSceneSwitched = false
        myStage.scene = new StartScene(this, controller).createScene()
    }

    def switchToGameScene(names: List[String]): Unit = {
        gameSceneSwitched = true
        myStage.scene = new GameScene(this, controller, windowsWidth, windowsHeight, names, myStage).createScene()
    }

    def switchToEndScene(): Unit = {
        myStage.scene = new EndScene(this, controller).createScene()
    }
}