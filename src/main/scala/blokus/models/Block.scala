package blokus.models

case class Block(baseForm: List[(Int, Int)], corners: List[(Int, Int)], edges: List[(Int, Int)])

object Block {
    val blockBaseForms: Array[List[(Int, Int)]] = Array(
        List((0, 0)), List((0, 0), (1, 0)), List((-1, 0), (0, 0), (1, 0)),
        List((0, 0), (1, 0), (0, 1)), List((-1, 0), (0, 0), (1, 0), (2, 0)),
        List((-1, 0), (-1, 1), (0, 0), (1, 0)), List((-1, 0), (0, 0), (1, 0), (0, 1)),
        List((0, 0), (1, 0), (0, 1), (1, 1)), List((0, 0), (-1, 0), (0, 1), (1, 1)),
        List((-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0)), List((-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1)),
        List((-1, 0), (0, 0), (1, 0), (0, 1), (1, 1)), List((-1, 0), (0, 0), (1, 0), (2, 0), (0, 1)),
        List((-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1)), List((-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1)),
        List((0, 0), (0, -1), (1, 0), (0, 1), (-1, 0)), List((-1, -1), (0, -1), (0, 0), (0, 1), (1, 1)),
        List((-1, -1), (0, -1), (0, 0), (1, 0), (1, 1)), List((-1, -1), (0, -1), (1, -1), (0, 0), (0, 1)),
        List((-1, -1), (0, -1), (0, 0), (1, 0), (0, 1)), List((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1))
    )

    def createBlock(blockType: Int, rotation: Int, mirrored: Boolean): Block = {
        val baseForm = blockBaseForms(blockType)
        var block = baseForm

        if (mirrored) {
        block = block.map { case (x, y) => (x, -y) }
        }
        for (_ <- 0 until rotation) {
        block = block.map { case (x, y) => (-y, x) }
        }

        val ecken = block
        .flatMap { case (x, y) => List((x - 1, y - 1), (x - 1, y + 1), (x + 1, y - 1), (x + 1, y + 1)) }
        .distinct

        val benachbarteEcken = List((0, 1), (0, -1), (1, 0), (-1, 0))
        val kanten = block
        .flatMap { case (x, y) => benachbarteEcken.map { case (dx, dy) => (x + dx, y + dy) } }
        .distinct

        val corners = ecken.filterNot(block.contains)
        val edges = kanten.filterNot(block.contains)

        Block(block, corners, edges)
    }
}
