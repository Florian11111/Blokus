package de.htwg.se.blokus

import com.google.inject.AbstractModule
import com.google.inject.Guice
import de.htwg.se.blokus.controller.*
import de.htwg.se.blokus.models.{BlockInventoryInterface, HoverBlockInterface}
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory
import de.htwg.se.blokus.models.FieldImpl.Field
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global
import de.htwg.se.blokus.controller.GameController
import de.htwg.se.blokus.controller.controllerInvImpl.Controller

class BlokusModule extends AbstractModule {
    override def configure(): Unit = {
        bind(classOf[GameController]).toInstance(
            new Controller()
        )
    }
}
