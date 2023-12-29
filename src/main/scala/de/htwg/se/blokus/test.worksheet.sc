import org.checkerframework.checker.units.qual.h
import de.htwg.se.blokus.models.hoverBlockImpl.HoverBlock
import de.htwg.se.blokus.models.blockInvImpl.BlockInventory
import de.htwg.se.blokus.models.fieldImpl.Field
import de.htwg.se.blokus.models.Block

val playerAmount = 4
var hoverBlock = new HoverBlock(5, 5, playerAmount, 0, 0, false)
var blockInventory = new BlockInventory(playerAmount, 1)
var field = new Field(Vector.fill(20, 20)(-1))

// replace -1 with "#"
def prityPrintField(field: Field, corner: List[(Int, Int)]): Unit = {
    val fieldVector = field.getFieldVector
    for (y <- 0 until field.height) {
        for (x <- 0 until field.width) {
            corner.find(e => e._1 == x && e._2 == y) match {
                case Some(_) => print("X ")
                case None => if (fieldVector(y)(x) == -1) {
                    print("# ")
                } else {
                    print(fieldVector(y)(x) + " ")
                }
            }       
        }
        println()
    }
}

def isValidCorner(x: Int, y: Int): Boolean = {
    val neighbors = List((x-1, y), (x+1, y), (x, y-1), (x, y+1))
    val fieldtemp = field.getFieldVector.transpose

    var allValid = true
    for ((nx, ny) <- neighbors) {
        if (!(nx < 0 || ny < 0 || nx >= field.width || ny >= field.height ||
            (nx >= 0 && ny >= 0 && nx < field.width && ny < field.height && fieldtemp(nx)(ny) != hoverBlock.getPlayer))) {
            allValid = false
        }
    }
    allValid && field.isInBounds(x, y) && fieldtemp(x)(y) == -1
}

def addCornerToInventory(player: Int): List[(Int, Int)] = {
    val block = Block.createBlock(hoverBlock.getBlockType, hoverBlock.getRotation, hoverBlock.getMirrored)
    val blocks = blockInventory.getBlocks(player)
    var corners2 = blockInventory.getPosPositions(player)
    corners2 = (corners2 ++ block.corners.map(e => (e._1 + hoverBlock.getX, e._2 + hoverBlock.getY)))
    corners2 = corners2.filter { ecke =>
        isValidCorner(ecke._1, ecke._2)
    }
    corners2 = corners2.filter { ecke =>
        if (ecke._1 == 4 && ecke._2 == 2) {
            println("halli Hallo")
        } else {
            print("x: " + ecke._1 + " y: " + ecke._2 + " ")
        }
        blocks.zipWithIndex.exists { case (blockamount, block) =>
            if (blockamount > 0) {
                (0 to 3).exists { i =>
                    List(false, true).exists { j =>
                        val tempHoverBlock =  HoverBlock.createInstance(ecke._1, ecke._2, playerAmount, block, i, j)
                        val ergebenis = field.cornerCheck(tempHoverBlock)
                        if (ergebenis) {
                            println("block: " + block + " rotation: " + i + " mirrored: " + j)
                        }
                        ergebenis
                    }
                } 
            } else {
                false
            }
        }
    }
    blockInventory.setPosPositions(player, corners2)
    corners2
}


hoverBlock.setX(19)
hoverBlock.setY(1)
hoverBlock.setPlayer(0)
hoverBlock.setBlockType(11)
hoverBlock.setMirrored(false)
hoverBlock.setRotation(1)
field = field.placeBlock(hoverBlock, true)

addCornerToInventory(0)

hoverBlock.setX(2)
hoverBlock.setY(0)
hoverBlock.setPlayer(1)
hoverBlock.setBlockType(9)
hoverBlock.setMirrored(false)
hoverBlock.setRotation(0)
field = field.placeBlock(hoverBlock, true)

addCornerToInventory(1)
prityPrintField(field, blockInventory.getPosPositions(1))

hoverBlock.setX(16)
hoverBlock.setY(1)
hoverBlock.setPlayer(0)
hoverBlock.setBlockType(18)
hoverBlock.setMirrored(false)
hoverBlock.setRotation(0)
field = field.placeBlock(hoverBlock, true)

addCornerToInventory(0)

hoverBlock.setX(7)
hoverBlock.setY(1)
hoverBlock.setPlayer(1)
hoverBlock.setBlockType(9)
hoverBlock.setMirrored(false)
hoverBlock.setRotation(0)
field = field.placeBlock(hoverBlock, true)


// test -------

val tempHoverBlock =  HoverBlock.createInstance(4, 2, playerAmount, 0, 0, false)
tempHoverBlock.setPlayer(1)
val ergebenis = field.cornerCheck(tempHoverBlock)
addCornerToInventory(1)
prityPrintField(field, blockInventory.getPosPositions(1))

hoverBlock.setX(14)
hoverBlock.setY(1)
hoverBlock.setPlayer(0)
hoverBlock.setBlockType(3)
hoverBlock.setMirrored(false)
hoverBlock.setRotation(1)
field = field.placeBlock(hoverBlock, true)

addCornerToInventory(0)

hoverBlock.setX(12)
hoverBlock.setY(2)
hoverBlock.setPlayer(1)
hoverBlock.setBlockType(14)
hoverBlock.setMirrored(false)
hoverBlock.setRotation(0)
field = field.placeBlock(hoverBlock, true)

addCornerToInventory(1)

prityPrintField(field, blockInventory.getPosPositions(1))

