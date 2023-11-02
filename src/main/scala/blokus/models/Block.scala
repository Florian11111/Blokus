package blokus.models

case class BlockType(baseForm: List[(Int, Int)])

object BlockType {
    val blockA = BlockType(List((0, 0), (0, -1), (0, 1), (1, 1)))
    val blockB = BlockType(List((0, 0), (0, -1), (1, 0), (1, -1)))
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


def main(args: Array[String]): Unit = {
    val blockType = BlockType.blockA // Wähle den gewünschten Blocktyp
    val rotation = 1
    val mirrored = true

    val rotatedBlock = BlockType.createBlock(blockType, rotation, mirrored)

    println(rotatedBlock)
}
