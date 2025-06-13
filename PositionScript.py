def load_map(path):
    map_data = []
    with open(path, 'r') as f:
        for line in f:
            row = [int(p) for p in line.strip().split()]
            if len(row) == 100:  # ou votre largeur
                map_data.append(row)
    return map_data

def find_candidates(map_data):
    h = len(map_data)
    w = len(map_data[0]) if h>0 else 0
    key_positions = []
    door_positions = []
    # Parcourir toutes les tuiles sauf les bords
    for y in range(1, h-1):
        for x in range(1, w-1):
            tile = map_data[y][x]
            # Clé: tuile=9, aucun arbre adjacent (8), et pas en bord
            if tile == 9:
                ok = True
                for dy in (-1,0,1):
                    for dx in (-1,0,1):
                        if dx==0 and dy==0: continue
                        ny, nx = y+dy, x+dx
                        # ny,nx dans [0,h-1] et [0,w-1], mais on écarte bord => ok
                        if map_data[ny][nx] == 8:
                            ok = False
                if ok:
                    key_positions.append((x,y))
            # Porte: tuile 4 ou 5, adjacente à herbe 2/3, pas d’arbre adjacent, et pas en bord
            if tile in (4,5):
                adjGrass = False
                adjTree = False
                for dy in (-1,0,1):
                    for dx in (-1,0,1):
                        if dx==0 and dy==0: continue
                        ny, nx = y+dy, x+dx
                        if map_data[ny][nx] in (2,3):
                            adjGrass = True
                        if map_data[ny][nx] == 8:
                            adjTree = True
                if adjGrass and not adjTree:
                    door_positions.append((x,y))
    return key_positions, door_positions

def generate_java_lines(keys, doors, tileVar="tileSize"):
    lines = []
    for i, (x,y) in enumerate(keys[:3]):
        lines.append(f"// obj[{i}]: clé à ({x},{y})")
        lines.append(f"box.obj[{i}] = new ObjectKey();")
        lines.append(f"box.obj[{i}].setWorld_x({x} * {tileVar});")
        lines.append(f"box.obj[{i}].setWorld_y({y} * {tileVar});")
        lines.append("")
    baseDoorIdx = 3
    for j, (x,y) in enumerate(doors[:3]):
        idx = baseDoorIdx + j
        lines.append(f"// obj[{idx}]: porte à ({x},{y})")
        lines.append(f"box.obj[{idx}] = new ObjectDoor();")
        lines.append(f"box.obj[{idx}].setWorld_x({x} * {tileVar});")
        lines.append(f"box.obj[{idx}].setWorld_y({y} * {tileVar});")
        lines.append("")
    return "\n".join(lines)

# Exemple d'utilisation
map_path = "resources/Map/Map.txt" 
map_data = load_map(map_path)
if not map_data:
    print("Erreur: impossible de charger la map depuis", map_path)
else:
    keys, doors = find_candidates(map_data)
    print("Clé candidates (x,y) hors bord:", keys[:10])
    print("Porte candidates (x,y) hors bord:", doors[:10])
    if len(keys) < 3:
        print("Attention: moins de 3 positions valides pour clés trouvées.")
    if len(doors) < 3:
        print("Attention: moins de 3 positions valides pour portes trouvées.")
    print("\n=== Lignes Java à coller dans Assets.set_object() ===\n")
    print(generate_java_lines(keys, doors))