# j4G Game of Life Challenge
March 2021 Live Coding Meetup

## What is Game of Life?

Game of life is a cellular automata composed of a n-dimensional grid in which each cell has a range of values which iteratively change according to rules related by its neighbours.

A traditional problem is a 2-dimensional grid in which each cell is alive or dead. It will remain alive if one or two of its directly adjacent neighbours (up, left, right and down) is alive, otherwise it will turn its state to dead.

For example, this initial grid:

will evolve to this one:

Our task will be to code and show some Game of Life related problems.

## Preliminary problems.

Compute the next step of a given grid:

[Game of Life](https://leetcode.com/problems/game-of-life/)

Compute N steps on a 1-dimensional grid:

[Prison cells after N days](https://leetcode.com/problems/prison-cells-after-n-days/)

Challenges:

* Generic model for multiple rules definition.
* Adjacent (4) or all around (8) neighbours.
* Memory usage.
* Can we compute only a subset of the whole grid?

## Evolve until chaos stabilizes.

Some of the cellular automata can stabilize (going to a static state), cycle (repeatable cycles of states) or turn into an unpredictable state of chaos, like the irrational numbers.

We will work with chaos stabilization, identifying a point where the grid doesn’t change after any subsequent iteration.

