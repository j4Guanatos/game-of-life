from itertools import product

def alive_neighbors(board, x, y):
    alive = 0

    for a, b in product([y-1, y, y+1], [x-1, x, x+1]):
        if (0 <= a < len(board)) and (0 <= b < len(board[0])):
            alive += board[a][b]

    return alive - board[y][x]

class Solution:
    # This is not a pythonic name but it's what leetcode expected		
    def gameOfLife(self, board: List[List[int]]) -> None:
        """
        Do not return anything, modify board in-place instead.
        """
        changes = []
        
        for y, row in enumerate(board):
            for x, value in enumerate(row):
                alive = alive_neighbors(board, x, y)
                
                if value == 1 and alive < 2:
                    changes.append((0, x, y))
                elif value == 1 and alive > 3:
                    changes.append((0, x, y))
                elif value == 0 and alive == 3:
                    changes.append((1, x, y))
                    
        for new_value, x, y in changes:
            board[y][x] = new_value
                
