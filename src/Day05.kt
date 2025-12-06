import kotlin.math.max
import kotlin.math.min

fun main() {


    data class Database(val fresh:List<LongRange>, val available:List<Long>)


    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():Database {
        val freshInput = this.takeWhile{it.isNotBlank()}
        val freshRanges = freshInput.map{ line ->
            line.split('-')
                .let{ it[0].toLong() .. it[1].toLong() }
        }
        val availableInput = this.drop(freshInput.count()+1).map{ it.toLong() }
        return Database(freshRanges, availableInput)
    }


    // *******************
    // Part 1
    // *******************

    fun Database.isFresh(ingredient:Long):Boolean = fresh.any{ ingredient in it }

    fun part1(input: List<String>):Int {
        val database = input.parse()
        return database.available.count{database.isFresh(it)}
    }




















    // *******************
    // Part 2
    // ********************

    fun LongRange.canMerge(other:LongRange):Boolean =
        (this.first in other) || (this.last in other) ||
        (other.first in this) || (other.last in this)

    fun LongRange.merge(other:LongRange):LongRange =
        min(this.first, other.first) .. max(this.last, other.last)

    // This is needed because the built-in count():Int doesn't take into
    // account that a long range count could be greater than Int.max
    fun LongRange.countLong():Long = last - first + 1L

    // Traverses each element in the SORTED and merges elements
    // that can be merged.
    fun List<LongRange>.performMerge():List<LongRange> =
        this.drop(1)
            .fold(listOf<LongRange>() to this[0]) { (acc, last), it ->
                if (last.canMerge(it)) acc to last.merge(it)
                else (acc + listOf(last)) to it
            }.let{ it.first + listOf(it.second) }

    fun part2(input:List<String>):Long =
        input.parse().fresh.sortedBy{it.first}.performMerge().sumOf{it.countLong()}


    // *******************
    // Test code
    // *******************
    val testInput = """
        3-5
        10-14
        16-20
        12-18
        
        1
        5
        8
        11
        17
        32
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 3)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 14L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
