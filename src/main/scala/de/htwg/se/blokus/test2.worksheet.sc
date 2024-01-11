import de.htwg.se.blokus.models.{FieldInterface, HoverBlockInterface}

var field = FieldInterface.getInstance(5, 5)
var hoverBlock = HoverBlockInterface.getInstance(0, 0, 0, 0, 0, false)
var hoverBlock2 = HoverBlockInterface.getInstance(1, 1, 0, 0, 0, false)

hoverBlock.setPlayer(0)
hoverBlock2.setPlayer(0)

field = field.placeBlock(hoverBlock, true)
//field = field.placeBlock(hoverBlock2, false)

field.isGameRuleConfirm(hoverBlock2)


