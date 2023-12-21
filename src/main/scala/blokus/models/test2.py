def print_offset_grid(center, *offset_lists):
    # Funktion zur Addition von Koordinaten
    def add_coordinates(coord1, coord2):
        return (coord1[0] + coord2[0], coord1[1] + coord2[1])

    # Berechne die maximalen und minimalen Koordinaten für das Raster
    min_x = min([coord[0] for offset_list in offset_lists for coord in offset_list]) + center[0]
    max_x = max([coord[0] for offset_list in offset_lists for coord in offset_list]) + center[0]
    min_y = min([coord[1] for offset_list in offset_lists for coord in offset_list]) + center[1]
    max_y = max([coord[1] for offset_list in offset_lists for coord in offset_list]) + center[1]

    # Erstelle eine leere 11x11 Matrix
    grid = [['.' for _ in range(11)] for _ in range(11)]

    # Setze das Zentrum und die Offset-Koordinaten in die Matrix

    symbols = iter(['O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'])

    for offset_list in offset_lists:
        symbol = next(symbols)
        for offset_coord in offset_list:
            coord = add_coordinates(center, offset_coord)
            grid[coord[1]][coord[0]] = symbol

    # Drucke die Matrix
    for row in grid:
        print(" ".join(row))

# Beispielaufruf
center_coordinates = (5, 5)
offset_list1 = [(-2,0), (-1,0), (0,0), (1,0), (1,1)]
offset_list2 = [(0,1), (2,1), (1,2), (2,0), (1,-1), (0,-1), (-1,-1), (-1,1), (-3,0), (-2,-1), (-2,1)]
offset_list3 = [(2,2), (0,2), (2,-1), (-3,1), (-3,-1)]

print_offset_grid(center_coordinates, offset_list1, offset_list2, offset_list3)
