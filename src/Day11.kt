fun main() {

    fun printGrid(grid: MutableList<MutableList<Int>>){
        for (row in grid){
            for (char in row){
                print(char)
            }
            println("")
        }
    }

    fun part1(input: List<String>): Long {
        var inputGrid: MutableList<MutableList<Int>> =
            input.map { it.map { it.digitToInt() }.toMutableList() }.toMutableList()

        val steps = 100
        var totalFlashes = 0

        printGrid(inputGrid)

        for (i in 1..steps) {
            // Initial increase
            inputGrid = inputGrid.map { it.map { it + 1 }.toMutableList() }.toMutableList()

            var flashedCordinates = mutableListOf<Pair<Int, Int>>()

            // Work out which ones flash
            var flashReady = true
            while (flashReady) {
                flashReady = false  // Assume we are done unless we find a 9
                for ((y, row) in inputGrid.withIndex()) {
                    for ((x, spotValue) in row.withIndex()) {
                        if (Pair(x, y) in flashedCordinates){
                            continue
                        }
                        if (spotValue > 9) {
                            for (xmod in -1..1) {
                                if ((x+xmod >= inputGrid.size) or (x+xmod < 0)){
                                    continue
                                }
                                for (ymod in -1..1) {
                                    if ((y+ymod >= inputGrid.size) or (y+ymod < 0)){
                                        continue
                                    }
                                    inputGrid[y + ymod][x + xmod] += 1
                                }
                            }
                            totalFlashes += 1
                            inputGrid[y][x] += 1 // Show this one has flashed
                            flashReady = true
                            flashedCordinates.add(Pair(x,y))
                        }
                    }
                } // End for y row
            }// End while flashReady
            // Once all have flashed in a step
            // Finally, each flashed octopus resets to 0
            for ((y, row) in inputGrid.withIndex()){
                for ((x, squid) in row.withIndex()){
                    if (squid > 9){
                        inputGrid[y][x] = 0
                    }
                }
            }
            println("On steps $i")
            printGrid(inputGrid)
        } // End for 100 steps

        return totalFlashes.toLong()
    }

        fun part2(input: List<String>): Long {
            return -1
        }

        // Change these for each day
        val day: String = "11"
        val testPart1Expected: Long = 1656
        val testPart2Expected: Long = 61229


        // test if implementation meets criteria from the description, like:
        val testInput = readInput("Day${day}_test")
        val testResPart1 = part1(testInput)
        check(testResPart1 == testPart1Expected) { "Test for part 1 returned ${testResPart1}, expected $testPart1Expected" }
        val input = readInput("Day${day}")
        println("Part 1 result: ${part1(input)}")

//    val smallTestInput = readInput("Day${day}_test_small")
//    val testResSmallPart2 = part2(smallTestInput)
//    check(testResSmallPart2.toInt() == 5353)

//    val testResPart2 = part2(testInput)
//    check(testResPart2.toLong() == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
//    println("Part 2 result: ${part2(input)}")
    }
