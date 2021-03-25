class Solution:
    # Again, not pythonic but whatever
    def prisonAfterNDays(self, cells: List[int], n: int) -> List[int]:
        initial_value = None
        epoch = 0
        
        while epoch < n:
            previous = cells.copy()
            for x in range(1, 7):
                cells[x] = 1 if previous[x-1] == previous[x+1] else 0
                
            if epoch == 0:
                cells[0] = cells[7] = 0
                initial_value = cells.copy()
                
            if cells == initial_value and epoch > 1:
                epoch = n - (n % epoch)
                if epoch == n:
                    return previous
                
            epoch += 1
                
        return cells
