


fun main() {

    data class Coord(val row:Int, val col:Int)
    data class Diagram(val start:Coord, val splitters:Set<Coord>, val maxRowCol:Pair<Int, Int>)


    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():Diagram {
        val maxRow = this.count()-1
        val maxCol = this[0].count()-1
        val startX = this[0].indexOf('S')
        val splitters = this.flatMapIndexed{ row, line ->
            line.mapIndexedNotNull { col, c ->
                if (c == '^') Coord(row, col) else null
            }
        }.toSet()
        val startCoord = Coord(0, startX)

        return Diagram(startCoord, splitters, maxRow to maxCol)
    }


    // *******************
    // Part 1
    // *******************



    fun part1(input: List<String>):Long {
        val diagram = input.parse()
        val (maxRows, maxCols) = diagram.maxRowCol
        var ret = 0L
        var currentBeams = mutableSetOf(diagram.start)
        while (currentBeams.isNotEmpty()) {
            val nextBeams = mutableSetOf<Coord>()
            for ((row, col) in currentBeams) {
                if (row + 1 > maxRows) continue
                val nextBeamDown = Coord(row+1, col)
                if (diagram.splitters.contains(nextBeamDown)) {
                    ret++
                    if (col-1 >= 0) nextBeams.add(Coord(row+1, col-1))
                    if (col+1 <= maxCols) nextBeams.add(Coord(row+1, col+1))
                }
                else nextBeams.add(nextBeamDown)

            }
            currentBeams = nextBeams
        }
        return ret
    }





















    // *******************
    // Part 2
    // ********************


    fun part2(input:List<String>):Long {
        val diagram = input.parse()
        val (maxRows, maxCols) = diagram.maxRowCol
        var ret = 0L
        var currentBeams = mutableMapOf(diagram.start to 1L)
        while (currentBeams.isNotEmpty()) {
            val nextBeams = mutableMapOf<Coord, Long>()
            for ((coord, count) in currentBeams) {
                val (row, col) = coord
                if (row + 1 > maxRows) {
                    ret += count
                    continue
                }

                val nextBeamDown = Coord(row+1, col)
                if (diagram.splitters.contains(nextBeamDown)) {
                    if (col > 0) {
                        val downLeft = Coord(row+1, col-1)
                        val value = nextBeams.getOrDefault(downLeft, 0L) + count
                        nextBeams[downLeft] = value
                    }

                    if (col+1 <= maxCols) {
                        val downRight = Coord(row+1, col+1)
                        val value = nextBeams.getOrDefault(downRight, 0L) + count
                        nextBeams[downRight] = value
                    }
                }
                else {
                    val value = nextBeams.getOrDefault(nextBeamDown, 0L) + count
                    nextBeams[nextBeamDown] = value
                }
            }
            currentBeams = nextBeams
        }
        return ret
    }


    // *******************
    // Test code
    // *******************
    val testInput = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 21L)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 40L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
