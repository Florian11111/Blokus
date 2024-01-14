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
import scala.xml._

class fileIoXmlImpl extends FileIOInterface {

    override def load(path: String): GameState = {

        val xml = XML.loadFile(path)

        val fieldNodes = (xml \\ "gameState" \ "field" \ "row")

        val newfield = new Field(fieldNodes.map { rowNode =>
            (rowNode \ "cell").map(_.text.toInt).toVector
        }.toVector)
        val currentPlayer = (xml \\ "gameState" \ "currentPlayer").text.toInt

        val playerAmount = (xml \\ "gameState" \\ "blockInventory" \ "playerAmount").text.toInt
        
        val inventoryNodes = (xml \\ "gameState" \\ "blockInventory" \ "inventory" \ "player")
        val inventories = inventoryNodes.map { playerNode =>
            (playerNode \ "block").map(_.text.toInt).toList
        }.toArray

        val firstBlocks = (xml \\ "gameState" \\ "blockInventory" \ "firstBlocks" \ "player").map(_.text.toBoolean).toList
        
        val posPositionsNodes = (xml \\ "gameState" \\ "blockInventory" \ "posPositions" \ "player")
        val posPositions = posPositionsNodes.map { playerNode =>
            (playerNode \ "block").map { blockNode =>
                val x = (blockNode \ "x").text.toInt
                val y = (blockNode \ "y").text.toInt
                (x, y)
            }.toList
        }.toArray

        var blockInventory = new BlockInventory(playerAmount, 
            inventories,
            firstBlocks.toArray,
            posPositions)

        new GameState(newfield, currentPlayer, blockInventory)
    }

    override def save(grid: GameState, path: String): Try[Unit] = {
        Try {
            val xml = GameStateToXml(grid)
            XML.save(path, xml)
        }
    }

    private def GameStateToXml(gameState: GameState): Elem = {
        <gameState>
            {fieldToXML(gameState.getField().getFieldVector)}
            <currentPlayer>{gameState.getCurrentPlayer()}</currentPlayer>
            {BlockInventoryToXml(gameState.getBlockInventory())}
        </gameState>
    }

    private def fieldToXML(field: Vector[Vector[Int]]): scala.xml.Node = {
        <field>
            {field.zipWithIndex.map { case (row, _) => rowToXML(row) }}
        </field>
    }

    private def rowToXML(row: Vector[Int]): scala.xml.Node = {
        <row>
            {row.zipWithIndex.map { case (cell, x) => <cell>{cell}</cell> }}
        </row>
    }

    private def BlockInventoryToXml(blockInventory: BlockInventoryInterface): Elem = {
        <blockInventory>
            <playerAmount>{blockInventory.getPlayerAmount()}</playerAmount>
            {inventoryToXml(blockInventory.getAllInventories())}
            <firstBlocks>{blockInventory.getAllFirstBlock().map(x => <player>{x}</player>)}</firstBlocks>
            {posPositionsToXml(blockInventory.getAllPosPositions())}
        </blockInventory>
    }

    private def posPositionsToXml(posPositions: Array[List[(Int, Int)]]): scala.xml.Node = {
        <posPositions>
            {posPositions.map { case (row) => posPositionsRowToXML(row) }}
        </posPositions>
    }

    private def posPositionsRowToXML(row: List[(Int, Int)]): scala.xml.Node = {
        <player>
            {row.zipWithIndex.map { case (cell, _) => <block>{<x>{cell(0)}</x><y>{cell(1)}</y>}</block> }}
        </player>
    }

    private def inventoryToXml(inventory: Array[List[Int]]): scala.xml.Node = {
        <inventory>
            {inventory.map { case (row) => inventoryRowToXML(row) }}
        </inventory>
    }

    private def inventoryRowToXML(row: List[Int]): scala.xml.Node = {
        <player>
            {row.zipWithIndex.map { case (cell, x) => <block>{cell}</block> }}
        </player>
    }
}

