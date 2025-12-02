import kotlin.collections.map

fun main() {

    // using LongRange as our main data structure

    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():List<LongRange> =
        this.flatMap { line ->
            // each line is a comma separated of range
            line.split(',').map { range ->
                // each range is numbers separated by '-'
                val r = range.split('-')
                LongRange( r[0].toLong(), r[1].toLong())
            }
        }




    // *******************
    // Part 1
    // *******************
    fun Long.isInvalid():Boolean {
        val s = this.toString()
        val count = s.length
        if (count%2!=0) return false // if not even num of chars, the halves can not match
        val firstHalf = s.take(count/2)
        val secondHalf = s.substring(count/2)
        return firstHalf == secondHalf
    }

    fun LongRange.getInvalidIds():List<Long> = this.asIterable().filter{ it.isInvalid() }

    fun part1(input: List<String>):Long = input.parse().flatMap{ it.getInvalidIds() }.sum()



















    // *******************
    // Part 2
    // *******************

    fun Long.isInvalid2():Boolean {
        val s = this.toString()
        val count = s.length
        if (count == 1) return false
        return (1 .. count/2).any { n ->
            // can this string be broken into x number of n chars?
            // if so, are all parts then the same?
            n == gcd(n, count) && s.windowed(n, n).unanimous()
        }
    }

    fun LongRange.getInvalidIds2():List<Long> = this.asIterable().filter{ it.isInvalid2() }

    fun part2(input: List<String>): Long = input.parse().flatMap{ it.getInvalidIds2() }.sum()

    // *******************
    // Test code
    // *******************
    val testInput = """
11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 1227775554L)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 4174379265L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
