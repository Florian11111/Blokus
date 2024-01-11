package de.htwg.se.blokus.models.fileIoImpl

import de.htwg.se.blokus.models.FileIOInterface
import de.htwg.se.blokus.models.GameState
import scala.util.Try
import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.BlockInventoryInterface
import scala.io.Source
import de.htwg.se.blokus.models.fieldImpl.Field
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory
import java.io._
import play.api.libs.json._

class fileIoJsonImpl extends FileIOInterface {

    override def load(path: String): GameState = {
        val source = Source.fromFile(path)
        val json = Json.parse(source.mkString)
        source.close()

        val newField = new Field(jsonToField((json \ "field").as[JsArray]).getFieldVector)
        val newCurrentPlayer = (json \ "currentPlayer").as[Int]
        val newPlayerAmount = (json \ "blockInventory" \ "playerAmount").as[Int]
        
        var newBlockInverntory = new BlockInventory(newPlayerAmount, -1)
        newBlockInverntory.setAllFirstBlock((json \ "blockInventory" \ "firstBlocks").as[Array[Boolean]])
        newBlockInverntory.setAllInventories((json \ "blockInventory" \ "inventory").as[Array[List[Int]]])
        newBlockInverntory.setAllPosPositions((json \ "blockInventory" \ "posPositions").as[Array[List[(Int, Int)]]])
        GameState(newField, newCurrentPlayer, newBlockInverntory)
    }

    def jsonToField(json: JsArray): FieldInterface = {
        val fieldVector = json.value.map {
            case arr: JsArray => arr.value.collect { case JsNumber(num) => num.toInt }.toVector
            case _ => Vector.empty[Int]
        }.toVector
        new Field(fieldVector)
    }

    override def save(grid: GameState, path: String): Try[Unit] = {

        Try {
            val pw = new PrintWriter(new File(path))
            pw.write(Json.prettyPrint(GameStateToJson(grid)))
            pw.close
        }
    }

    def GameStateToJson(gameState: GameState): JsObject = {
        Json.obj(
            "field" -> gameState.getField().getFieldVector,
            "currentPlayer" -> JsNumber(gameState.getCurrentPlayer()),
            "blockInventory" -> BlockInventoryToJson(gameState.getBlockInventory())
        )
    }

    def FieldToJson(field: FieldInterface): JsObject = {
        Json.obj(
            "field" -> field.getFieldVector
        )
    }

    def BlockInventoryToJson(blockInventory: BlockInventoryInterface): JsObject = {
        val playerAmount = blockInventory.getPlayerAmount()
        val inventory = blockInventory.getAllInventories()
        val firstBlocks = blockInventory.getAllFirstBlock()
        val posPositions = blockInventory.getAllPosPositions()

        Json.obj(
            "playerAmount" -> playerAmount,
            "inventory" -> inventory,
            "firstBlocks" -> firstBlocks,
            "posPositions" -> posPositions
        )
    }
}
