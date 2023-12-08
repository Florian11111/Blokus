import numpy as np
'''
(0, 0)
(0, 0), (1, 0)
(-1, 0), (0, 0), (1, 0)
(0, 0), (1, 0), (0, 1)
(-1, 0), (0, 0), (1, 0) (2, 0)
(-1, 0), (-1, 1), (0, 0), (1, 0)
(-1, 0), (0, 0), (1, 0), (0, 1)
(0, 0), (1, 0), (0, 1), (1, 1)
(0, 0), (-1, 0), (0, 1), (1, 1)
(-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0)
(-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1)
(-1, 0), (0, 0), (1, 0), (0, 1), (1, 1)
(-1, 0), (0, 0), (1, 0), (2, 0), (0, 1)
(-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1)
(-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1)
(0, 0), (0, -1), (1, 0), (0, 1), (-1, 0)
(-1, -1), (0, -1), (0, 0), (0, 1), (1, 1)
(-1, -1), (0, -1), (0, 0), (1, 0), (1, 1)
(-1, -1), (0, -1), (1, -1), (0, 0), (0, 1)
(-1, -1), (0, -1), (0, 0), (1, 0), (0, 1)
(-1, -1), (0, -1), (1, -1), (1, 0), (1, 1)

'''

liste = [(-1, -1), (0, -1), (1, -1), (0, 0), (0, 1)]
ecken = []

# Erstelle die Eckenliste
for x, y in liste:
    for xCord in [-1, 1]:
        for yCord in [-1, 1]:
            ecken.append((x + xCord, y + yCord))

print(ecken, "\n")

# Entferne die Koordinaten der Ecken aus der Liste
ecken = list(set(ecken))  # Um Duplikate zu entfernen
for coord in ecken:
    if coord in liste:
        ecken.remove(coord)

print(ecken, "\n")

# Entferne die Ecken, die direkt an einen Block angrenzen
benachbarte_ecken = [(0, 1), (0, -1), (1, 0), (-1, 0)]
for x, y in liste:
    for coord in benachbarte_ecken:
        nachbar = x + coord[0], y + coord[1] 
        if (nachbar) in ecken:
            ecken.remove(nachbar)

print(ecken)

