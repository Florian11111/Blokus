package de.htwg.se.blokus.models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BlockSpec extends AnyWordSpec with Matchers {
  "Block" should {
    "create a Block with the correct baseForm, corners, and edges" in {
      val blockType = 0
      val rotation = 0
      val mirrored = false

      val block = Block.createBlock(blockType, rotation, mirrored)

      val expectedBaseForm = List((0, 0))
      val expectedCorners = List((1, 1), (1, -1), (-1, 1), (-1, -1))
      val expectedEdges = List((-1, 0), (1, 0), (0, -1), (0, 1))

      block.baseForm shouldEqual expectedBaseForm
      block.corners shouldEqual expectedCorners
      block.edges shouldEqual expectedEdges
    }

    "create a Block with mirrored coordinates when mirrored is true" in {
      val blockType = 1
      val rotation = 0
      val mirrored = true

      val block = Block.createBlock(blockType, rotation, mirrored)

      val expectedBaseForm = List((0, 0), (1, 0)).map { case (x, y) => (x, -y) }
      val expectedCorners =List((2, 1), (2, -1), (-1, 1), (-1, -1))
      val expectedEdges = List((2, 0), (1, -1), (1, 1), (-1, 0), (0, -1), (0, 1))

      block.baseForm shouldEqual expectedBaseForm
      block.corners shouldEqual expectedCorners
      block.edges shouldEqual expectedEdges
    }

    "rotate the Block correctly" in {
      val blockType = 2
      val rotation = 1
      val mirrored = false

      val block = Block.createBlock(blockType, rotation, mirrored)

      val expectedBaseForm = List((-1, 0), (0, 0), (1, 0)).map { case (x, y) => (-y, x) }
      val expectedCorners = List((1, 2), (-1, 2), (1, -2), (-1, -2))
      val expectedEdges = List((-1, 1), (1, 1), (0, 2), (-1, 0), (1, 0), (-1, -1), (1, -1), (0, -2))

      block.baseForm shouldEqual expectedBaseForm
      block.corners shouldEqual expectedCorners
      block.edges shouldEqual expectedEdges
    }

    "correctly calculate corners and edges for a given Block" in {
      val blockType = 1
      val rotation = 0
      val mirrored = false

      val block = Block.createBlock(blockType, rotation, mirrored)

      var (expectedCorners, expectedEdges) = Block.eckenUndKanten(block.baseForm)
      expectedCorners = expectedCorners.filterNot(block.baseForm.contains)
      expectedEdges = expectedEdges.filterNot(block.baseForm.contains)

      block.corners shouldEqual expectedCorners
      expectedCorners shouldBe a[List[(Int, Int)]]
      block.edges shouldEqual expectedEdges
      expectedEdges shouldBe a[List[(Int, Int)]]
    }
  }
}