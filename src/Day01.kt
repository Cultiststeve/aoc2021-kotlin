fun main() {
    fun part1(input: List<String>): Int {
        var prevDepth: Int = -1
        var depthIncreases: Int = 0
        for (DepthStr in input) {
            val depth = DepthStr.toInt()
            if (prevDepth == -1){
                prevDepth = depth
                continue
            }
            val diff = depth - prevDepth
            if (diff > 0){
                depthIncreases++
            }
            prevDepth = depth
        }
        return depthIncreases
    }

    fun part2(input: List<String>): Int {
        val depthInts = input.map{it.toInt()}

        fun movingAverage(index: Int): Int{
            // Get moving average, from given index back 2
            return depthInts[index] + depthInts[index-1] + depthInts[index-2]
        }

        var depthIncreases = 0
        for ((i, depth) in depthInts.withIndex()) {
           if (i < 3){
               continue  // Need 2 full moving averages
           }
            val currAverage = movingAverage(i)
            val prevAverage = movingAverage(i-1)
            val diff = currAverage - prevAverage
            if (diff > 0){
             depthIncreases++
            }

        }
        return depthIncreases
    }

    // Change these for each day
    val day: String = "01"
    val testPart1Res = 7
    val testPart2Res = 5

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val TestRes = part1(testInput)
    check(TestRes == testPart1Res){"Test for part 1 returned $TestRes, expected $testPart1Res"}

    val TestResPart2 = part2(testInput)
    check(TestResPart2 == testPart2Res){"Test for part 2 returned $TestRes, expected $testPart2Res"}

    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")
    println("Part 2 result: ${part2(input)}")
}
