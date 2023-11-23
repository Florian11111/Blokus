import numpy as np

x = np.zeros((10, 10), dtype=int)
block_coordinates = [[5, 3], [5, 4], [6, 4]]
ecken = [(1, 1), (1, -1), (-1, 1), (-1, -1)]

corners = []
for block in block_coordinates:
    for ecke in ecken:
        corners.append()

for coord in block_coordinates:
    x[coord[0]][coord[1]] = 1

print(x)
