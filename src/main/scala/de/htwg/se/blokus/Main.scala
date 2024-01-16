package de.htwg.se.blokus

import de.htwg.se.blokus.BlokusModule
import de.htwg.se.blokus.controller.GameController
import scala.concurrent.{Future, ExecutionContext, Await}
import scala.concurrent.duration.Duration

import de.htwg.se.blokus.view.Tui
import de.htwg.se.blokus.view.Gui

import com.google.inject.Guice

object Main {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new BlokusModule)
    val controller = injector.getInstance(classOf[GameController])
    val tui = new Tui(controller)

    tui.inputLoop()
  }
}
