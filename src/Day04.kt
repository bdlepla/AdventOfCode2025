
fun main() {

    data class Coord(val row:Int, val col:Int)
    fun Coord.surrounding():List<Coord> =
        (-1..1).flatMap {r ->
            (-1 .. 1).mapNotNull {c ->
                if (r != 0 || c != 0) Coord(row+r, col+c)
                else null
            }
        }

    data class Diagram(val paperRolls:Set<Coord>)

    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():Diagram =
        Diagram(this.flatMapIndexed {row, line ->
            line.mapIndexedNotNull {col, char ->
                if (char == '@') Coord(row, col) else null
            }
        }.toSet())




    // *******************
    // Part 1
    // *******************

    // returns true is less than 4 of the surrounding 8 spaces contain a paper roll
    fun Diagram.canAccessPaperRoll(coord:Coord):Boolean = coord.surrounding().intersect(paperRolls).count() < 4

    fun Diagram.removeAvailablePaperRolls():Pair<Int, Diagram> {
        val paperRolls = this.paperRolls
        val toRemove = paperRolls.filter{this.canAccessPaperRoll(it)}.toSet()
        return toRemove.count() to Diagram(paperRolls.subtract(toRemove))
    }

    fun part1(input: List<String>):Int = input.parse().removeAvailablePaperRolls().first




















    // *******************
    // Part 2
    // *******************


    fun part2(input:List<String>):Int =
        generateSequence(0 to input.parse()) {  (_, diagram) ->
            val nextSequence = diagram.removeAvailablePaperRolls()
            if (nextSequence.first > 0) nextSequence else null
        }.sumOf{it.first}


    // *******************
    // Test code
    // *******************
    val testInput = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 13)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 43)
    println("Check Part 2 passed")

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
