import kotlin.collections.map

enum class LockTurn(val mod:Int) {
    Right(1),
    Left(-1)
}

fun main() {

    data class Instruction(val turn:LockTurn, val amount: Int )

    // *******************
    // Parsing
    // *******************
    fun String.toLockTurn():LockTurn = if (this == "L") LockTurn.Left else LockTurn.Right
    fun List<String>.parse():List<Instruction> {
        val regExp = Regex("([LR])(\\d+)")
        return this
            .map { regExp.matchEntire(it)!!.groups }
            .map { Instruction(it[1]!!.value.toLockTurn(), it[2]!!.value.toInt())}
    }



    // *******************
    // Part 1
    // *******************
    fun Instruction.turn1(position:Int):Int = (position + (turn.mod * amount)).mod(100)

    fun part1(input: List<String>):Int =
        input.parse().runningFold(50) { acc, inst -> inst.turn1(acc) }.count{ it == 0 }


















    // *******************
    // Part 2
    // *******************

    // return Pair is newPosition, count of crossing/resting at Zero
    fun Instruction.turn2(position:Int):Pair<Int, Int>  {
        if (amount == 0) return position to 0

        val fullRotations = amount / 100
        val amountWithinOneRotation = amount % 100
        val tempNewPos = position + (turn.mod * amountWithinOneRotation)
        val crossed = if (position != 0 && (tempNewPos !in 1..<100)) 1 else 0
        val newPos = tempNewPos.mod(100)
        //println("$position $turn $amount => $tempNewPos $newPos $crossed $fullRotations")
        return newPos to fullRotations + crossed
    }

    fun part2(input: List<String>): Int =
        input.parse().fold(50 to 0) { (position, zeroCount), inst ->
            val (newPosition, instrZeroCount) = inst.turn2(position)
            newPosition to zeroCount + instrZeroCount
        }.second


    // *******************
    // Test code
    // *******************
    val testInput = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent().lines()
    check(part1(testInput) == 3)
    println("Check Part 1 passed")
    check(part2(testInput) == 6)
    println("Check Part 2 passed")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
