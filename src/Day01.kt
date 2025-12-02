import kotlin.collections.map

enum class LockTurn {
    Right,
    Left
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
    fun Instruction.turn1(position:Int):Int =
        if (turn == LockTurn.Left) (100 + (position - amount)) % 100
        else (position + amount) % 100


    fun part1(input: List<String>):Int =
        input.parse().runningFold(50) { acc, inst ->
            inst.turn1(acc)
        }.count{ it == 0}


















    // *******************
    // Part 2
    // *******************

    // return Pair is newPosition, count of crossing/resting at Zero
    fun Instruction.turn2(position:Int):Pair<Int, Int>  {
        if (amount == 0) return position to 0

        val fullRotations = amount / 100
        val amountWithinOneRotation = amount % 100

        return if (turn == LockTurn.Left) {
            val tempNewPos = position - amountWithinOneRotation
            val crossed = if (position != 0 && tempNewPos <= 0) 1 else 0
            val newPos = (100 + tempNewPos) % 100
            //println("$position $turn $amount => $tempNewPos $newPos $crossed $fullRotations")
            newPos to fullRotations + crossed
        }
        else {
            val tempNewPos = position + amountWithinOneRotation
            val crossed = if (position != 0 && tempNewPos >= 100) 1 else 0
            val newPos = tempNewPos % 100
            //println("$position $turn $amount => $tempNewPos $newPos $crossed $fullRotations")
            newPos to fullRotations + crossed
        }
    }

    fun part2(input: List<String>): Int {
        return input.parse().fold(50 to 0) { acc, inst ->
            val (position, zeroCount) = acc
            val (newPosition, instrZeroCount) = inst.turn2(position)
            newPosition to zeroCount + instrZeroCount
        }.second
    }

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
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
