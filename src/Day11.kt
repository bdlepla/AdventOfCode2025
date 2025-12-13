fun main() {

    // main data structure is Map<String, List<String>>

    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():Map<String, List<String>> =
        this.let { input ->
            buildMap {
                input.forEach { line ->
                    val splits = line.split(':')
                    val key = splits[0]
                    val value = splits[1].drop(1).split(' ')
                    put(key, value)
                }
            }
        }

    // *******************
    // Part 1
    // *******************
    fun part1(input:List<String>):Int {

        // recursion!
        fun tracePath(document:Map<String, List<String>>, key:String, cache:MutableMap<String, Int>):Int {
            if (key == "out") { return 1 }
            if (cache.contains(key)) { return cache[key]!! }
            if (!document.containsKey(key)) { return 0 }
            val ret = document[key]!!.sumOf{tracePath(document, it, cache)}
            cache[key]=ret
            return ret
        }

        return tracePath(input.parse(), "you", mutableMapOf())
    }















    // *******************
    // Part 2
    // ********************


    fun part2(input: List<String>): Long {

        // recursion!
        fun tracePath(document:Map<String, List<String>>, key:String, required:MutableSet<String>, cache:MutableMap<String, Long>):Long {
            if (key == "out") { return if (required.isEmpty()) 1 else 0 }

            val cacheKey = key + ", " + required.joinToString()
            if (cache.contains(cacheKey)) { return cache[cacheKey]!! }

            if (!document.containsKey(key)) { return 0 }

            val foundRequired = required.contains(key)
            if (foundRequired) { required.remove(key) }

            val ret = document[key]!!.sumOf{tracePath(document, it, required, cache)}
            cache[cacheKey]=ret

            if (foundRequired) { required.add(key) }

            return ret
        }

        return tracePath(input.parse(), "svr", mutableSetOf("dac", "fft"), mutableMapOf())
    }

    // *******************
    // Test code
    // *******************
    val testInput = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
    """.trimIndent().lines()
    val part1Answer = part1(testInput)
    check(part1Answer == 5)
    println("Check Part 1 passed")


    val test2Input = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
    """.trimIndent().lines()
    val part2Answer = part2(test2Input)
    check(part2Answer == 2L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()

}