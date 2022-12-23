import os


path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day8"))
with open(path, "tr") as file:
    lines = file.read().splitlines()

grid = []
for line in lines:
    row = []
    row.extend(line)
    grid.append(list(map(int, row)))

visibility = [[0 for x in range(len(grid[0]))] for x in range(len(grid))]

#mark edges
for i in range(len(grid)):
    visibility[i][0] = 1
    visibility[i][len(grid[0]) - 1] = 1

for j in range(len(grid[0])):
    visibility[0][j] = 1
    visibility[len(grid) - 1][j] = 1


#look from left
for i in range(1, len(grid)-1):
    max = grid[i][0]
    for j in range(1, len(grid[0])):
        #print(f"1. check if {grid[i][j]} > {max}")
        if grid[i][j] > max:
            visibility[i][j] = 1
            max = grid[i][j]
            #print(f"Marking {grid[i][j]} at ({i}, {j}) as visible")

#look from right
for i in range(1, len(grid)-1):
    max = grid[i][len(grid[0])-1]
    for j in range(len(grid[0]) - 1, 0, -1):
        #print(f"2. check if {grid[i][j-1]} > {max}")
        if grid[i][j-1] > max:
            visibility[i][j-1] = 1
            max = grid[i][j-1]
            #print(f"Marking {grid[i][j]} at ({i}, {j}) as visible")


#look from top
for j in range(1, len(grid[0])-1):
    max = grid[0][j]
    for i in range(1, len(grid)):
        #print(f"3. check if {grid[i][j]} > {max}")
        if grid[i][j] > max:
            visibility[i][j] = 1
            max = grid[i][j]
            #print(f"Marking {grid[i][j]} at ({i}, {j}) as visible")


#look from bottom
for j in range(1, len(grid[0])-1):
    max = grid[len(grid)-1][j]
    for i in range(len(grid)-1, 0, -1):
        #print(f"4. check if {grid[i-1][j]} > {max}")
        if grid[i-1][j] > max:
            visibility[i-1][j] = 1
            max = grid[i-1][j]
            #print(f"Marking {grid[i][j]} at ({i}, {j}) as visible")

visible_trees = sum(map(sum, visibility))

print("Part A: {}".format(visible_trees))


scenic_score = 0
for i in range(1, len(grid)-1):
    for j in range(1, len(grid[0])-1):
        height = grid[i][j]

        #look right
        r = 0
        for x in range(j+1, len(grid[0])):
            r += 1
            if grid[i][x] >= height:
                break
                
        #look left
        l = 0
        for x in range(j-1, -1, -1):
            l += 1
            if grid[i][x] >= height:
                break

        #look up
        u = 0
        for x in range(i-1, -1, -1):
            u += 1
            if grid[x][j] >= height:
                break

        #look down
        d = 0
        for x in range(i+1, len(grid)):
            d += 1
            if grid[x][j] >= height:
                break
    
        #print(f"({i},{j}) has r:{r}, l:{l}, u:{u}, d:{d}")
        this_scenic_score = l * r * u * d
        if this_scenic_score > scenic_score:
            scenic_score = this_scenic_score

print("Part B: {}".format(scenic_score))
