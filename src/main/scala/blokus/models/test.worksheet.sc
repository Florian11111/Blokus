

case class BlockType(baseForm: List[(Int, Int)])

object BlockType {
    val block1 = BlockType(List((0, 0)))
    val block2 = BlockType(List((0, 0), (1, 0)))
    val block3 = BlockType(List((-1, 0), (0, 0), (1, 0)))
  // Weitere Blocktypen ...

    def createBlock(blockType: BlockType, rotation: Int, mirrored: Boolean): List[(Int, Int)] = {
        var block = blockType.baseForm
        for (_ <- 0 until rotation) {
            block = block.map { case (x, y) => (y, -x) }
        }
        if (mirrored) {
            block = block.map { case (x, y) => (x, -y) }
        }
        block
    }
}


val blockType = BlockType.block1 // Wähle den gewünschten Blocktyp
val rotation = 1
val mirrored = false

val rotatedBlock = BlockType.createBlock(blockType, rotation, mirrored)

println(rotatedBlock)