import sys
from itertools import product
from os.path import isfile

def occupied_seats(seats, x, y):
    occupied = 0
    for a, b in product([x-1, x, x+1], [y-1, y, y+1]):
        if a == x and b == y:
            continue

        if (0 <= a < len(seats[0])) and (0 <= b < len(seats)):
           if seats[b][a] == '#':
               occupied += 1
    return occupied


def solution(seats):
    while True:
        changes = []
        total_occupied = 0
        for y, row in enumerate(seats):
            for x, seat in enumerate(row):
                if seat == '.':
                    continue
                if seat == '#':
                    total_occupied += 1
                occupied = occupied_seats(seats, x, y)
                if seat == 'L' and occupied == 0:
                    changes.append(('#', x, y))
                if seat == '#' and occupied >= 4:
                    changes.append(('L', x, y))
        if not changes:
            return total_occupied
        for new_value, x, y in changes:
            seats[y][x] = new_value


def read_input(path):
    seats = []    
    for line in open(path):
        seats.append(list(line.strip()))
    return seats


def main():
    if len(sys.argv) < 2:
        raise SystemExit("Please supply a file")
    if not isfile(sys.argv[1]):
        raise SystemExit(f"{sys.argv[1]} is not valid file") 
    seats = read_input(sys.argv[1])
    occupied = solution(seats)
    print(occupied)

if __name__ == '__main__':
    main()