fun main() {
    fun part1(input: List<String>): Int {
        var horiz_pos = 0
        var depth = 0

        for (command in input){
            val dir = command.split(" ")[0]
            val magnitude = command.split(" ")[1].toInt()
            when (dir){
                "forward" -> horiz_pos += magnitude
                "up" -> depth -= magnitude
                "down" -> depth += magnitude
            else -> println("Error, we got a direction: $dir")
            }
        }
        return horiz_pos * depth
    }

    fun part2(input: List<String>): Int {
        var horiz_pos = 0
        var depth = 0
        var aim = 0

        for (command in input){
            val dir = command.split(" ")[0]
            val magnitude = command.split(" ")[1].toInt()
            when (dir){
                "forward" -> {
                    horiz_pos += magnitude
                    depth += aim * magnitude
                }
                "up" -> aim -= magnitude
                "down" -> aim += magnitude
                else -> println("Error, we got a direction: $dir")
            }
        }
        return horiz_pos * depth
    }

    // Change these for each day
    val day: String = "02"
    val testPart1Res = 150
    val testPart2Res = 900

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val TestRes = part1(testInput)
    check(TestRes == testPart1Res){"Test for part 1 returned $TestRes, expected $testPart1Res"}

    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

    val TestResPart2 = part2(testInput)
    check(TestResPart2 == testPart2Res){"Test for part 2 returned $TestRes, expected $testPart2Res"}
    println("Part 2 result: ${part2(input)}")
}
