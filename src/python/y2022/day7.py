import os

class Node:
    def __init__(self, name, size = 0):
        self.children = []
        self.name = name
        self.size = size
        self.parent = None
    
    def tree_size(self):
        accumulator = self.size
        for child in self.children:
            accumulator += child.tree_size()
        return accumulator

    def add_child(self, node):
        self.children.append(node)
        node.parent = self

    def __str__(self):
        result = ""
        cur_node = self
        while cur_node != None:
            result += "  "
            cur_node = cur_node.parent
        
        result += self.name
        if self.size == 0:
            result += " [dir]"
        else:
            result += f" ({self.size})"
        result += "\n"
        
        for child in self.children:
            result += f"{child}"
        return result



path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day7"))
with open(path, "tr") as file:
    lines = file.read().splitlines()

pos = 0
root = Node("/")
current_node = root

while pos < len(lines):
    input = lines[pos]

    if input[0] == '$':
        # it's a command
        tokens = input.split()
        if tokens[1] == "cd":
            if tokens[2] == "/":
                current_node = root
            elif tokens[2] == "..":
                current_node = current_node.parent
            else:
                for child in current_node.children:
                    if child.name == tokens[2]:
                        current_node = child
        elif tokens[1] == "ls":
            # read children
            while pos + 1 < len(lines) and lines[pos + 1][0] != '$':
                pos += 1
                child_tokens = lines[pos].split()
                if child_tokens[0] == "dir":
                    current_node.add_child(Node(child_tokens[1]))
                else:
                    current_node.add_child(Node(child_tokens[1], int(child_tokens[0])))
    pos += 1

def look_for_small_directories(node):
    acc = 0
    if node.size == 0 and node.tree_size() <= 100000:
        acc += node.tree_size()
    for child in node.children:
        acc += look_for_small_directories(child)
    return acc

resultA = 0
for child in root.children:
    resultA += look_for_small_directories(child)

space_to_free = root.tree_size() - 40000000
resultB = 99999999999999

def look_for_directory_to_remove(node):
    global resultB
    tree_size = node.tree_size()
    if node.size == 0 and tree_size >= space_to_free and tree_size < resultB:
        resultB = tree_size
    for child in node.children:
        look_for_directory_to_remove(child)

look_for_directory_to_remove(root)

print("Part A: {}".format(resultA))
print("Part B: {}".format(resultB))
