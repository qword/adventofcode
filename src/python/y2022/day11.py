import os
import re
import math


class Monkey:
    def __init__(self):
        self.inspections = 0

    def __str__(self):
        return f"Monkey {self.id}, items: {self.items}"

def solve(partB = False):
    path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2022/day11"))
    with open(path, "tr") as file:
        lines = file.read().splitlines()

    monkeys = []

    monkey = Monkey()
    for line in lines:
        if len(line) == 0:
            monkeys.append(monkey)
            monkey = Monkey()

        regexp = re.match("^Monkey (\d+):$", line)
        if regexp != None:
            monkey.id = regexp.group(1)
        
        regexp = re.match("^  Starting items: ([\d\,\s]+)$", line)
        if regexp != None:
            monkey.items = [int(n.strip()) for n in regexp.group(1).split(",")]

        regexp = re.match("^  Operation: new = old (.) (.+)$", line)
        if regexp != None:
            monkey.operation = regexp.group(1)
            monkey.operand = regexp.group(2)

        regexp = re.match("^  Test: divisible by (\d+)$", line)
        if regexp != None:
            monkey.test = int(regexp.group(1))

        regexp = re.match("^    If true: throw to monkey (\d+)$", line)
        if regexp != None:
            monkey.if_true = int(regexp.group(1))
        
        regexp = re.match("^    If false: throw to monkey (\d+)$", line)
        if regexp != None:
            monkey.if_false = int(regexp.group(1))
    monkeys.append(monkey)

    mod_all = 1
    for div_by in [m.test for m in monkeys]:
        mod_all *= div_by

    rng = 20
    if partB == True:
        rng = 10000

    for turn in range(rng):
        for monkey in monkeys:
            while len(monkey.items) > 0:
                worry_level = monkey.items.pop(0)
                monkey.inspections += 1

                worry_operand = worry_level # "old" case
                if monkey.operand != "old":
                    worry_operand = int(monkey.operand)

                if monkey.operation == "*":
                    worry_level = worry_level * worry_operand
                if monkey.operation == "+":
                    worry_level = worry_level + worry_operand

                if partB == False:
                    worry_level = math.trunc(worry_level / 3)
                else:
                    worry_level = worry_level % mod_all

                if worry_level % monkey.test == 0:
                    monkeys[monkey.if_true].items.append(worry_level)
                else:
                    monkeys[monkey.if_false].items.append(worry_level)

    inspections = [m.inspections for m in monkeys]
    inspections.sort()
    return inspections[len(inspections) - 1] * inspections[len(inspections) - 2]

print("Part A: {}".format(solve()))
print("Part B: {}".format(solve(True)))
