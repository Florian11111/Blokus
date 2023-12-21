package blokus

import blokus.controller.GameController
import scala.concurrent.{Future, ExecutionContext, Await}
import scala.concurrent.duration.Duration

import blokus.view.Tui
import blokus.view.Gui

import com.google.inject.Guice

object Main {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new BlokusModule)
    val controller = injector.getInstance(classOf[GameController])
    val tui = new Tui(controller)
    val gui = new Gui(controller, 480, 650)
  
    // Hier wird die GUI in einem separaten Thread gestartet
    implicit val context: ExecutionContext = scala.concurrent.ExecutionContext.global
    val guiFuture: Future[Unit] = Future {
      gui.main(Array[String]())
    }

    // Starte die TUI
    tui.inputLoop()
    // Warten, bis die GUI initialisiert ist, bevor die TUI gestartet wird
    Await.result(guiFuture, Duration.Inf)

  }
}
