fun main() {
    fun part1(input: List<String>): Int {
        var drawnNumbers = input[0].split(",").map { it.toInt() }

        class Grid(
            val numbers: Array<IntArray?>,
            )
        var grids: MutableList<Grid> = mutableListOf()

        for (i in 2..input.size step 6){  // Each grid takes 6 lines to define
            var numbers: Array<IntArray?> = arrayOfNulls<IntArray>(5)
            for (j in 0..4){
                val lineNumbers = input[i+j].split(" ").filter { it != "" }.map { it.toInt() }
                numbers[j] = lineNumbers.toIntArray()
            }
            val newGrid = Grid(numbers)
            grids.add(newGrid)
        }

        // Now we have parsed the input, start to think if we have bingo
        for (chosenAmt in 5..drawnNumbers.size) {
            val drawnNumbers = drawnNumbers.subList(0, chosenAmt)  // The numbers that have been drawn so far

            for (grid in grids){
                // Has this grid won yet?
                var won = false

                // Rows?
                // TODO a lot of iteration here, can probably be done better
                for (row in grid.numbers) {
                    var rowGood = true
                    for (num in row!!){
                        if (num !in drawnNumbers)
                            rowGood = false
                            break
                    }
                    if (rowGood) {
                        won = true
                        break
                    }
                for (colums){
                    // are colums good?
                }

                }
            }
        }

        return input.size
    }

    fun part2(input: List<String>): Int {

        return input.size
    }

    // Change these for each day
    val day: String = "04"
    val testPart1Expected = 198
    val testPart2Expected = 230

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testResPart1 = part1(testInput)
    check(testResPart1 == testPart1Expected){"Test for part 1 returned ${testResPart1}, expected $testPart1Expected"}
    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

//    val testResPart2 = part2(testInput)
//    check(testResPart2 == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
//    println("Part 2 result: ${part2(input)}")
}
