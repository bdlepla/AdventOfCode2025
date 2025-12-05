
fun main() {

    // using String as our main data structure
    // no further parsing needed

    // *******************
    // Part 1
    // *******************

    fun String.getMaxJoltage():Int =
        ('9' downTo '1').firstNotNullOf { c ->
            val index = this.dropLast(1).indexOf(c)
            if (index != -1) {
                val firstDigit = this[index].toString()
                val secondDigit = this.drop(index + 1).max()
                (firstDigit + secondDigit).toInt()
            } else null
        }

    fun part1(input: List<String>):Int = input.sumOf { it.getMaxJoltage() }


    // *******************
    // Part 2
    // *******************
    fun String.removeNextDigit():String {
        val idx = this.zipWithNext().indexOfFirst{it.second > it.first}
        return if (idx != -1) this.removeRange(idx, idx+1) else this.dropLast(1)
    }

    fun String.getMaxJoltage2():Long =
        generateSequence(this) { acc ->
            val newAcc = acc.removeNextDigit()
            if (newAcc.any()) newAcc else null
        }.takeWhile{ it.length >= 12 }.last().toLong()

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
