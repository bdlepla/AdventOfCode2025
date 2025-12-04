
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
    fun Diagram.canAccessPaperRoll(coord:Coord):Boolean =
        coord.surrounding().intersect(paperRolls).count() < 4

    fun part1(input: List<String>):Int {
        val diagram = input.parse()
        return diagram.paperRolls.count{diagram.canAccessPaperRoll(it)}
    }



















    // *******************
    // Part 2
    // *******************

    fun part2(input: List<String>): Int {
        var diagram = input.parse()
        val removedRolls = mutableListOf<Coord>()
        while(true) {
            val removedThisRound = diagram.paperRolls.filter{diagram.canAccessPaperRoll(it)}.toSet()
            if (removedThisRound.isEmpty()) break
            val remainingRolls = diagram.paperRolls.subtract(removedThisRound)
            removedRolls.addAll(removedThisRound)
            diagram = Diagram(remainingRolls)
        }

        return removedRolls.count()
    }

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
