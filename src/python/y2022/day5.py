import os

def move_crates1(lines):
    section_delimiter = lines.index("")
    stack_number = len(lines[section_delimiter - 1].split())
    stacks =[ [] for i in range(stack_number) ]

    for i in range(section_delimiter - 2, -1, -1):
        line = lines[i]
        for j in range(len(stacks)):
            crate = line[j*4+1]
            if (crate != ' '):
                stacks[j].append(crate)

    for i in range(section_delimiter + 1, len(lines), 1):
        tokens = lines[i].split()
        qty = int(tokens[1])
        move_from = int(tokens[3]) - 1
        move_to = int(tokens[5]) - 1

        for j in range(qty):
            if (len(stacks[move_from]) > 0):
                stacks[move_to].append(stacks[move_from].pop())

    result = ""
    for i in range(stack_number):
        result += stacks[i].pop()

    return result

def move_crates2(lines):
    section_delimiter = lines.index("")
    stack_number = len(lines[section_delimiter - 1].split())
    stacks =[ [] for i in range(stack_number) ]

    for i in range(section_delimiter - 2, -1, -1):
        line = lines[i]
        for j in range(len(stacks)):
            crate = line[j*4+1]
            if (crate != ' '):
                stacks[j].append(crate)

    for i in range(section_delimiter + 1, len(lines), 1):
        tokens = lines[i].split()
        qty = int(tokens[1])
        move_from = int(tokens[3]) - 1
        move_to = int(tokens[5]) - 1

        tmp_stack = []
        for j in range(qty):
            if (len(stacks[move_from]) > 0):
                tmp_stack.append(stacks[move_from].pop())
        tmp_stack.reverse()
        for crate in tmp_stack:
            stacks[move_to].append(crate)

    result = ""
    for i in range(stack_number):
        result += stacks[i].pop()

    return result

path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day5"))
with open(path, "tr") as file:
    lines = file.read().splitlines()


print("Part A: {}".format(move_crates1(lines)))
print("Part B: {}".format(move_crates2(lines)))


