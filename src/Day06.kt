
enum class Day06Operation {
    Add,
    Multiply
}

fun main() {


    data class Worksheet(val operations:List<Pair<Day06Operation, List<Long>>>)


    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():Worksheet {
        val inputNumbers = this.dropLast(1).map{ line ->
            line.split(' ').filter{it.isNotBlank()}.map{it.toLong()}
        }
        val inputOperators = this.last().split(' ').filter{it.isNotBlank()}.map{
            when (it) {
                "+" -> Day06Operation.Add
                "*" -> Day06Operation.Multiply
                else -> throw IllegalArgumentException("Unknown input operator '$it' in parse")
            }
        }

        val operations = inputOperators.mapIndexed{ idx, op ->
            op to inputNumbers.map{row -> row[idx] }
        }

        return Worksheet(operations)
    }


    // *******************
    // Part 1
    // *******************

    fun List<Long>.product() = this.reduce{a,b -> a * b}

    fun Worksheet.doTheHomework():Long =
        operations.sumOf{ (op, numbers) ->
            when (op) {
                Day06Operation.Add -> numbers.sum()
                Day06Operation.Multiply -> numbers.product()
            }
        }


    fun part1(input: List<String>):Long = input.parse().doTheHomework()





















    // *******************
    // Part 2
    // ********************
    fun String.indicesOf(filter:(Char)->Boolean):List<Int> =
        this.indices.asIterable().filter{filter(this[it])}

    fun List<String>.parse2():Worksheet {
        // the operator "+" or "*" lines up at the left most column of each group.
        // grab the operator and the widths to the next operator (or end of line)
        val operationLine = this.last()
        val operatorIndices = operationLine.indicesOf{it in listOf('+','*')} + listOf(this[0].length+1)
        val operatorAndWidths = operatorIndices.zipWithNext().map {
            val operator = when (operationLine[it.first]) {
                    '+' -> Day06Operation.Add
                    '*' -> Day06Operation.Multiply
                    else -> throw IllegalArgumentException("Unknown input '$it' in parse")
                }
            operator to it.second - it.first
        }
        val inputNumbers = this.dropLast(1)
        var curIdx = 0
        val operations = operatorAndWidths.map { (op, width) ->
            val groupNumbers = (0..<width-1).map {
                val col = curIdx+it
                inputNumbers.map { line -> line[col] }.joinToString("").trim().toLong()
            }
            curIdx += width
            op to groupNumbers
        }

        return Worksheet(operations)

    }

    fun part2(input:List<String>):Long = input.parse2().doTheHomework()


    // *******************
    // Test code
    // *******************
    val testInput = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   + 
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 4277556L)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 3263827L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
