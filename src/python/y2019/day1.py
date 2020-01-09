import os


def calc_fuel(mass):
    fuel = (mass // 3) - 2
    if fuel < 0:
        return 0
    else:
        return fuel


def calc_fuel_com(mass):
    fuel = calc_fuel(mass)
    if fuel > 0:
        return fuel + calc_fuel_com(fuel)
    else:
        return fuel


path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../input/2019/day1"))
with open(path, "tr") as file:
    lines = list(map(int, file.read().splitlines()))

fuel_part_a = 0
fuel_part_b = 0
for mass in lines:
    fuel_part_a += calc_fuel(mass)
    fuel_part_b += calc_fuel_com(mass)

print("Part A: {}".format(fuel_part_a))  # 3297866
print("Part B: {}".format(fuel_part_b))  # 4943923
