import kotlin.collections.map

fun main() {

    // using String as our main data structure
    // no further parsing needed

    // *******************
    // Part 1
    // *******************
    fun String.indicesOf(filter: (Char)->Boolean):List<Int> =
        this.indices.asIterable().filter { filter(this[it]) }

    fun String.getMaxJoltage():Int {
        val searchString = this.dropLast(1);
        (9 downTo 1).forEach { s ->
            val c = s.toString()[0]
            val matches = searchString.indicesOf{it == c}
            if (matches.any()) {
                return matches.maxOfOrNull {
                    val firstDigit = this[it].toString()
                    val secondDigit = this.drop(it + 1).max().toString()
                    (firstDigit + secondDigit).toInt()
                }!!
            }
        }

        // should never get here
        return 0
    }

    fun part1(input: List<String>):Int = input.sumOf { it.getMaxJoltage() }


    // *******************
    // Part 2
    // *******************

    fun String.getMaxJoltage2():Long {
        var ret = this
        while (ret.length > 12) {
            var removed = false
            for (idx in 0..ret.length-2) {
                if (ret[idx+1] > ret[idx]) {
                    ret = ret.removeRange(idx, idx+1)
                    removed = true
                    break
                }
            }
            if (!removed) {
                val numToDrop = ret.length - 12
                ret = ret.dropLast(numToDrop)
                break
            }
        }
        return ret.toLong()
    }

    fun part2(input: List<String>):Long = input.sumOf { it.getMaxJoltage2() }


    // *******************
    // Test code
    // *******************
    val testInput = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 357)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 3121910778619)
    println("Check Part 2 passed")

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
