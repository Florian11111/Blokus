def matrix_to_speech(matrix, block):
    if len(matrix) != 5 or any(len(row) != 5 for row in matrix):
        raise ValueError("Die Matrix sollte eine 5x5-Matrix sein.")

    block_shape = block.getBlock()
    # Block in die Mitte (2,2) der Matrix einfügen
    for i in range(len(block_shape)):
        x, y = block_shape[i]
        matrix[2 + x][2 + y] = "#"

    speech = "Hier ist die 5x5-Matrix:\n"
    for row in matrix:
        speech += " | ".join(str(element) for element in row) + "\n"
        speech += "------------------\n"

    print(speech)

# Beispielaufruf
def getMatrix():
    return [
        ["-", "-", "-", "-", "-"],
        ["-", "-", "-", "-", "-"],
        ["-", "-", "-", "-", "-"],
        ["-", "-", "-", "-", "-"],
        ["-", "-", "-", "-", "-"]
    ]

class Block:
    # Basisform des Blocks
    baseForm = [(0, 0), (0, -1), (0, 1), (1, 1)]

    def __init__(self):
        self.rotation = 0
        self.mirrored = False

    def rotate(self, times=1):
        self.rotation = (self.rotation + times) % 4

    def mirror(self):
        self.mirrored = not self.mirrored

    def getBlock(self):
        block = self.baseForm[:]
        for _ in range(self.rotation):
            block = [(y, -x) for x, y in block]

        if self.mirrored:
            block = [(x, -y) for x, y in block]

        return block

# Beispiel:
myBlock = Block()
while(True):
    matrix_to_speech(getMatrix(), myBlock)
    x = input("r | s | e\n")
    if (x == "e"):
        break
    elif (x == "r"):
        myBlock.rotate(1)
    elif (x == "s"):
        myBlock.mirror()
    matrix_to_speech(getMatrix(), myBlock)

matrix_to_speech(getMatrix(), myBlock)
myBlock.rotate(2)  # Drehung um 180 Grad
matrix_to_speech(getMatrix(), myBlock)
myBlock.mirror()   # Spiegelung)
rotatedBlock = myBlock.getBlock()

print(rotatedBlock)
