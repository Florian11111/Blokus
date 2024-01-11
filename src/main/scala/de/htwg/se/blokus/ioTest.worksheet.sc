import de.htwg.se.blokus.models.fileIoImpl.fileIoXmlImpl
import de.htwg.se.blokus.models.fileIoImpl.fileIoJsonImpl
import de.htwg.se.blokus.models.{BlockInventoryInterface, FieldInterface, GameState}
import de.htwg.se.blokus.models.fieldImpl.Field
import de.htwg.se.blokus.models.BlockInventoryInterface.*
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory
import de.htwg.se.blokus.models.fieldImpl.Field

import de.htwg.se.blokus.models.GameState

var field = new Field(Vector(Vector(0, 0), Vector(0, 0)))
var blockInventory = new BlockInventory(2, initialCount = 2);
blockInventory.setAllPosPositions(Array(List((0, 0), (0, 1), (1, 0), (1, 1))))

field.getFieldVector
var gameState = new GameState(field, 0, blockInventory)

var fileIoJsonImpl = new fileIoXmlImpl()
fileIoJsonImpl.save(gameState, "test.xml")

gameState = fileIoJsonImpl.load("test.xml")

print(gameState.getBlockInventory())