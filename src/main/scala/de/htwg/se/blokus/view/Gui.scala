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
import scalafx.scene.image.Image
class Gui(controller: GameController, windowsWidth: Int, windowsHeight: Int) extends JFXApp3 with Observer[Event] {
    
    private var myStage: Stage = _
    private var gameSceneSwitched: Boolean = false
    private var nameList = List[String]()

    controller.addObserver(this)

    def setNames(names: List[String]): List[String] = {
        val oldNameList = nameList
        nameList = names
        oldNameList
    }

    def update(event: Event): Unit = {
        event match {
            case StartGameEvent => 
                Platform.runLater {
                    if (!gameSceneSwitched) {
                        setNames(List("Player1", "Player2", "Player3", "Player4"))
                        switchToGameScene(nameList, false, true)
                        gameSceneSwitched = true
                    }
                }
            case EndGameEvent => {
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
            val file = new java.io.File("./src/main/resources/gameIcon.png")
            icons.add(new Image(file.toURI().toString()))
            title.value = "Blokus"
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

    def switchToGameScene(names: List[String], highPerformentsMode: Boolean, info: Boolean): Unit = {
        setNames(names)
        gameSceneSwitched = true
        myStage.scene = new GameScene(this, controller, windowsWidth, windowsHeight, names, highPerformentsMode, info).createScene()
    }

    def switchToEndScene(): Unit = {
        myStage.scene = new EndScene(this, controller, nameList).createScene()
    }
}