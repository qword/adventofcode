import os


path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day10"))
with open(path, "tr") as file:
    lines = file.read().splitlines()


cycle = sum = 0
register = 1
display = [[" " for x in range(40)] for y in range(6)]

def draw():
    global cycle, register, display
    if abs(cycle % 40 - register) <= 1:
        display[int(cycle / 40)][cycle % 40] = "#"

def check():
    global cycle, register, sum, display
    if cycle in [20, 60, 100, 140, 180, 220]:
        sum += cycle * register


for line in lines:
    instruction = line.split()[0]

    draw()
    cycle += 1
    check()
    
    if instruction == "addx":
        draw()
        value = int(line.split()[1])
        cycle += 1
        check()
        register += value


print("Part A: {}".format(sum))
print("Part B:")
for i in range(6):
    for j in range(40):
        print(display[i][j], end='')
    print()

