package blokus

import com.google.inject.AbstractModule
import com.google.inject.Guice
import blokus.controller.*
import blokus.models.{BlockInventoryInterface, FieldInterface, HoverBlockInterface}
import blokus.models.blockInvImpl.BlockInventory
import blokus.models.FieldImpl.Field
import blokus.models.hoverBlockImpl.HoverBlock
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global
import controller.GameController

class BlokusModule extends AbstractModule {
    private val gameSize: (Int, Int) = (20, 20)
    private val playerAmount: Int = 2
    override def configure(): Unit = {
        bind(classOf[GameController]).toInstance(new Controller(playerAmount, 0, gameSize._1, gameSize._2))

        bind(classOf[FieldInterface]).toInstance(Field.getInstance(gameSize._1, gameSize._2))

        bind(classOf[BlockInventoryInterface]).toInstance(BlockInventory.getInstance(playerAmount, 1))
        bind(classOf[HoverBlockInterface]).toInstance(HoverBlock.getInstance(playerAmount, 2))
    }
}
