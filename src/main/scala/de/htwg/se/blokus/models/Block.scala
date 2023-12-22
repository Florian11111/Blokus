package de.htwg.se.blokus.models

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

        val (ecken, kanten) = eckenUndKanten(block)
        val corners = ecken.filterNot(block.contains)
        val edges = kanten.filterNot(block.contains)
        Block(block, corners, edges)
    }

    def eckenUndKanten(liste: List[(Int, Int)]): (List[(Int, Int)], List[(Int, Int)]) = {
        var ecken = List[(Int, Int)]()
        var kanten = List[(Int, Int)]()

        // Erstelle die Eckenliste
        for ((x, y) <- liste) {
            for (xCord <- List(-1, 1)) {
            for (yCord <- List(-1, 1)) {
                ecken = (x + xCord, y + yCord) :: ecken
            }
            }
        }

        ecken = ecken.distinct.filterNot(liste.contains)

        val benachbarteEcken = List((0, 1), (0, -1), (1, 0), (-1, 0))
        for ((x, y) <- liste) {
            for (coord <- benachbarteEcken) {
                val nachbar = (x + coord._1, y + coord._2)
                kanten = nachbar :: kanten
                if (ecken.contains(nachbar)) {
                    ecken = ecken.filterNot(_ == nachbar)
                }
            }
        }

        (ecken.distinct, kanten.distinct)
    }
}
