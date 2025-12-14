import kotlin.math.abs

typealias Point = Pair<Long, Long>
typealias Bounds = Pair<Point, Point>
fun main() {


    // *******************
    // Parsing
    // *******************
    fun List<String>.parse(): List<Pair<Long, Long>> =
        this.map { line ->
            line.split(',').map { it.toLong() }.let { it[0] to it[1] }
        }




    // *******************
    // Part 1
    // *******************



    fun part1(input: List<String>):Long =
        input.parse().allTuples().map {
            val (a, b) = it
            abs(((a.first - b.first)+1L) * ((a.second - b.second)+1L))
        }.maxOf { it }








    // *******************
    // Part 2
    // ********************

    fun part2(input:List<String>):Long {

        fun bounds(p1:Point, p2:Point):Bounds {
            val (x1, y1) = p1
            val (x2, y2) = p2
            val (minX, maxX) = x1.minMax(x2)
            val (minY, maxY) = y1.minMax(y2)
            return (minX to minY) to (maxX to maxY)
        }
        fun bounds(bounds:Bounds):Bounds {
            val (p1, p2) = bounds
            return bounds(p1, p2)
        }
        // this assumes the p1 <= p2
        fun area(bounds:Bounds):Long {
            val (p1, p2) = bounds
            return (p2.first - p1.first + 1) * (p2.second - p1.second + 1)
        }

        val corners = input.parse()

        val pairsSortedByArea = corners
            .allTuples()
            .map(::bounds)
            .sortedByDescending(::area)

        val lines = (corners + listOf(corners.first())) // make sure last to first is created
            .zipWithNext()
            .map(::bounds)
            .sortedByDescending(::area)

        val ret = pairsSortedByArea.first{ (p1, p2) ->
            !lines.any{ (l1, l2) ->
                val (l, t) = p1     // min corner (left and top)
                val (r, b) = p2     // max corner (right and bottom)
                val (lx, ly) = l1   // top left line segment
                val (rx, ry) = l2   // bottom right line segment
                lx<r && ly<b && rx>l && ry>t
            }
        }

        return area(ret)
    }




    // *******************
    // Test code
    // *******************
    val testInput = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 50L)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 24L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day09.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
