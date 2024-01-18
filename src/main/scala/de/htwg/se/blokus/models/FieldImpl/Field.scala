package de.htwg.se.blokus.models.fieldImpl

import de.htwg.se.blokus.models.FieldInterface
import de.htwg.se.blokus.models.HoverBlockInterface
import de.htwg.se.blokus.models.Block
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock

class Field(private val fieldVector: Vector[Vector[Int]]) extends FieldInterface {
    val width: Int = fieldVector.headOption.map(_.size).getOrElse(0)
    val height: Int = fieldVector.size

    def getFieldVector: Vector[Vector[Int]] = fieldVector

    def setFieldVector(newFieldVector: Vector[Vector[Int]]): Field = new Field(newFieldVector)

    def isCorner(x: Int, y: Int): Boolean = {
        (x == 0 && y == 0) || (x == width - 1 && y == 0) || (x == 0 && y == height - 1) || (x == width - 1 && y == height - 1)
    }

    def getBlockAmount(player: Int): Int = {
        fieldVector.flatten.count(_ == player)
    }

    def isInBounds(x: Int, y : Int): Boolean = x >= 0 && x < width && y >= 0 && y < height

    def isInsideField(hoverBlock: HoverBlockInterface): Boolean = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        block.baseForm.forall { case (dx, dy) =>
            val newX = hoverBlock.getX + dx
            val newY = hoverBlock.getY + dy

            newY >= 0 && newY < height && newX >= 0 && newX < width
        }
    }

    def posPositionsCheck(hoverBlock: HoverBlockInterface): Boolean = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        val x = hoverBlock.getX
        val y = hoverBlock.getY
        block.baseForm.exists { blockcords =>
            val hoverBlock2 = hoverBlock.newInstance(x + blockcords._1, y + blockcords._2, hoverBlock.getPlayer, hoverBlock.getBlockType,
                hoverBlock.getPlayer, hoverBlock.getRotation, hoverBlock.getMirrored)
            isGameRuleConfirm(hoverBlock)
        }
    }

    def isNoBlocksOnTop(hoverBlock: HoverBlockInterface): Boolean = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        block.baseForm.forall { case (dx, dy) =>
            val newX = hoverBlock.getX + dx
            val newY = hoverBlock.getY + dy
            isInBounds(newX, newY) && fieldVector(newY)(newX) == -1
        }
    }

    def isInCorner(hoverBlock: HoverBlockInterface): Boolean = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        isInsideField(hoverBlock) &&
        isNoBlocksOnTop(hoverBlock) &&
        block.baseForm.exists {case (dx, dy) => isCorner(hoverBlock.getX + dx, hoverBlock.getY + dy)}
    }

    def isGameRuleConfirm(hoverBlock: HoverBlockInterface): Boolean = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        isInsideField(hoverBlock) && isNoBlocksOnTop(hoverBlock) &&
            block.edges.forall { case (dx, dy) => !isInBounds(hoverBlock.getX + dx, hoverBlock.getY + dy) ||
            isInBounds(hoverBlock.getX + dx, hoverBlock.getY + dy) &&
            fieldVector(hoverBlock.getY + dy)(hoverBlock.getX + dx) != hoverBlock.getPlayer &&
            block.corners.exists { case (dx, dy) => isInBounds(hoverBlock.getX + dx, hoverBlock.getY + dy) &&
            fieldVector(hoverBlock.getY + dy)(hoverBlock.getX + dx) == hoverBlock.getPlayer}
        }
    }

    def placeBlock(hoverBlock: HoverBlockInterface, firstPlace: Boolean): Field = {
        val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
        if (isGameRuleConfirm(hoverBlock) || (firstPlace && isInCorner(hoverBlock))) {
            val updatedField = fieldVector.zipWithIndex.map { case (row, rowIndex) =>
                row.zipWithIndex.map { case (_, colIndex) =>
                if (block.baseForm.contains((colIndex - hoverBlock.getX, rowIndex - hoverBlock.getY)))
                    hoverBlock.getPlayer
                else
                    fieldVector(rowIndex)(colIndex)
                }
            }
            new Field(updatedField)
        } else {
            throw new IllegalArgumentException("Invalid position for block placement.")
        }
    }

    def copy(): Field = {
        val copiedVector = fieldVector.map(_.toVector).toVector
        new Field(copiedVector)
    }
}

object Field {
    private var instance: Option[Field] = None

    def getInstance(width: Int, height: Int): Field = {
        instance.getOrElse {
        val initialFieldVector = Vector.fill(height, width)(-1)
        val fieldInstance = new Field(initialFieldVector)
        instance = Some(fieldInstance)
        fieldInstance
        }
    }

    def apply(width: Int, height: Int): Field = new Field(Vector.fill(height, width)(-1))
}