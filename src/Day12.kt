class Day12 {
}fun main() {

    fun <T> List<T>.toPair(): Pair<T, T> {
        require (this.size == 2) { "List is not of length 2!" }
        val (a, b) = this
        return Pair(a, b)
    }

    val start = "start"

    var pathMap: List<Pair<String, String>> = mutableListOf()


    fun calculate_paths_from(existingPath: MutableList<Pair<String, String>>): MutableList<Pair<String, String>>{
        // Given a location, calculate all paths to end
        // Return as a list of those paths
        var newPaths = existingPath
        for (possiblePath in pathMap){
            if (possiblePath.first == existingPath.last().second){
                //Possible path, take it
                newPaths = newPaths.plus(
                    calculate_paths_from(existingPath.plus(possiblePath).toMutableList())
                ).toMutableList()
            }
        }

        return newPaths
    }

    fun part1(input: List<String>): Long {
        pathMap = input.map { it.split("-").toPair() }

        var possiblePaths: MutableList<String> = mutableListOf()
        for (path in pathMap){
            if (path.first == start){  // Assume start is only in first part of a path
                possiblePaths.merge(calculate_paths_from(existingPath = mutableListOf(Pair("prestart", start))))
            }
        }

        // Remove paths without a small cave

        return possiblePaths.size.toLong() // Return total possible paths
    }

    fun part2(input: List<String>): Long {
        return -1
    }

    // Change these for each day
    val day: String = "12"
    val testPart1Expected: Long = 10
    val testPart2Expected: Long = 195


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testResPart1 = part1(testInput)
    check(testResPart1 == testPart1Expected) { "Test for part 1 returned ${testResPart1}, expected $testPart1Expected" }
    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

//    val smallTestInput = readInput("Day${day}_test_small")
//    val testResSmallPart2 = part2(smallTestInput)
//    check(testResSmallPart2.toInt() == 5353)
//
//    val testResPart2 = part2(testInput)
//    check(testResPart2.toLong() == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
//    println("Part 2 result: ${part2(input)}")
}
