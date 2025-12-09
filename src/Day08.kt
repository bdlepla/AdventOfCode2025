


fun main() {

    data class Point3d(val x:Long, val y:Long, val z:Long)
    fun Point3d.distanceSquared(other:Point3d):Long =
        (this.x-other.x)*(this.x-other.x) +
                (this.y - other.y)*(this.y - other.y) +
                (this.z - other.z)*(this.z - other.z)



    // *******************
    // Parsing
    // *******************
    fun List<String>.parse():List<Point3d> =
        this.map {line ->
            val (x,y,z) = line.split(',').map{it.toLong()}
            Point3d(x,y,z)
        }


    // *******************
    // Part 1
    // *******************



    fun part1(input: List<String>, numConnections:Int):Long {
        val junctionPairsSortedByShortestDistance =
            input.parse()
                .allTuples()
                .map{ (a,b) -> a.distanceSquared(b) to (a to b) }
                .sortedBy{it.first}
                .take(numConnections)
                .map{it.second}

        val groupOfCircuits = mutableListOf<MutableSet<Point3d>>()
        junctionPairsSortedByShortestDistance.forEach{ (j1, j2) ->
            val circuitIdx1 = groupOfCircuits.indexOfFirst { circuit -> circuit.contains(j1) }
            if (circuitIdx1 != -1) {
                val circuitIdx2 = groupOfCircuits.indexOfFirst { circuit -> circuit.contains(j2) }
                if (circuitIdx2 != -1) {
                    if (circuitIdx2 != circuitIdx1) {
                        groupOfCircuits[circuitIdx1].addAll(groupOfCircuits[circuitIdx2])
                        groupOfCircuits.removeAt(circuitIdx2)
                    }
                } else {
                    groupOfCircuits[circuitIdx1].add(j2)
                }
            } else {
                val circuitIdx2 = groupOfCircuits.indexOfFirst { circuit -> circuit.contains(j2) }
                if (circuitIdx2 != -1) {
                    groupOfCircuits[circuitIdx2].add(j1)
                } else {
                    groupOfCircuits.add(mutableSetOf(j1, j2))
                }
            }
        }
//        for (circuit in groupOfCircuits) {
//            println(circuit)
//        }
        return groupOfCircuits.map{it.count().toLong()}.sortedDescending().take(3).reduce(Long::times)
    }





















    // *******************
    // Part 2
    // ********************


    fun part2(input:List<String>):Long {
        val junctions = input.parse()
        val junctionPairsSortedByShortestDistance =
            junctions
                .allTuples()
                .map{ (a,b) -> a.distanceSquared(b) to (a to b) }
                .sortedBy{it.first}
                .map{it.second}

        val groupOfCircuits = mutableListOf<MutableSet<Point3d>>()
        for (junction in junctions) {
            groupOfCircuits.add(mutableSetOf(junction))
        }
        junctionPairsSortedByShortestDistance.forEach{ (j1, j2) ->
            val circuitIdx1 = groupOfCircuits.indexOfFirst { circuit -> circuit.contains(j1) }
            if (circuitIdx1 != -1) {
                val circuitIdx2 = groupOfCircuits.indexOfFirst { circuit -> circuit.contains(j2) }
                if (circuitIdx2 != -1) {
                    if (circuitIdx2 != circuitIdx1) {
                        groupOfCircuits[circuitIdx1].addAll(groupOfCircuits[circuitIdx2])
                        groupOfCircuits.removeAt(circuitIdx2)
                    }
                } else {
                    groupOfCircuits[circuitIdx1].add(j2)
                }
            } else {
                val circuitIdx2 = groupOfCircuits.indexOfFirst { circuit -> circuit.contains(j2) }
                if (circuitIdx2 != -1) {
                    groupOfCircuits[circuitIdx2].add(j1)
                } else {
                    groupOfCircuits.add(mutableSetOf(j1, j2))
                }
            }
            if (groupOfCircuits.count() == 1) return j1.x * j2.x
        }
//        for (circuit in groupOfCircuits) {
//            println(circuit)
//        }

        throw IllegalStateException("Should not be here; something went terribly wrong")
    }



    // *******************
    // Test code
    // *******************
    val testInput = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
    """.trimIndent().lines()
    val part1Answer = part1(testInput, 10)
    check(part1Answer == 40L)
    println("Check Part 1 passed")
    val part2Answer = part2(testInput)
    check(part2Answer == 25272L)
    println("Check Part 2 passed")

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    part1(input, 1000).println()
    part2(input).println()
}
