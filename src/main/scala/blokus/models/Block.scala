package blokus.models

case class Block(baseForm: List[(Int, Int)])

object Block {
    val baseForm0 = List((0, 0))
    val baseForm1 = List((0, 0), (1, 0))
    val baseForm2 = List((-1, 0), (0, 0), (1, 0))
    val baseForm3 = List((0, 0), (1, 0), (0, 1))
    val baseForm4 = List((-1, 0), (0, 0), (1, 0), (2, 0))
    val baseForm5 = List((-1, 0), (-1, 1), (0, 0), (1, 0))
    val baseForm6 = List((-1, 0), (0, 0), (1, 0), (0, 1))
    val baseForm7 = List((0, 0), (1, 0), (0, 1), (1, 1))
    val baseForm8 = List((0, 0), (-1, 0), (0, 1), (1, 1))
    val baseForm9 = List((-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0))
    val baseForm10 = List((-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1))
    val baseForm11 = List((-1, 0), (0, 0), (1, 0), (0, 1), (1, 1))
    val baseForm12 = List((-1, 0), (0, 0), (1, 0), (2, 0), (0, 1))
    val baseForm13 = List((-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1))
    val baseForm14 = List((-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1))
    val baseForm15 = List((0, 0), (0, -1), (1, 0), (0, 1), (-1, 0))
    val baseForm16 = List((-1, -1), (0, -1), (0, 0), (0, 1), (1, 1))
    val baseForm17 = List((-1, -1), (0, -1), (0, 0), (1, 0), (1, 1))
    val baseForm18 = List((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1))
    val baseForm19 = List((-1, -1), (0, -1), (1, -1), (0, 0), (0, 1))

    val blockBaseForms: Array[List[(Int, Int)]] = Array(
        baseForm0, baseForm1, baseForm2, baseForm3, baseForm4,
        baseForm5, baseForm6, baseForm7, baseForm8, baseForm9,
        baseForm10, baseForm11, baseForm12, baseForm13, baseForm14,
        baseForm15, baseForm16, baseForm17, baseForm18, baseForm19
    )
    
    trait Strategy {
        def createBlock(baseForm: List[(Int, Int)], rotation: Int, mirrored: Boolean): List[(Int, Int)]
    }

    object MirroredStrategy extends Strategy {
        override def createBlock(baseForm: List[(Int, Int)], rotation: Int, mirrored: Boolean): List[(Int, Int)] = {
        var block = baseForm.map { case (x, y) => (x, -y) }

        for (_ <- 0 until rotation) {
            block = block.map { case (x, y) => (-y, x) }
        }
        block
        }
    }

    object NotMirroredStrategy extends Strategy {
        override def createBlock(baseForm: List[(Int, Int)], rotation: Int, mirrored: Boolean): List[(Int, Int)] = {
        var block = baseForm

        for (_ <- 0 until rotation) {
            block = block.map { case (x, y) => (-y, x) }
        }
        block
        } 
    }

    def createBlock(blockType: Int, rotation: Int, mirrored: Boolean): List[(Int, Int)] = {
        val baseForm = blockBaseForms(blockType)
        val strategy: Strategy = if (mirrored) MirroredStrategy else NotMirroredStrategy

        strategy.createBlock(baseForm, rotation, mirrored)
    }
}
