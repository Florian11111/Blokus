package blokus.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import javax.swing.text.html.BlockView

class BlockSpec extends AnyWordSpec with Matchers {

  "A Block" should {
    "correctly create the base forms" in {
      Block.baseForm0 shouldEqual List((0, 0))
      Block.baseForm1 shouldEqual List((0, 0), (1, 0))
      Block.baseForm2 shouldEqual List((-1, 0), (0, 0), (1, 0))
      Block.baseForm3 shouldEqual List((0, 0), (1, 0), (0, 1))
      Block.baseForm4 shouldEqual List((-1, 0), (0, 0), (1, 0), (2, 0))
      Block.baseForm5 shouldEqual List((-1, 0), (-1, 1), (0, 0), (1, 0))
      Block.baseForm6 shouldEqual List((-1, 0), (0, 0), (1, 0), (0, 1))
      Block.baseForm7 shouldEqual List((0, 0), (1, 0), (0, 1), (1, 1))
      Block.baseForm8 shouldEqual List((0, 0), (-1, 0), (0, 1), (1, 1))
      Block.baseForm9 shouldEqual List((-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0))
      Block.baseForm10 shouldEqual List((-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1))
      Block.baseForm11 shouldEqual List((-1, 0), (0, 0), (1, 0), (0, 1), (1, 1))
      Block.baseForm12 shouldEqual List((-1, 0), (0, 0), (1, 0), (2, 0), (0, 1))
      Block.baseForm13 shouldEqual List((-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1))
      Block.baseForm14 shouldEqual List((-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1))
      Block.baseForm15 shouldEqual List((0, 0), (0, -1), (1, 0), (0, 1), (-1, 0))
      Block.baseForm16 shouldEqual List((-1, -1), (0, -1), (0, 0), (0, 1), (1, 1))
      Block.baseForm17 shouldEqual List((-1, -1), (0, -1), (0, 0), (1, 0), (1, 1))
      Block.baseForm18 shouldEqual List((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1))
      Block.baseForm19 shouldEqual List((-1, -1), (0, -1), (1, -1), (0, 0), (0, 1))
    }

    "correctly create a block with rotation and mirroring" in {
      val blockType = 1
      val rotation = 2
      val mirrored = true

      val block = Block.createBlock(blockType, rotation, mirrored)
      block shouldEqual List((0, 0), (0, -1), (-1, -1))
    }

    "correctly create a block without rotation and mirroring" in {
      val blockType = 5
      val rotation = 0
      val mirrored = false

      val block = Block.createBlock(blockType, rotation, mirrored)
      block shouldEqual List((-1, 0), (-1, 1), (0, 0), (1, 0))
    }

    "throw an exception when creating a block with an invalid blockType" in {
      val blockType = 20
      val rotation = 0
      val mirrored = false

      an[IndexOutOfBoundsException] should be thrownBy {
        Block.createBlock(blockType, rotation, mirrored)
      }
    }
  }
}
