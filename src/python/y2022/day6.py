import os

path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day6"))
with open(path, "tr") as file:
    lines = file.read().splitlines()

input = lines[0]

def find_marker(window_size):
    for i in range(len(input) - window_size + 1):
        s = set()
        for j in range(window_size):
            s.add(input[i + j])
            if (len(s) == window_size):
                return i + j + 1
    return -1

print("Part A: {}".format(find_marker(4)))
print("Part B: {}".format(find_marker(14)))
