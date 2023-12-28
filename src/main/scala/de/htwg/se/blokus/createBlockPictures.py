import math
from PIL import Image
from PIL import ImageDraw

block = [
    [(0, 0)],
    [(0, 0), (1, 0)],
    [(-1, 0), (0, 0), (1, 0)],
    [(0, 0), (1, 0), (0, 1)],
    [(-1, 0), (0, 0), (1, 0), (2, 0)],
    [(-1, 0), (-1, 1), (0, 0), (1, 0)],
    [(-1, 0), (0, 0), (1, 0), (0, 1)],
    [(0, 0), (1, 0), (0, 1), (1, 1)],
    [(0, 0), (-1, 0), (0, 1), (1, 1)],
    [(-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0)],
    [(-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1)],
    [(-1, 0), (0, 0), (1, 0), (0, 1), (1, 1)],
    [(-1, 0), (0, 0), (1, 0), (2, 0), (0, 1)],
    [(-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1)],
    [(-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1)],
    [(0, 0), (0, -1), (1, 0), (0, 1), (-1, 0)],
    [(-1, -1), (0, -1), (0, 0), (0, 1), (1, 1)],
    [(-1, -1), (0, -1), (0, 0), (1, 0), (1, 1)],
    [(-1, -1), (0, -1), (1, -1), (0, 0), (0, 1)],
    [(-1, -1), (0, -1), (0, 0), (1, 0), (0, 1)],
    [(-1, -1), (0, -1), (1, -1), (1, 0), (1, 1)]
]

def getSize(coords):
    x = [i[0] for i in coords]
    y = [i[1] for i in coords]
    return (int(max(x) + math.sqrt(min(x)**2)) + 1, int(max(y) + math.sqrt(min(y)**2)) + 1)

def createBlockPicture(coords, nummer):
    x, y = getSize(coords)
    coords = [(i[0] + abs(min([i[0] for i in coords])), i[1] + abs(min([i[1] for i in coords]))) for i in coords]
    arr = [[0 for i in range(x)] for j in range(y)]
    for i in coords:
        arr[i[1]][i[0]] = 1
    
    canvas = (x*30, y*30)

    im = Image.new('RGBA', canvas, (255, 255, 255, 0))
    draw = ImageDraw.Draw(im)
    for x, y in coords:
        draw.rectangle([x*30, y*30, x*30+30, y*30+30], fill=(0, 0, 0, 255))
        draw.rectangle([x*30+1, y*30+1, x*30+28, y*30+28], fill=(255, 255, 0, 255))
    # save image
    im.save(f"./yellow/block{nummer}.png")

for inx, x in enumerate(block):
    createBlockPicture(x, inx)