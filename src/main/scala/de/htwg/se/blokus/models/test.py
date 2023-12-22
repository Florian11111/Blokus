import numpy as np

bloecke = [(0, 0)], [(0, 0), (1, 0)], [(-1, 0), (0, 0), (1, 0)], [(0, 0), (1, 0), (0, 1)],  [(-1, 0), (0, 0), (1, 0), (2, 0)], [(-1, 0), (-1, 1), (0, 0), (1, 0)],  [(-1, 0), (0, 0), (1, 0), (0, 1)], [(0, 0), (1, 0), (0, 1), (1, 1)],  [(0, 0), (-1, 0), (0, 1), (1, 1)], [(-2, 0), (-1, 0), (0, 0), (1, 0), (2, 0)],  [(-1, 1), (-1, 0), (0, 0), (1, 0), (1, 1)], [(-1, 0), (0, 0), (1, 0), (0, 1), (1, 1)],  [(-1, 0), (0, 0), (1, 0), (2, 0), (0, 1)], [(-2, 0), (-1, 0), (0, 0), (0, 1), (1, 1)],  [(-2, 0), (-1, 0), (0, 0), (1, 0), (1, 1)], [(0, 0), (0, -1), (1, 0), (0, 1), (-1, 0)],  [(-1, -1), (0, -1), (0, 0), (0, 1), (1, 1)], [(-1, -1), (0, -1), (0, 0), (1, 0), (1, 1)],  [(-1, -1), (0, -1), (1, -1), (0, 0), (0, 1)], [(-1, -1), (0, -1), (0, 0), (1, 0), (0, 1)],  [(-1, -1), (0, -1), (1, -1), (1, 0), (1, 1)]


def eckenUndKanten(liste):
    ecken = []
    kanten = []
    # Erstelle die Eckenliste
    for x, y in liste:
        for xCord in [-1, 1]:
            for yCord in [-1, 1]:
                ecken.append((x + xCord, y + yCord))

    # Entferne die Koordinaten der Ecken aus der e
    ecken = list(set(ecken))  # Um Duplikate zu entfernen
    for coord in ecken:
        if coord in liste:
            ecken.remove(coord)

    # Entferne die Ecken, die direkt an einen Block angrenzen
    benachbarte_ecken = [(0, 1), (0, -1), (1, 0), (-1, 0)]
    for x, y in liste:
        for coord in benachbarte_ecken:
            nachbar = x + coord[0], y + coord[1] 
            kanten.append(nachbar)
            if (nachbar) in ecken:
                ecken.remove(nachbar)

    
    kanten = list(set(kanten))  # Um Duplikate zu entfernen
    for coord in liste:
        if coord in kanten:
            kanten.remove(coord)
    return (ecken, kanten)


for top in bloecke:
    print(eckenUndKanten(top)[1])