import os

path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day6"))
with open(path, "tr") as file:
    lines = file.read().splitlines()

input = lines[0]

def find_marker1():
    for i in range(len(input) - 3):
        
        s = set()
        for j in range(4):
            s.add(input[i + j])
            if (len(s) == 4):
                return i + j + 1
    return -1

def find_marker2():
    for i in range(len(input) - 13):
        
        s = set()
        for j in range(14):
            s.add(input[i + j])
            if (len(s) == 14):
                return i + j + 1
    return -1

print("Part A: {}".format(find_marker1()))
print("Part B: {}".format(find_marker2()))
