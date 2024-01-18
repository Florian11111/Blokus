package de.htwg.se.blokus.models

import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.BlockInventoryInterface
/*  */
class GameState(private var field: FieldInterface,
                private var currentPlayer: Int,
                private var blockInventory: BlockInventoryInterface) {
    def getField(): FieldInterface = field
    def getCurrentPlayer(): Int = currentPlayer
    def getBlockInventory(): BlockInventoryInterface = blockInventory
}
