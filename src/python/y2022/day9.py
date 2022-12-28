import os


path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day9"))
with open(path, "tr") as file:
    lines = file.read().splitlines()

def create_knots(quantity):
    knots = []
    for k in range(quantity):
        knots.append((0, 0))
    return knots

def move_head(knots, direction):
    head = knots[0]

    if direction == "R": 
        new_head = (head[0] + 1, head[1])
    elif direction == "U":
        new_head = (head[0], head[1] - 1)
    elif direction == "L":
        new_head = (head[0] - 1, head[1])
    elif direction == "D":
        new_head = (head[0], head[1] + 1)
    
    knots[0] = new_head


def follow(knots, positions, index):
    head = knots[index - 1]
    tail = knots[index]

    headX = head[0]
    headY = head[1]

    tailX = tail[0]
    tailY = tail[1]

    # diagonal tail move
    if abs(headX - tailX) + abs(headY - tailY) > 2:
        if headX > tailX:
            tailX += 1
        elif headX < tailX:
            tailX -= 1
        
        if headY > tailY:
            tailY += 1
        elif headY < tailY:
            tailY -= 1

    # direct tail move
    if headX - tailX > 1:
        tailX += 1
    if tailX - headX > 1:
        tailX -= 1
    if headY - tailY > 1:
        tailY += 1
    if tailY - headY > 1:
        tailY -= 1

    position = (tailX, tailY)

    if index == len(knots) - 1:
        positions.add(position)

    knots[index] = position


sizeA = 2
sizeB = 10

knotsA = create_knots(sizeA)
positionsA = set()

knotsB = create_knots(sizeB)
positionsB = set()

for line in lines:
    direction = line.split()[0]
    distance = int(line.split()[1])

    for i in range(distance):
        move_head(knotsA, direction)
        move_head(knotsB, direction)
        for j in range(1, sizeA):
            follow(knotsA, positionsA, j)
        for j in range(1, sizeB):
            follow(knotsB, positionsB, j)
        #print(knots)


print("Part A: {}".format(len(positionsA)))
print("Part B: {}".format(len(positionsB)))
