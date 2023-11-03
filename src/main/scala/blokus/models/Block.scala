package blokus.models

case class BlockType(baseForm: List[(Int, Int)])

object BlockType {
    val block1 = BlockType(List((0, 0)))
    val block2 = BlockType(List((0, 0), (1, 0)))
    val block3 = BlockType(List((-1, 0), (0, 0), (1, 0)))
    val block4 = BlockType(List((0, 0), (1, 0), (0, 1)))
    val block5 = BlockType(List((-1, 0), (0, 0), (1, 0), (2, 0)))
    val block6 = BlockType(List((-1, 0), (-1, 1), (0, 0), (1, 0)))
    val block7 = BlockType(List((-1, 0), (0, 0), (1, 0), (0, 1)))
    val block8 = BlockType(List((0, 0), (1, 0), (0, 1), (1, 1)))
    val block9 = BlockType(List((0, 0), (-1, 0), (0, 1), (1, 1)))
    val block10 = BlockType(List((-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0)))
    val block11 = BlockType(List((-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1)))
    val block12 = BlockType(List((-1, 0), (0, 0), (1, 0), (0, 1), (1, 1)))
    val block13 = BlockType(List((-1, 0), (0, 0), (1, 0), (2, 0), (0, 1)))
    val block14 = BlockType(List((-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1)))
    val block15 = BlockType(List((-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1)))
    val block16 = BlockType(List((0, 0), (0, -1), (1, 0), (0, 1), (-1, 0)))
    val block17 = BlockType(List((-1, -1), (0, -1), (0, 0), (0, 1), (1, 1)))
    val block18 = BlockType(List((-1, -1), (0, -1), (0, 0), (1, 0), (1, 1)))
    val block19 = BlockType(List((-1, -1), (0, -1), (1, -1), (0, 0), (0, 1)))
    val block20 = BlockType(List((-1, -1), (0, -1), (0, 0), (1, 0), (0, 1)))
    val block21 = BlockType(List((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1)))


    def createBlock(blockType: BlockType, rotation: Int, mirrored: Boolean): List[(Int, Int)] = {
        var block = blockType.baseForm

        if (mirrored) {
            block = block.map { case (x, y) => (x, -y) }
        }
        for (_ <- 0 until rotation) {
            block = block.map { case (x, y) => (-y, x) }
        }
        block
    } 
}


def main(args: Array[String]): Unit = {
    val blockType = BlockType.block6 // Wähle den gewünschten Blocktyp
    val rotation = 1
    val mirrored = true

    val rotatedBlock = BlockType.createBlock(blockType, rotation, mirrored)

    println(rotatedBlock)
}
