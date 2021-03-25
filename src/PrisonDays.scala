import scala.collection.mutable

object PrisonDays {
  def prisonAfterNDays(cells: Array[Int], n: Int): Array[Int] = {
    val stateMap: mutable.Map[Byte, (Prison, Int)] = mutable.Map()
    evolve(Prison(cells), 0, n, stateMap)
  }

  @scala.annotation.tailrec
  def evolve(prison: Prison, current: Int, total: Int, stateMap: mutable.Map[Byte, (Prison, Int)]): Array[Int] =
    if (current == total) prison.toCells
    else if (stateMap.contains(prison.state)) {
      val cycleInit = stateMap(prison.state)._2
      val remains = (total - current) % (current - cycleInit)
      cached(prison, 0, remains, stateMap)
    } else {
      val next = prison.next
      stateMap.put(prison.state, (next, current))
      evolve(next, current + 1, total, stateMap)
    }

  @scala.annotation.tailrec
  def cached(prison: Prison, current: Int, total: Int, stateMap: mutable.Map[Byte, (Prison, Int)]): Array[Int] =
    if (current == total) prison.toCells
    else cached(stateMap(prison.state)._1, current + 1, total, stateMap)

  def main(args: Array[String]): Unit = {
    // Calculation examples.
    val int = Prison.binaryToInt("11011001")
    val samples = Array(
      int,                 // 01011001
      int << 1,            // 10110010
      int >> 1,            // 00101100
      int << 1 ^ int >> 1, // 10011110
      ~((int << 1) ^ (int >> 1)),           // 01100001
      ~((int << 1) ^ (int >> 1)) >> 1,      // 00110000
      ~((int << 1) ^ (int >> 1)) >> 1 << 1, // 01100000
      ~((int << 1) ^ (int >> 1)) >> 1 << 1 & ~Prison.binaryToInt("10000000"), // 01100000
    ).map(_.toByte)

    samples.foreach(println)
    samples.map(Prison.intToBinary).foreach(println)

    val inputs = Array(
      (Array(0,1,0,1,1,0,0,1), 7),
      (Array(1,0,0,1,0,0,1,0), 1000000000),
      (Array(1,1,0,0,0,0,0,1), 8)
    )

    inputs.map { case (cells, n) => prisonAfterNDays(cells, n).toList }.foreach(println)
  }
}

case class Prison(state: Byte) {
  private val firstOneRemover = ~Prison.binaryToInt("10000000")
  def toCells: Array[Int] = Prison.fromState(state)
  def next: Prison = Prison(nextState())
  private def nextState(): Byte = (~((state << 1) ^ (state >> 1)) >> 1 << 1 & firstOneRemover).toByte
}

object Prison {
  def apply(cells: Array[Int]) = new Prison(toState(cells))
  def toState(cells: Array[Int]): Byte = binaryToInt(cells.mkString(""))
  def fromState(state: Byte): Array[Int] = intToBinary(state).toList.map(chr => chr - '0').toArray
  def binaryToInt(binary: String): Byte = Integer.parseInt(binary, 2).toByte
  def intToBinary(int: Byte): String = Integer.toBinaryString(int).reverse.padTo(8, '0').reverse.takeRight(8)
}
