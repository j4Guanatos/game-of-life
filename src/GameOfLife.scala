object Solution {
  import Matrix.copy

  def gameOfLife(board: Array[Array[Int]]): Unit = {
    copy(Board(board, Conway.nextValue).next.board, board)
  }

  def main(args: Array[String]): Unit = {
    val inputs = Array(
      Array(Array(0, 1, 0), Array(0, 0, 1), Array(1, 1, 1), Array(0, 0, 0))
    )

    inputs.map(Board(_, Conway.nextValue).next()).foreach(_.print())
  }
}

object Matrix {
  def valid(i: Int, size: Int): Boolean = i >= 0 && i < size

  def copy(source: Array[Array[Int]], target: Array[Array[Int]]): Unit = {
    source.indices.foreach(i => source(i).indices.foreach(j => target(i)(j) = source(i)(j)))
  }
}

case class Board(board: Array[Array[Int]],
                 nextRule: (Board, Int, Int) => Int) {
  import Matrix._

  def allNeighbours(i: Int, j: Int): List[Int] =
    neighbours()
    .map { case(x, y) => (i + x, j + y) }
    .filter { case (x, y) => valid(x, board.length) && valid(y, board(x).length) }
    .map { case (x, y) => board(x)(y) }
    .toList

  def at(i: Int, j: Int): Int = board(i)(j)

  def next(): Board = Board(nextState(), nextRule)

  def print(): Unit = board.foreach(row => println(row.mkString(" ")))

  private def neighbours() = {
    for {x <- (-1 to 1); y <- (-1 to 1) if !(x == 0 && y == 0)} yield (x, y)
  }

  private def nextState() =
    board
    .zipWithIndex
    .map { case (row, i) => row.indices.map(j => nextRule(this, i, j)).toArray }
}

/**
 * Any live cell with fewer than two live neighbors dies as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population.
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 */
object Conway {
  def nextValue(board: Board, i: Int, j: Int): Int = {
    val isAlive = alive(board.at(i, j))
    val liveCellsAround = board.allNeighbours(i, j).count(alive)
    if (isAlive) if ((2 to 3).contains(liveCellsAround)) 1 else 0 else if (liveCellsAround == 3) 1 else 0
  }

  def alive(value: Int): Boolean = value == 1
}
