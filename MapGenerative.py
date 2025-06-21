import os
import random
import math

def generate_biome_map(width=100, height=100, spawn=(10, 10), seed=None):
    """
    Génère une map de taille width x height avec divers biomes :
    - Bordures impassables (stone)
    - Desert, prairie, forêt, ruines
    - Lacs et plages
    - Villages (zones boisées)
    - Chemins reliant spawn, villages et ruines
    Chaque exécution produit une map différente si seed=None.
    """
    if seed is not None:
        random.seed(seed)
    else:
        random.seed()
    # Tile codes :
    # 0: dirt1, 1: dirt2
    # 2: grass1, 3: grass2
    # 4: stone1, 5: stone2 (collision)
    # 6: water1 (collision)
    # 7: sand1
    # 8: tree1 (collision)
    # 9: wood1 (village buildings)

    # Initialisation : grid vide
    grid = [[None for _ in range(width)] for _ in range(height)]

    def in_bounds(x, y):
        return 0 <= x < width and 0 <= y < height

    # 1. Bordures : stone collision
    for x in range(width):
        grid[0][x] = 4  # stone1
        grid[height-1][x] = 4
    for y in range(height):
        grid[y][0] = 4
        grid[y][width-1] = 4

    # 2. Définir des centres de biomes (Voronoi)
    biome_types = ['desert', 'prairie', 'forest', 'ruin']  # types
    # Poids pour la fréquence
    weights = {'desert': 1, 'prairie': 3, 'forest': 2, 'ruin': 1}
    # Nombre de centres de biomes
    num_centers = random.randint(6, 10)
    centers = []
    for _ in range(num_centers):
        # Eviter trop près des bords
        cx = random.randint(10, width - 11)
        cy = random.randint(10, height - 11)
        # Choisir un type selon poids
        types = []
        for t in biome_types:
            types += [t] * weights[t]
        btype = random.choice(types)
        centers.append((cx, cy, btype))
    # 3. Pour chaque cellule non border, assigner biome le plus proche
    for y in range(1, height-1):
        for x in range(1, width-1):
            if grid[y][x] is not None:
                continue
            # Trouver centre le plus proche
            best = None
            best_dist = None
            for cx, cy, btype in centers:
                d = (x - cx)**2 + (y - cy)**2
                if best_dist is None or d < best_dist:
                    best_dist = d
                    best = btype
            # Remplir selon biome
            if best == 'desert':
                # Majoritairement sand, quelques rochers
                if random.random() < 0.1:
                    grid[y][x] = random.choice([4, 5])  # stone sporadique
                else:
                    grid[y][x] = 7  # sand
            elif best == 'prairie':
                # Herbe, avec quelques arbres dispersés
                if random.random() < 0.05:
                    grid[y][x] = 8  # tree
                else:
                    grid[y][x] = random.choice([2, 3])
            elif best == 'forest':
                # Densité d'arbres élevée
                if random.random() < 0.7:
                    grid[y][x] = 8
                else:
                    grid[y][x] = random.choice([2, 3])
            elif best == 'ruin':
                # Base herbe, ruine sera incrémenté plus tard
                grid[y][x] = random.choice([2, 3])

    # 4. Ajouter lacs aléatoires dans les zones prairie/forest
    num_lakes = random.randint(3, 6)
    lake_centers = []
    for _ in range(num_lakes):
        # Choisir centre dans prairie ou forêt
        attempts = 0
        while attempts < 100:
            cx = random.randint(5, width - 6)
            cy = random.randint(5, height - 6)
            if grid[cy][cx] in (2, 3, 8):  # herbe ou arbre
                break
            attempts += 1
        radius = random.randint(3, 7)
        lake_centers.append((cx, cy, radius))
        for yy in range(cy - radius, cy + radius + 1):
            for xx in range(cx - radius, cx + radius + 1):
                if in_bounds(xx, yy) and (xx - cx)**2 + (yy - cy)**2 <= radius**2:
                    grid[yy][xx] = 6  # water
    # Plage autour de lacs
    for y in range(1, height-1):
        for x in range(1, width-1):
            if grid[y][x] == 6:
                for dx, dy in [(1,0),(-1,0),(0,1),(0,-1)]:
                    nx, ny = x+dx, y+dy
                    if in_bounds(nx, ny) and grid[ny][nx] in (2, 3):
                        grid[ny][nx] = 7  # beach

    # 5. Placer villages dans prairie
    village_positions = []
    num_villages = random.randint(3, 5)
    for _ in range(num_villages):
        for attempt in range(200):
            cx = random.randint(10, width - 11)
            cy = random.randint(10, height - 11)
            # Vérifier zone: prairie majoritaire et pas trop proche de l'eau
            if grid[cy][cx] not in (2, 3):
                continue
            # vérifier voisinage sans arbres denses et eau
            ok = True
            for dy in range(-3, 4):
                for dx in range(-3, 4):
                    nx, ny = cx+dx, cy+dy
                    if not in_bounds(nx, ny):
                        ok = False; break
                    if grid[ny][nx] == 6 or grid[ny][nx] == 8:
                        ok = False; break
                if not ok:
                    break
            if ok:
                # Carve village: zone 5x5 en bois
                for dy in range(-2, 3):
                    for dx in range(-2, 3):
                        nx, ny = cx+dx, cy+dy
                        if in_bounds(nx, ny):
                            grid[ny][nx] = 9
                village_positions.append((cx, cy))
                break

    # 6. Placer ruines dans biome ruin
    ruin_positions = []
    num_ruins = random.randint(3, 5)
    for _ in range(num_ruins):
        for attempt in range(200):
            cx = random.randint(10, width - 11)
            cy = random.randint(10, height - 11)
            # Vérifier biome ruine et zone libre
            if grid[cy][cx] not in (2, 3):
                continue
            ok = True
            for dy in range(-4, 5):
                for dx in range(-4, 5):
                    nx, ny = cx+dx, cy+dy
                    if not in_bounds(nx, ny):
                        ok = False; break
                    if grid[ny][nx] in (6, 8, 9):
                        ok = False; break
                if not ok:
                    break
            if ok:
                radius = random.randint(3, 5)
                for yy in range(cy - radius, cy + radius + 1):
                    for xx in range(cx - radius, cx + radius + 1):
                        if in_bounds(xx, yy) and (xx - cx)**2 + (yy - cy)**2 <= radius**2:
                            grid[yy][xx] = random.choice([4, 5])
                ruin_positions.append((cx, cy))
                break

    # 7. Assurer spawn libre et zone dégagée
    sx, sy = spawn
    if in_bounds(sx, sy):
        grid[sy][sx] = random.choice([2, 3])
    for dy in range(-2, 3):
        for dx in range(-2, 3):
            nx, ny = sx+dx, sy+dy
            if in_bounds(nx, ny) and grid[ny][nx] in (6, 8, 4, 5):
                grid[ny][nx] = random.choice([2, 3])

    # 8. Tracer chemins L-shaped
    def carve_path(x1, y1, x2, y2):
        x, y = x1, y1
        # Décider ordre
        if random.random() < 0.5:
            # horizontal puis vertical
            while x != x2:
                if grid[y][x] != 6:
                    grid[y][x] = random.choice([0, 1])
                x += 1 if x < x2 else -1
            while y != y2:
                if grid[y][x] != 6:
                    grid[y][x] = random.choice([0, 1])
                y += 1 if y < y2 else -1
        else:
            while y != y2:
                if grid[y][x] != 6:
                    grid[y][x] = random.choice([0, 1])
                y += 1 if y < y2 else -1
            while x != x2:
                if grid[y][x] != 6:
                    grid[y][x] = random.choice([0, 1])
                x += 1 if x < x2 else -1
        # finale
        if grid[y][x] != 6:
            grid[y][x] = random.choice([0, 1])

    # Connecter spawn -> plus proche village
    if village_positions:
        vx, vy = min(village_positions, key=lambda v: abs(v[0]-sx)+abs(v[1]-sy))
        carve_path(sx, sy, vx, vy)
    # Connecter villages entre eux
    for i in range(len(village_positions)-1):
        x1, y1 = village_positions[i]
        x2, y2 = village_positions[i+1]
        carve_path(x1, y1, x2, y2)
    # Connecter villages -> ruines
    for vp in village_positions:
        if ruin_positions:
            rx, ry = min(ruin_positions, key=lambda r: abs(r[0]-vp[0])+abs(r[1]-vp[1]))
            carve_path(vp[0], vp[1], rx, ry)

    return grid

# Exécution et sauvegarde
map_grid = generate_biome_map(width=100, height=100, spawn=(10, 10), seed=None)
output_rel_path = "resources/Map/generated_map.txt"
dirpath = os.path.dirname(output_rel_path)
if dirpath and not os.path.exists(dirpath):
    os.makedirs(dirpath)
with open(output_rel_path, "w") as f:
    for row in map_grid:
        f.write(" ".join(str(cell) for cell in row) + "\n")
print(f"Map générée et sauvegardée dans: {output_rel_path}")
